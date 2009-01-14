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
import org.ximtec.igesture.algorithm.feature.FeatureTool;
import org.ximtec.igesture.algorithm.rubine.RubineAlgorithm;
import org.ximtec.igesture.algorithm.rubine.RubineConfiguration;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.core.Sample3DDescriptor;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.util.RecordedGesture3D;
import org.ximtec.igesture.util.XMLTool;

public class Rubine3DAlgorithm implements Algorithm {

	private Rubine3DConfiguration rubine3dConfig;

	// Plane gesture sets
	private GestureSet setXY;
	private GestureSet setYZ;
	private GestureSet setZX;

	/**
	 * Constructor
	 */
	public Rubine3DAlgorithm() {
		setXY = new GestureSet();
		setYZ = new GestureSet();
		setZX = new GestureSet();
	}

	@Override
	public Enum<?>[] getConfigParameters() {
		return Rubine3DConfiguration.Config.values();
	}

	@Override
	public String getDefaultParameterValue(String parameterName) {
		return Rubine3DConfiguration.getDefaultConfiguration().get(parameterName);
	}

	@Override
	public void init(Configuration configuration) throws AlgorithmException {
		System.err.println("Rubine3DAlgorithm.init()");
		this.rubine3dConfig = new Rubine3DConfiguration(configuration);
		// Split all gesture sets up into planes
		Iterator<GestureSet> i = configuration.getGestureSets().iterator();
		while (i.hasNext()) {
			GestureSet tempSet = i.next();
			splitGestureSet(tempSet);
		}
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
		// Determine the weights of the planes
		List<Double> weights = determinePlaneWeights(planes);

		Configuration configXY = createConfiguration("XY");
		Configuration configYZ = createConfiguration("YZ");
		Configuration configZX = createConfiguration("ZX");
		// Start recognition process
		Recogniser recogniserXY = new Recogniser(configXY);
		Recogniser recogniserYZ = new Recogniser(configYZ);
		Recogniser recogniserZX = new Recogniser(configZX);
		// Recognise and add a ResultSet to sets per plane
		List<ResultSet> sets = new ArrayList<ResultSet>();
		sets.add(recogniserXY.recognise(planes.get(0)));
		sets.add(recogniserYZ.recognise(planes.get(1)));
		sets.add(recogniserZX.recognise(planes.get(2)));
		// Combine sets to one ResultSet and return
		try {
			// return sets.get(0);
			return combineResultSets(sets, weights);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a configuration for the recogniser (ugly, needs improvement)
	 * 
	 * @return The Configuration for the recogniser
	 */
	private Configuration createConfiguration(String plane) {
		// New Configuration object
		Configuration config = new Configuration();
		// Add the Rubine algorithm to the configuration
		config.addAlgorithm(RubineAlgorithm.class.getName());
		// Add gesture set to the configuration
		if (plane.equals("XY")) {
			// Add Gesture Set
			config.addGestureSet(this.setXY);
			// Add parameters for the rubine algorithm for the plane
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.MAHALANOBIS_DISTANCE.name(),
					Double.toString(this.rubine3dConfig.getXyConfiguration()
							.getMahalanobisDistance()));
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.MIN_DISTANCE.name(), Double
							.toString(this.rubine3dConfig.getXyConfiguration()
									.getMinDistance()));
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.PROBABILITY.name(),
					Double.toString(this.rubine3dConfig.getXyConfiguration()
							.getProbability()));
			// Create feauture string
			String featureString = "";
			for(int i = 0; i < this.rubine3dConfig.getXyConfiguration().getFeatureList().length; i++){
				featureString = featureString + this.rubine3dConfig.getXyConfiguration().getFeatureList()[i].getClass().getName() + ",";
			}
			//Remove last comma
			featureString = featureString.substring(0, featureString.length()-1);
			//Add feature string to config
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.FEATURE_LIST.name(),
					featureString);
			// Return configuration
			return config;
		}
		if (plane.equals("YZ")) {
			config.addGestureSet(this.setYZ);
			// Add parameters for the rubine algorithm for the plane
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.MAHALANOBIS_DISTANCE.name(),
					Double.toString(this.rubine3dConfig.getYzConfiguration()
							.getMahalanobisDistance()));
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.MIN_DISTANCE.name(), Double
							.toString(this.rubine3dConfig.getYzConfiguration()
									.getMinDistance()));
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.PROBABILITY.name(),
					Double.toString(this.rubine3dConfig.getYzConfiguration()
							.getProbability()));
			// Create feauture string
			String featureString = "";
			for(int i = 0; i < this.rubine3dConfig.getYzConfiguration().getFeatureList().length; i++){
				featureString = featureString + this.rubine3dConfig.getYzConfiguration().getFeatureList()[i].getClass().getName() + ",";
			}
			//Remove last comma
			featureString = featureString.substring(0, featureString.length()-1);
			//Add feature string to config
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.FEATURE_LIST.name(),
					featureString);
			// Return configuration
			return config;
		}
		if (plane.equals("ZX")) {
			config.addGestureSet(this.setZX);
			// Add parameters for the rubine algorithm for the plane
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.MAHALANOBIS_DISTANCE.name(),
					Double.toString(this.rubine3dConfig.getZxConfiguration()
							.getMahalanobisDistance()));
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.MIN_DISTANCE.name(), Double
							.toString(this.rubine3dConfig.getZxConfiguration()
									.getMinDistance()));
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.PROBABILITY.name(),
					Double.toString(this.rubine3dConfig.getZxConfiguration()
							.getProbability()));
			// Create feauture string
			String featureString = "";
			for(int i = 0; i < this.rubine3dConfig.getZxConfiguration().getFeatureList().length; i++){
				featureString = featureString + this.rubine3dConfig.getZxConfiguration().getFeatureList()[i].getClass().getName() + ",";
			}
			//Remove last comma
			featureString = featureString.substring(0, featureString.length()-1);
			//Add feature string to config
			config.addParameter(RubineAlgorithm.class.getName(),
					RubineConfiguration.Config.FEATURE_LIST.name(),
					featureString);
			// Return configuration
			return config;
		}
		System.err
				.println("Rubine3DAlgorithm.createConfiguration(): Please provide a valid plane name (\"XY\", \"YZ\" or \"ZX\")");
		return null;
	}

	private List<Double> determinePlaneWeights(List<Gesture<Note>> planes) {
		List<Double> weights = new Vector<Double>();
		// Fill
		weights.add(rubine3dConfig.getXyWeight());
		weights.add(rubine3dConfig.getYzWeight());
		weights.add(rubine3dConfig.getZxWeight());
		// Return
		return weights;
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
		// Create a Resultset with combined Results
		ResultSet returnSet = combine(sets, weights);
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
	private ResultSet combine(List<ResultSet> sets, List<Double> weights) {
		// Multiply each plane result by its weight
		// XY plane
		for (int i = 0; i < sets.get(0).getResults().size(); i++) {
			sets.get(0).getResults().get(i).setAccuracy(
					sets.get(0).getResults().get(i).getAccuracy()
							* weights.get(0));
		}
		// YZ plane
		for (int i = 0; i < sets.get(1).getResults().size(); i++) {
			sets.get(1).getResults().get(i).setAccuracy(
					sets.get(1).getResults().get(i).getAccuracy()
							* weights.get(1));
		}
		// ZX plane
		for (int i = 0; i < sets.get(2).getResults().size(); i++) {
			sets.get(2).getResults().get(i).setAccuracy(
					sets.get(2).getResults().get(i).getAccuracy()
							* weights.get(2));
		}
		// Create return variable
		ResultSet returnSet = new ResultSet();
		// Add results up to make final resultset
		// XY plane
		returnSet = sets.get(0);
		// YZ plane
		// Loop through results
		for (int i = 0; i < sets.get(1).getResults().size(); i++) {
			// if the returnset already contains a result for this gesture class
			if (returnSet.contains(sets.get(1).getResult(i).getGestureClass())) {
				// Find the result with this gesture class
				for (int j = 0; j < returnSet.getResults().size(); j++) {
					if (returnSet.getResults().get(j).getGestureClass()
							.getName().equals(
									sets.get(1).getResult(i).getGestureClass()
											.getName())) {
						returnSet.getResults().get(j).setAccuracy(
								returnSet.getResults().get(j).getAccuracy()
										+ sets.get(1).getResult(i)
												.getAccuracy());
					}
				}
			} else { // Add a new result for this gesture class to the returnset
				returnSet.addResult(sets.get(1).getResult(i));
			}
		}
		// ZX plane
		// Loop through results
		for (int i = 0; i < sets.get(2).getResults().size(); i++) {
			// if the returnset already contains a result for this gesture class
			if (returnSet.contains(sets.get(2).getResult(i).getGestureClass())) {
				// Find the result with this gesture class
				for (int j = 0; j < returnSet.getResults().size(); j++) {
					if (returnSet.getResults().get(j).getGestureClass()
							.getName().equals(
									sets.get(2).getResult(i).getGestureClass()
											.getName())) {
						returnSet.getResults().get(j).setAccuracy(
								returnSet.getResults().get(j).getAccuracy()
										+ sets.get(2).getResult(i)
												.getAccuracy());
					}
				}
			} else { // Add a new result for this gesture class to the returnset
				returnSet.addResult(sets.get(2).getResult(i));
			}
		}
		// Make the accuracies in the set add up to 1
		double totalAccuracy = 0;
		for (int i = 0; i < returnSet.getResults().size(); i++) {
			totalAccuracy = totalAccuracy
					+ returnSet.getResult(i).getAccuracy();
		}
		// System.err.println("TOTAL ACCURACY: " + totalAccuracy);
		if (totalAccuracy != 0 && totalAccuracy < 1) {
			double factor = 1 / totalAccuracy;
			for (int i = 0; i < returnSet.getResults().size(); i++) {
				returnSet.getResults().get(i).setAccuracy(
						returnSet.getResults().get(i).getAccuracy() * factor);
			}
		}
		// Return the set
		return returnSet;
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

	/**
	 * Splits inputSet into three separate gesture sets for the three planes
	 * 
	 * @param inputSet
	 */
	private void splitGestureSet(GestureSet inputSet) {
		// Set names
		setXY.setName(inputSet.getName());
		setYZ.setName(inputSet.getName());
		setZX.setName(inputSet.getName());
		// Iterate through gesture classes in set
		Iterator<GestureClass> classIter = inputSet.getGestureClasses()
				.iterator();
		while (classIter.hasNext()) {
			GestureClass tempClass = classIter.next();
			GestureClass classXY = new GestureClass(tempClass.getName());
			GestureClass classYZ = new GestureClass(tempClass.getName());
			GestureClass classZX = new GestureClass(tempClass.getName());
			// If the gesture class contains a sample descriptor
			if (tempClass.getDescriptor(Sample3DDescriptor.class) != null) {
				SampleDescriptor descXY = new SampleDescriptor();
				SampleDescriptor descYZ = new SampleDescriptor();
				SampleDescriptor descZX = new SampleDescriptor();
				// Iterate through samples
				Iterator<Gesture<RecordedGesture3D>> sampleIter = tempClass
						.getDescriptor(Sample3DDescriptor.class).getSamples()
						.iterator();
				while (sampleIter.hasNext()) {
					GestureSample3D tempSample = (GestureSample3D) sampleIter
							.next();
					// Split to planes
					List<Gesture<Note>> planes = tempSample.splitToPlanes();
					// Add each plane to its own descriptor
					descXY.addSample(planes.get(0));
					descYZ.addSample(planes.get(1));
					descZX.addSample(planes.get(2));
				}
				// Add descriptors to classes
				classXY.addDescriptor(descXY);
				classYZ.addDescriptor(descYZ);
				classZX.addDescriptor(descZX);
			}
			// Add classes to sets
			setXY.addGestureClass(classXY);
			setYZ.addGestureClass(classYZ);
			setZX.addGestureClass(classZX);
		}

	}

}
