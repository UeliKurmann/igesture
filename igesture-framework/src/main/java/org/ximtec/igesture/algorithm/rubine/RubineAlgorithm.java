/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Implementation of the Rubine algorithm.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 16, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.rubine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math.linear.InvalidMatrixException;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.sigtec.ink.Note;
import org.sigtec.util.Constant;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.SampleBasedAlgorithm;
import org.ximtec.igesture.algorithm.AlgorithmException.ExceptionType;
import org.ximtec.igesture.algorithm.feature.FeatureException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.util.DoubleVector;
import org.ximtec.igesture.util.GestureTool;


/**
 * Implementation of the Rubine algorithm.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class RubineAlgorithm extends SampleBasedAlgorithm {

   private static final Logger LOGGER = Logger.getLogger(RubineAlgorithm.class
         .getName());

   private static final String RESULT = "Result: ";

   private static final String NO_RESULT = "No result";

   private static final String DISTANCE = "Distance: ";

   private static final String PROBABILITY = "Probability: ";

   private static final String COMPUTATION_FAILED = "Computation failed because of NaN fields in the matrix";

   /**
    * the common covariant matrix for the set
    */
   private RealMatrix matrix;

   /**
    * inverse of the common covariant matrix
    */
   private RealMatrix inverse;

   private RubineConfiguration rubineConfig;

   private Map<GestureClass, GestureClassHelper> helpers;

   /**
    * the gesture set we are working on
    */
   GestureSet gestureSet;

   private Executor threadPool;


   /**
    * Default constructor
    */
   public RubineAlgorithm() {
      super();
      LOGGER.setLevel(Level.SEVERE);
      helpers = new HashMap<GestureClass, GestureClassHelper>();
      threadPool = Executors.newFixedThreadPool(3);
   }


   public ResultSet recognise(Gesture< ? > gesture) throws AlgorithmException {
      ResultSet resultSet = new ResultSet();
      Note note = null;

      if (gesture instanceof GestureSample) {
         note = ((GestureSample)gesture).getGesture();
      }

      if (isApplicable(note)) {

         try {
            GestureSampleHelper helper = new GestureSampleHelper(note,
                  rubineConfig);

            resultSet = classify(helper.getFeatureVector());
         }
         catch (FeatureException exception) {
            throw new AlgorithmException(ExceptionType.Recognition);
         }

         resultSet.setGesture(gesture);

         if (resultSet.getResult() != null) {
            LOGGER.info(RESULT
                  + resultSet.getResult().getGestureClass().getName());
         }
         else {
            LOGGER.info(NO_RESULT);
         }
      }

      return resultSet;
   } // recognise


   public void init(Configuration config) throws AlgorithmException {
      this.rubineConfig = new RubineConfiguration(config);
      preprocess(GestureTool.combine(config.getGestureSets()));
   } // init


   private void preprocess(GestureSet gestureSet) throws AlgorithmException {
      this.gestureSet = gestureSet;
      CountDownLatch latch = new CountDownLatch(gestureSet.size());

      for (GestureClass gestureClass : gestureSet.getGestureClasses()) {
         GestureClassHelper helper = new GestureClassHelper(gestureClass,
               rubineConfig, latch);
         helpers.put(gestureClass, helper);
         threadPool.execute(helper);
      }

      try {
         latch.await();
         // Computes the common covariance matrix
         this.matrix = getCovarianceMatrix();
         inverse = matrix.inverse();
      }
      catch (InterruptedException e) {
         throw new AlgorithmException(
               AlgorithmException.ExceptionType.Initialisation, e);
      }
      catch (InvalidMatrixException e) {
         e.printStackTrace();
         throw new AlgorithmException(
               AlgorithmException.ExceptionType.Initialisation, e);
      }

      computeWeights();
   } // preprocess


   /**
    * Computes the common covariance matrix of the set.
    * 
    * @return the common covariance matrix of the set.
    */
   private RealMatrix getCovarianceMatrix() {
      int dim = rubineConfig.getNumberOfFeatures();
      double[][] commonCovMatrix = new double[dim][dim];

      for (int i = 0; i < dim; i++) {

         for (int j = 0; j < dim; j++) {
            double dividend = 0;

            for (GestureClass gestureClass : gestureSet.getGestureClasses()) {
               RealMatrix covarianceMatrix = helpers.get(gestureClass)
                     .getCovarianceMatrix();

               dividend += covarianceMatrix.getEntry(i, j)
                     / (helpers.get(gestureClass).getNumberOfSamples() - 1);
            }

            double divisor = -gestureSet.size();

            for (GestureClass gestureClass : gestureSet.getGestureClasses()) {
               divisor += helpers.get(gestureClass).getNumberOfSamples();
            }

            commonCovMatrix[i][j] = dividend / divisor;
         }

      }

      return new RealMatrixImpl(commonCovMatrix);
   } // getCovarianceMatrix


   /**
    * Computes the weights per class.
    * 
    */
   private void computeWeights() throws AlgorithmException {
      for (GestureClassHelper helper : helpers.values()) {
         helper.computeWeights(inverse);
      }

   } // computeWeights


   /**
    * Classification Algorithm
    * 
    */
   private ResultSet classify(DoubleVector inputFeatureVector) {
      double max = -Double.MAX_VALUE;
      GestureClass classifiedGesture = null;
      HashMap<GestureClass, Double> classifiers = new HashMap<GestureClass, Double>();

      for (GestureClassHelper helper : helpers.values()) {

         DoubleVector weightVector = helper.getWeights();
         double v = helper.getInitialWeight();

         for (int i = 0; i < inputFeatureVector.size(); i++) {
            v += weightVector.get(i) * inputFeatureVector.get(i);
         }

         LOGGER.info(helper.getGestureClass().getName() + Constant.COLON_BLANK
               + v);
         classifiers.put(helper.getGestureClass(), v);

         if (v > max) {
            max = v;
            classifiedGesture = helper.getGestureClass();
         }

         if (Double.isNaN(v)) {
            LOGGER.warning(COMPUTATION_FAILED);
            return new ResultSet();
         }

      }

      // probability
      double divisor = 0;

      for (GestureClass gestureClass : classifiers.keySet()) {
         divisor += Math.exp(classifiers.get(gestureClass)
               - classifiers.get(classifiedGesture));
      }

      double probability = 1.0 / divisor;

      // outliers
      double distance = getMahalanobisDistance(classifiedGesture,
            inputFeatureVector);

      ResultSet resultSet = new ResultSet();

      if (probability >= rubineConfig.getProbability()
            && distance <= rubineConfig.getMahalanobisDistance()) {
         resultSet.addResult(new Result(classifiedGesture, 1));
      }

      LOGGER.info(DISTANCE + distance);
      LOGGER.info(PROBABILITY + probability);
      return resultSet;
   } // classify


   /**
    * Computes the mahalanobis distance. This distance is used to reject
    * outliers.
    * 
    * @param gestureClass the gesture class to be used.
    * @param inputVector the input vector the distance has to be computed for.
    * @return the mahalanobis distance.
    */
   private double getMahalanobisDistance(GestureClass gestureClass,
         DoubleVector inputVector) {
      double result = 0;
      DoubleVector meanVector = helpers.get(gestureClass).getMeanFeatureVector();

      for (int j = 0; j < rubineConfig.getNumberOfFeatures(); j++) {

         for (int k = 0; k < rubineConfig.getNumberOfFeatures(); k++) {
            result += inverse.getEntry(j, k)
                  * (inputVector.get(j) - meanVector.get(j))
                  * (inputVector.get(k) - meanVector.get(k));
         }

      }

      return result;
   } // getMahalanobisDistance


   public RubineConfiguration.Config[] getConfigParameters() {
      return RubineConfiguration.Config.values();
   } // getConfigParameters


   /**
    * Tests if the gesture can be recognised.
    * @param gesture the gesture to be checked for.
    * @return true if the gesture can be recognised, false otherwise.
    */
   private boolean isApplicable(Note gesture) {
      return gesture != null
            && gesture.getPoints().size() >= rubineConfig
                  .getMinimalNumberOfPoints();
   }


   @Override
   public String getDefaultParameterValue(String parameterName) {
      return RubineConfiguration.getDefaultConfiguration().get(parameterName);
   } // getDefaultParameterValue


	@Override
	public String getType() {
		return org.ximtec.igesture.util.Constant.TYPE_2D;
	}

}
