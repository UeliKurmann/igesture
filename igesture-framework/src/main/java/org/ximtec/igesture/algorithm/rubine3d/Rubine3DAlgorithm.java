package org.ximtec.igesture.algorithm.rubine3d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmException.ExceptionType;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;

public class Rubine3DAlgorithm implements Algorithm {

	private Configuration configuration;
	
	@Override
	public Enum<?>[] getConfigParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultParameterValue(String parameterName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(Configuration configuration) throws AlgorithmException {
		this.configuration = configuration;
		
	}

	/**
	 * Recognizes a Gesture<?> if it is a GestureSample3D, otherwise it throws
	 * an AlgorithmException
	 */
	public ResultSet recognise(Gesture<?> gesture) throws AlgorithmException {

		if (gesture instanceof GestureSample3D) {
			return this.recognise((GestureSample3D) gesture);
		} else {
			throw new AlgorithmException(ExceptionType.Recognition); // Is this
			// correct?
		}
	}

	/**
	 * Recognizes a GestureSample3D and returns a Resultset
	 * 
	 * @param gesture
	 *            The GestureSample3D to be recognized
	 * @return The ResultSet after recognising the gesture
	 * @throws AlgorithmException
	 */
	public ResultSet recognise(GestureSample3D gesture)
			throws AlgorithmException {
		// Split gesture into planes
		List<Gesture<Note>> planes = gesture.splitToPlanes();

		// TODO What to do with the configuration??

		// Determine the weights of the planes by analysing the movement per
		// plane
		List<Double> weights = computePlaneWeights(planes);
		// Start recognition process
		Recogniser recogniser = new Recogniser(new Configuration());
		// Iterate through the planes and recognise per plane and add a
		// ResultSet to sets for every plane
		Iterator<Gesture<Note>> iterator = planes.iterator();
		List<ResultSet> sets = new ArrayList<ResultSet>();
		while (iterator.hasNext()) {
			Gesture<Note> plane = iterator.next();
			// Recognise current plane and add ResultSet to sets
			sets.add(recogniser.recognise(plane));
		}
		// Combine sets to one ResultSet and return
		try {
			return combineResultSets(sets, weights);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Analyses the amount of movement per plane for the given list of plane
	 * notes and calculates weight percentages per plane out of it
	 * 
	 * @param planes
	 *            The planes to be analysed
	 * @return The list of weights per plane, corresponding to the order of the
	 *         input planes
	 */
	private List<Double> computePlaneWeights(List<Gesture<Note>> planes) {
		// Each plane is a note that consists of traces. By adding up the length
		// of all traces in the note you get an idea of the amount of movement
		// in the plane
		List<Double> planeMovements = new Vector<Double>();
		Iterator<Gesture<Note>> planeIterator = planes.iterator();
		while (planeIterator.hasNext()) {
			double planeMovementLength = 0;
			Gesture<Note> plane = planeIterator.next();
			Iterator<Trace> traceIterator = plane.getGesture().getTraces()
					.iterator();
			while (traceIterator.hasNext()) {
				Trace trace = traceIterator.next();
				planeMovementLength = planeMovementLength + trace.getLength();
			}
			planeMovements.add(Double.valueOf(planeMovementLength));
		}
		// Now we have the lengths of the combined traces (which can be seen as
		// the amount of movement) per Note. This has to be converted to
		// percentages, that make 100 when added up. These percentages stand for
		// the movement that takes place in the corresponding plane, as a
		// percentage of the total movement of all planes added up (total Trace
		// length)
		double totalLength = 0;
		Iterator<Double> lengthsIterator = planeMovements.iterator();
		while (lengthsIterator.hasNext()) {
			Double length = lengthsIterator.next();
			totalLength = totalLength + length.doubleValue();
		}
		// Compute per plane its percentage of the total movement
		List<Double> weightPercentages = new Vector<Double>();
		lengthsIterator = planeMovements.iterator();
		while (lengthsIterator.hasNext()) {
			Double length = lengthsIterator.next();
			double percentage = (length / totalLength) * 100;
			weightPercentages.add(Double.valueOf(percentage));
		}
		// Return the list of found percentages
		return weightPercentages;
	}

	/**
	 * Combines multiple ResultSet objects into one, using weight factors.
	 * 
	 * @param sets
	 *            The ResultSets to be combined
	 * @param weights
	 *            The weights per ResultSet
	 * @return The combined ResultSet
	 * @throws Exception
	 *             When the number of resultsets does not match the number of
	 *             weights or the sum of the weights is not 100%
	 */
	private ResultSet combineResultSets(List<ResultSet> sets,
			List<Double> weights) throws Exception {
		// First check if the number of resultsets matches the number of weights
		if (sets.size() != weights.size()) {
			throw new Exception(
					"Rubine3DAlgorithm.combineResultSets(): The number of ResultSets does not match the number of weights.");
		}
		// Then check if the sum of the weights is 100 %
		double totalWeights = 0;
		Iterator<Double> wIt = weights.iterator();
		while (wIt.hasNext()) {
			Double weight = wIt.next();
			totalWeights = totalWeights + weight.doubleValue();
		}
		if (totalWeights != 100) {
			throw new Exception(
					"Rubine3DAlgorithm.combineResultSets(): The sum of the weights is not 100 %.");
		}
		// Create a list of found GestureClasses in the resultsets combined
		List<GestureClass> foundClasses = findAllGestureClasses(sets);
		// Create a Resultset with combined Results
		ResultSet returnSet = combine(sets, weights, foundClasses);
		// Sort the results in the resultset by accuracy
		returnSet = sortByAccuray(returnSet);
		// Return the set
		return returnSet;
	}

	/**
	 * Combines Resultsets
	 * 
	 * @param sets
	 *            The ResultSets to be combined
	 * @param weights
	 *            The weights of the sets
	 * @param foundClasses
	 *            The GestureClasses that have been found in the sets before
	 * @return The combined ResultSet
	 */
	private ResultSet combine(List<ResultSet> sets, List<Double> weights,
			List<GestureClass> foundClasses) {
		// Create return variable
		ResultSet returnSet = new ResultSet();
		// Iterate through found gesture classes list
		Iterator<GestureClass> it = foundClasses.iterator();
		while (it.hasNext()) {
			GestureClass gClass = it.next();
			List<Double> accuracies = new Vector<Double>();
			// Iterate through all available resultsets looking for results for
			// this gesture class
			Iterator<ResultSet> ite = sets.iterator();
			Iterator<Double> wIte = weights.iterator();
			while (ite.hasNext()) {
				double weight = wIte.next().doubleValue();
				// Iterate through resultlist in set
				Iterator<Result> iter = ite.next().getResults().iterator();
				while (it.hasNext()) {
					Result tempResult = iter.next();
					if (tempResult.getGestureClass().equals(gClass)) {
						// Add accuracy to the list of accuracies for this
						// gesture class
						double weightedAccuracy = tempResult.getAccuracy()
								* (weight / 100);
						accuracies.add(new Double(weightedAccuracy));
					}

				}
			}
			// Add up the weighted accuracies for this gesture class
			double accuracy = 0;
			Iterator<Double> accIt = accuracies.iterator();
			while (accIt.hasNext()) {
				Double acc = accIt.next();
				accuracy = accuracy + acc.doubleValue();
			}
			// Divide by the number of accuracies in the list
			accuracy = accuracy / accuracies.size();
			// Create new result and add it to return variable
			returnSet.addResult(new Result(gClass, accuracy));
		}
		return returnSet;
	}

	/**
	 * Creates a list of all the GestureClasses it finds in the given list of
	 * ResultSets
	 * 
	 * @param sets
	 *            The ResultSets to be searched
	 * @return The list of found GestureClasses
	 */
	private List<GestureClass> findAllGestureClasses(List<ResultSet> sets) {
		// Create a list of found GestureClasses in the resultsets combined
		List<GestureClass> foundClasses = new Vector<GestureClass>();
		// Iterate through list of sets to create complete list of gesture
		// classes in the results
		Iterator<ResultSet> i = sets.iterator();
		while (i.hasNext()) {
			// Iterate through resultlist in set
			Iterator<Result> it = i.next().getResults().iterator();
			while (it.hasNext()) {
				Result tempResult = it.next();
				if (!foundClasses.contains(tempResult.getGestureClass())) {
					foundClasses.add(tempResult.getGestureClass());
				}
			}
		}
		return foundClasses;
	}

	/**
	 * Sorts the results in a ResultSet such that the result with the highest
	 * accuracy comes first
	 * 
	 * @param inputSet
	 *            The ResulSet that needs to be sorted by accuracy of the
	 *            Results it contains
	 * @return The sorted ResultSet
	 */
	private ResultSet sortByAccuray(ResultSet inputSet) {
		// Create output resultset and give it its first result
		ResultSet outputSet = new ResultSet();
		outputSet.addResult(inputSet.getResult(0));
		// Loop through inputSet and take accuracy
		for (int j = 0; j < inputSet.getResults().size(); j++) {
			boolean added = false;
			// Loop through output set and compare accuracies
			for (int k = 0; k < outputSet.getResults().size(); k++) {
				// If the accuracy is higher than the accuracy of the result at
				// position k in outputset
				if (inputSet.getResult(j).getAccuracy() > outputSet
						.getResult(k).getAccuracy()) {
					// Insert the result and break the loop to prevent from
					// inserting multiple times
					outputSet.getResults().add(k, inputSet.getResult(j));
					added = true;
				}
			}
			// If not added because the accuracy is smaller than the existing
			// ones, add at the end
			if (!added) {
				outputSet.getResults().add(inputSet.getResult(j));
			}
		}
		// Return output variable
		return outputSet;
	}

}
