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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.sigtec.ink.Note;
import org.sigtec.util.Constant;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmException.ExceptionType;
import org.ximtec.igesture.algorithm.feature.F1;
import org.ximtec.igesture.algorithm.feature.F10;
import org.ximtec.igesture.algorithm.feature.F11;
import org.ximtec.igesture.algorithm.feature.F12;
import org.ximtec.igesture.algorithm.feature.F13;
import org.ximtec.igesture.algorithm.feature.F14;
import org.ximtec.igesture.algorithm.feature.F15;
import org.ximtec.igesture.algorithm.feature.F16;
import org.ximtec.igesture.algorithm.feature.F17;
import org.ximtec.igesture.algorithm.feature.F18;
import org.ximtec.igesture.algorithm.feature.F19;
import org.ximtec.igesture.algorithm.feature.F2;
import org.ximtec.igesture.algorithm.feature.F21;
import org.ximtec.igesture.algorithm.feature.F3;
import org.ximtec.igesture.algorithm.feature.F4;
import org.ximtec.igesture.algorithm.feature.F5;
import org.ximtec.igesture.algorithm.feature.F6;
import org.ximtec.igesture.algorithm.feature.F7;
import org.ximtec.igesture.algorithm.feature.F8;
import org.ximtec.igesture.algorithm.feature.F9;
import org.ximtec.igesture.algorithm.rubine.RubineAlgorithm;
import org.ximtec.igesture.algorithm.rubine.RubineConfiguration;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.SampleDescriptor3D;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

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
		return Rubine3DConfiguration.getDefaultConfiguration().get(
				parameterName);
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
	 * Creates a configuration for the recogniser, based on the plane name
	 * provided
	 * 
	 * @param plane
	 *            The name of the plane for which the configuration should be
	 *            created
	 * @return The configuration object for the recogniser of the plane
	 */
	private Configuration createConfiguration(String plane) {
		// Configuration objects
		Configuration recogniserConfig = new Configuration();
		RubineConfiguration rubineConfig = null;

		// System.err.println("PLANE: " + plane);

		// Include the Rubine Algorithm
		recogniserConfig.addAlgorithm(RubineAlgorithm.class.getName());
		// Check for which plane the configuration should be
		if (plane.equals("XY")) {
			// Add Gesture Set
			recogniserConfig.addGestureSet(this.setXY);
			rubineConfig = rubine3dConfig.getXyConfiguration();
		}
		if (plane.equals("YZ")) {
			// Add Gesture Set
			recogniserConfig.addGestureSet(this.setYZ);
			rubineConfig = rubine3dConfig.getYzConfiguration();
		}
		if (plane.equals("ZX")) {
			// Add Gesture Set
			recogniserConfig.addGestureSet(this.setZX);
			rubineConfig = rubine3dConfig.getZxConfiguration();
		}
		// else {
		// System.err
		// .println("Rubine3DAlgorithm.createConfiguration(): Please provide a valid plane name.");
		// return null;
		// }

		// System.err.println("RUBINE CONFIG: " + rubineConfig);

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
		recogniserConfig.addParameter(RubineAlgorithm.class.getName(),
				RubineConfiguration.Config.FEATURE_LIST.name(), F1.class
						.getName()
						+ Constant.COMMA
						+ F2.class.getName()
						+ Constant.COMMA
						+ F3.class.getName()
						+ Constant.COMMA
						+ F4.class.getName()
						+ Constant.COMMA
						+ F5.class.getName()
						+ Constant.COMMA
						+ F6.class.getName()
						+ Constant.COMMA
						+ F7.class.getName()
						+ Constant.COMMA
						+ F8.class.getName()
						+ Constant.COMMA
						+ F9.class.getName()
						+ Constant.COMMA
						+ F10.class.getName()
						+ Constant.COMMA
						+ F11.class.getName()
						+ Constant.COMMA
						+ F12.class.getName()
						+ Constant.COMMA
						+ F13.class.getName()
						+ Constant.COMMA
						+ F14.class.getName()
						+ Constant.COMMA
						+ F15.class.getName()
						+ Constant.COMMA
						+ F16.class.getName()
						+ Constant.COMMA
						+ F17.class.getName()
						+ Constant.COMMA
						+ F18.class.getName()
						+ Constant.COMMA
						+ F19.class.getName()
						+ Constant.COMMA
						+ F21.class.getName());

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
			GestureClass classYZ = new GestureClass(tempClass.getName());
			GestureClass classZX = new GestureClass(tempClass.getName());
			// If the gesture class contains a sample descriptor
			if (tempClass.getDescriptor(SampleDescriptor3D.class) != null) {		
				SampleDescriptor descXY = new SampleDescriptor();
				SampleDescriptor descYZ = new SampleDescriptor();
				SampleDescriptor descZX = new SampleDescriptor();
				// Iterate through samples
				Iterator<Gesture<RecordedGesture3D>> sampleIter = tempClass
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

}
