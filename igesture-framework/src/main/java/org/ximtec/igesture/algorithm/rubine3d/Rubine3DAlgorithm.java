/*
 * @(#)$Id: Rubine3DAlgorithm.java
 *
 * Author		:	Arthur Vogels, arthur.vogels@gmail.com
 *                  
 *
 * Purpose		:   3 Dimensional use of the Rubine algorithm.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 05.01.2009		vogelsar	Initial Release
 * 22.05.2010		bpuype		Code cleanup and bug fixes
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.algorithm.rubine3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.sigtec.util.Constant;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.SampleBasedAlgorithm;
import org.ximtec.igesture.algorithm.AlgorithmException.ExceptionType;
import org.ximtec.igesture.algorithm.feature.Feature;
import org.ximtec.igesture.algorithm.rubine.RubineAlgorithm;
import org.ximtec.igesture.algorithm.rubine.RubineConfiguration;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.SampleDescriptor3D;
import org.ximtec.igesture.util.additions3d.Note3D;

public class Rubine3DAlgorithm extends SampleBasedAlgorithm/*implements Algorithm */{

	private static final Logger LOGGER = Logger.getLogger(Rubine3DAlgorithm.class.getName());
	
	private Rubine3DConfiguration rubine3dConfig;
	
	private static final int PLANE_XY = 0;
	private static final int PLANE_YZ = 1;
	private static final int PLANE_ZX = 2;

	private Map<GestureClass, GestureClass> gestureClassMapping;
	
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
		gestureClassMapping = new HashMap<GestureClass,GestureClass>();
	}

	@Override
	public Enum<?>[] getConfigParameters() {
		return Rubine3DConfiguration.Config.values();
	}

	@Override
	public String getDefaultParameterValue(String parameterName) {
		return Rubine3DConfiguration.getDefaultConfiguration().get(
				parameterName);
	}

	@Override
	public void init(Configuration configuration) throws AlgorithmException {
		LOGGER.info("Rubine3DAlgorithm.init()");
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
			throw new AlgorithmException(ExceptionType.Recognition);
		}
	}

	/**
	 * Recognizes a GestureSample3D by splitting it up into three 2D-planes (XY,
	 * YZ, ZX) and recognising these three 2D planes with the standard
	 * RubineAlgorithm. It returns a combined ResultSet, made up of the three
	 * ResultSets of the three planes, combined using weight factors.
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
		List<Double> weights = determinePlaneWeights();

		Configuration configXY = createConfiguration(PLANE_XY);
		Configuration configYZ = createConfiguration(PLANE_YZ);
		Configuration configZX = createConfiguration(PLANE_ZX);
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
			ResultSet rs = combineResultSets(sets, weights); 
			List<Result> results = rs.getResults();
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				Result result = (Result) iterator.next();
				result.setGestureClass(gestureClassMapping.get(result.getGestureClass()));
			}
			return rs; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a configuration for the recogniser, based on the plane name
	 * provided
	 * 
	 * @param plane
	 *            The name of the plane for which the configuration should be
	 *            created
	 * @return The configuration object for the recogniser of the plane
	 */
	private Configuration createConfiguration(int plane) {
		// Configuration objects
		Configuration recogniserConfig = new Configuration();
		RubineConfiguration rubineConfig = null;

		// Include the Rubine Algorithm
		recogniserConfig.addAlgorithm(RubineAlgorithm.class.getName());
		// Check for which plane the configuration should be
		switch(plane)
		{
		case PLANE_XY:
			// Add Gesture Set
			recogniserConfig.addGestureSet(this.setXY);
			rubineConfig = rubine3dConfig.getXyConfiguration();
			break;
		case PLANE_YZ:
			// Add Gesture Set
			recogniserConfig.addGestureSet(this.setYZ);
			rubineConfig = rubine3dConfig.getYzConfiguration();
			break;
		case PLANE_ZX:
			// Add Gesture Set
			recogniserConfig.addGestureSet(this.setZX);
			rubineConfig = rubine3dConfig.getZxConfiguration();
			break;
		default:
			//LOGGER.severe("Rubine3DAlgorithm.createConfiguration(): Please provide a valid plane name.");
			//return null;
		}

		// LOGGER.info("RUBINE CONFIG: " + rubineConfig);

		// Put parameters from rubineConfig into recogniserConfig
		recogniserConfig.addParameter(RubineAlgorithm.class.getName(),
				RubineConfiguration.Config.MAHALANOBIS_DISTANCE.name(), String
						.valueOf(rubineConfig.getMahalanobisDistance()));
		recogniserConfig.addParameter(RubineAlgorithm.class.getName(),
				RubineConfiguration.Config.MIN_DISTANCE.name(), String
						.valueOf(rubineConfig.getMinDistance()));
		recogniserConfig.addParameter(RubineAlgorithm.class.getName(),
				RubineConfiguration.Config.PROBABILITY.name(), String
						.valueOf(rubineConfig.getProbability()));
		
		Feature[] features = rubineConfig.getFeatureList();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < features.length; i++) {
			sb.append(features[i].getClass().getName());
			sb.append(Constant.COMMA);
		}

		recogniserConfig.addParameter(RubineAlgorithm.class.getName(), 
				RubineConfiguration.Config.FEATURE_LIST.name(), sb.toString());

		// Return the configuration
		return recogniserConfig;
	}

	/**
	 * Determines the weights of the planes from the Rubine3DConfiguration
	 * 
	 * @return A list with 3 weights. The first for the XY plane, second for the
	 *         YZ plane and third for the ZX plane
	 * @throws Exception
	 */
	private List<Double> determinePlaneWeights() throws AlgorithmException {
		List<Double> weights = new Vector<Double>();
		// Check if weights add up to 1
		if ((rubine3dConfig.getXyWeight() + rubine3dConfig.getYzWeight() + rubine3dConfig
				.getZxWeight()) > 1.01
				|| (rubine3dConfig.getXyWeight() + rubine3dConfig.getYzWeight() + rubine3dConfig
						.getZxWeight()) < 0.99) {
			System.err.println("Weights do not add up to 1");
			throw new AlgorithmException(
					AlgorithmException.ExceptionType.Initialisation);
		}
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
	 *             weights
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
		if (inputSet.getResults().size() > 0) {
			outputSet.addResult(inputSet.getResult(0));
			// Loop through inputSet and take accuracy
			for (int j = 0; j < inputSet.getResults().size(); j++) {
				boolean added = false;
				// Loop through output set and compare accuracies
				for (int k = 0; k < outputSet.getResults().size(); k++) {
					// If the accuracy is higher than the accuracy of the result
					// at
					// position k in outputset
					if (inputSet.getResult(j).getAccuracy() > outputSet
							.getResult(k).getAccuracy()) {
						// Insert the result and break the loop to prevent from
						// inserting multiple times
						outputSet.getResults().add(k, inputSet.getResult(j));
						added = true;
					}
				}
				// If not added because the accuracy is smaller than the
				// existing
				// ones, add at the end
				if (!added) {
					outputSet.getResults().add(inputSet.getResult(j));
				}
			}
		}
		// Return output variable
		return outputSet;
	}

	/**
	 * Splits GestureSet inputSet (which contains 3D gestures) into three
	 * separate gesture sets (containing 2D gestures) for the three planes
	 * 
	 * @param inputSet
	 *            The gesture set with 3D gestures
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
			gestureClassMapping.put(classXY, tempClass);
			GestureClass classYZ = new GestureClass(tempClass.getName());
			gestureClassMapping.put(classYZ, tempClass);
			GestureClass classZX = new GestureClass(tempClass.getName());
			gestureClassMapping.put(classZX, tempClass);
			// If the gesture class contains a sample descriptor
			if (tempClass.getDescriptor(SampleDescriptor3D.class) != null) {		
				SampleDescriptor descXY = new SampleDescriptor();
				SampleDescriptor descYZ = new SampleDescriptor();
				SampleDescriptor descZX = new SampleDescriptor();
				// Iterate through samples
				Iterator<Gesture<Note3D>> sampleIter = tempClass
						.getDescriptor(SampleDescriptor3D.class).getSamples()
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

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.algorithm.Algorithm#getType()
	 */
	@Override
	public int getType() {
		return org.ximtec.igesture.util.Constant.TYPE_3D;
	}

}
