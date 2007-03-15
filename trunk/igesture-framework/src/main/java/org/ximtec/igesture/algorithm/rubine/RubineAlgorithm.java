/*
 * @(#)RubineAlgorithm.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Implementation of the Rubine algorithm
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.rubine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math.linear.InvalidMatrixException;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.sigtec.ink.Note;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.algorithm.AlgorithmTool;
import org.ximtec.igesture.algorithm.SampleBasedAlgorithm;
import org.ximtec.igesture.algorithm.feature.F1;
import org.ximtec.igesture.algorithm.feature.F10;
import org.ximtec.igesture.algorithm.feature.F2;
import org.ximtec.igesture.algorithm.feature.F3;
import org.ximtec.igesture.algorithm.feature.F4;
import org.ximtec.igesture.algorithm.feature.F5;
import org.ximtec.igesture.algorithm.feature.F6;
import org.ximtec.igesture.algorithm.feature.F7;
import org.ximtec.igesture.algorithm.feature.F8;
import org.ximtec.igesture.algorithm.feature.F9;
import org.ximtec.igesture.algorithm.feature.Feature;
import org.ximtec.igesture.algorithm.feature.FeatureTools;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.DoubleVector;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.core.VectorTools;
import org.ximtec.igesture.util.GestureTool;


public class RubineAlgorithm extends SampleBasedAlgorithm {

   private static final Logger LOGGER = Logger.getLogger(AlgorithmFactory.class
         .getName());

   /**
    * datastructure for storing the samples per gesture class
    */
   private HashMap<GestureClass, List<GestureSample>> samples;

   /**
    * datastructure for storing the feature vector per sample
    */
   private HashMap<GestureSample, DoubleVector> sampleFeatureVector;

   /**
    * datastructure for storing the mean feature vector per gesture class
    */
   private HashMap<GestureClass, DoubleVector> meanFeatureVector;

   /**
    * datastructure for storing the weight vector per gesture class
    */
   private HashMap<GestureClass, DoubleVector> weightsVector;

   /**
    * datastructure for storing the coovariant matrix per gesture class
    */
   private HashMap<GestureClass, RealMatrix> covMatrices;

   /**
    * datastructure for storing the initial weight per gesture class
    */
   private HashMap<GestureClass, Double> initialWeight;

   /**
    * the common coovariant matrix for the set
    */
   private RealMatrix matrix;

   /**
    * inverse of the common covariant matrix
    */
   private RealMatrix inverse;

   /**
    * the gesture set we are working on
    */
   GestureSet gestureSet;

   public enum Config {
      MIN_DISTANCE, FEATURE_LIST, MAHALANOBIS_DISTANCE, PROPABILITY
   }

   /**
    * the list of features used during the recognising process
    */
   private Feature[] featureList;

   private double minDistance;

   private double mahalanobisDistance;

   private double probability;

   /**
    * Set default parameter values
    */
   static {
      /**
       * Parameter Default Values
       */
      DEFAULT_CONFIGURATION.put(Config.MIN_DISTANCE.name(), "1");

      DEFAULT_CONFIGURATION.put(Config.MAHALANOBIS_DISTANCE.name(), "100");
      DEFAULT_CONFIGURATION.put(Config.PROPABILITY.name(), "0.95");
      DEFAULT_CONFIGURATION.put(Config.FEATURE_LIST.name(), F1.class.getName()
            + "," + F2.class.getName() + "," + F3.class.getName() + ","
            + F4.class.getName() + "," + F5.class.getName() + ","
            + F6.class.getName() + "," + F7.class.getName() + ","
            + F8.class.getName() + "," + F9.class.getName() + ","
            + F10.class.getName());

      LOGGER.setLevel(Level.WARNING);
   }


   /**
    * Empty default constructor
    */
   public RubineAlgorithm() {

   }


   public ResultSet recognise(Note note) {
      final ResultSet resultSet = classify(computeFeatureVector(note,
            featureList));
      resultSet.setNote(note);

      if (resultSet.getResult() != null) {
         LOGGER.info("Result: "
               + resultSet.getResult().getGestureClass().getName());
      }
      else {
         LOGGER.info("No result");
      }

      fireEvent(resultSet);
      return resultSet;
   }


   public void init(Configuration config) throws AlgorithmException {
      final HashMap<String, String> parameters = config
            .getAlgorithmParameters(this.getClass().getCanonicalName());

      minDistance = AlgorithmTool.getDoubleParameterValue(Config.MIN_DISTANCE
            .name(), parameters, DEFAULT_CONFIGURATION);
      LOGGER.info(Config.MIN_DISTANCE + ": " + minDistance);

      mahalanobisDistance = AlgorithmTool.getDoubleParameterValue(
            Config.MAHALANOBIS_DISTANCE.name(), parameters,
            DEFAULT_CONFIGURATION);
      LOGGER.info(Config.MAHALANOBIS_DISTANCE + ": " + mahalanobisDistance);

      final String featureNames = AlgorithmTool.getParameterValue(
            Config.FEATURE_LIST.name(), parameters, DEFAULT_CONFIGURATION);
      LOGGER.info(Config.FEATURE_LIST + ": " + featureNames);
      featureList = FeatureTools.createFeatureList(featureNames).toArray(
            new Feature[0]);

      probability = AlgorithmTool.getDoubleParameterValue(Config.PROPABILITY
            .name(), parameters, DEFAULT_CONFIGURATION);
      LOGGER.info(Config.PROPABILITY + ": " + probability);

      addEventManagerListener(config.getEventManager());

      preprocess(GestureTool.combine(config.getGestureSets()));
   }


   private void preprocess(GestureSet gestureSet) throws AlgorithmException {
      this.gestureSet = gestureSet;

      // make the samples directly available for each gesture class
      this.samples = new HashMap<GestureClass, List<GestureSample>>();
      for (final GestureClass gestureClass : gestureSet.getGestureClasses()) {
         this.samples.put(gestureClass, getSamples(gestureClass));
      }

      // Computes the Feature Vectors for each gesture class and its samples
      this.sampleFeatureVector = new HashMap<GestureSample, DoubleVector>();
      this.meanFeatureVector = new HashMap<GestureClass, DoubleVector>();
      this.covMatrices = new HashMap<GestureClass, RealMatrix>();
      for (final GestureClass gestureClass : gestureSet.getGestureClasses()) {
         computeFeatureVectors(gestureClass, featureList);
         this.covMatrices.put(gestureClass, getCovMatrixPerClass(gestureClass));
      }

      // Computes the common covarianz matrix
      this.matrix = getCovMatrix();

      // Computes weights
      computeWeights();
   }


   /**
    * Iterates over each sample of the gesture class and computes the feature
    * vector. in a second step the mean feature vector of the samples is
    * computed. both vectores are stored in the corresponding datastructure
    * 
    * @param gestureClass the gesture class
    * @param featureList the feature list
    */
   private void computeFeatureVectors(GestureClass gestureClass,
         Feature[] featureList) {

      // computes the feature vector per class
      final List<DoubleVector> vectors = new ArrayList<DoubleVector>();
      for (final GestureSample sample : samples.get(gestureClass)) {
         final DoubleVector vector = computeFeatureVector(sample.getNote(),
               featureList);
         sampleFeatureVector.put(sample, vector);
         vectors.add(vector);
      }
      // computes the mean vector
      meanFeatureVector.put(gestureClass, VectorTools.mean(vectors));
   }


   /**
    * Computes the feature vector for a given sample. The features to compute are
    * passed as an array. The method returns a DoubleVector representing the
    * feature vector.
    * 
    * @param sample the sample.
    * @param featureList the features to compute.
    * @return the feature vector for the given sample.
    */
   private DoubleVector computeFeatureVector(Note note, Feature[] featureList) {

      // clone the note to avoid side effects
      final Note clone = (Note) note.clone();
      clone.scaleTo(200, 200);

      // filter the note using the min distance
      clone.filter(minDistance);

      // create the feature vector
      final DoubleVector featureVector = new DoubleVector(featureList.length);
      
      for (int i = 0; i < featureList.length; i++) {
         featureVector.set(i, featureList[i].compute(clone));
      }

      LOGGER.info(featureVector.toString());
      return featureVector;
   } // computeFeatureVector


   /**
    * Computes the covarainz matrix for a given gesture class.
    * 
    * The covarianz matrix is cached.
    * 
    * @param gestureClass the gesture class to compute the cov matrix
    * @return the cov matrix of the gesture class
    */
   private RealMatrix getCovMatrixPerClass(GestureClass gestureClass) {
      RealMatrix realMatrix = covMatrices.get(gestureClass);
      if (realMatrix == null) {

         final int numOfFeatures = featureList.length;

         final DoubleVector meanVector = meanFeatureVector.get(gestureClass);

         final double[][] matrix = new double[numOfFeatures][numOfFeatures];

         for (int i = 0; i < numOfFeatures; i++) {
            for (int j = 0; j < numOfFeatures; j++) {
               double sum = 0;
               for (final GestureSample sample : samples.get(gestureClass)) {
                  sum += (sampleFeatureVector.get(sample).get(i) - meanVector
                        .get(i))
                        * (sampleFeatureVector.get(sample).get(j) - meanVector
                              .get(j));
               }
               matrix[i][j] = sum;
            }
         }
         realMatrix = new RealMatrixImpl(matrix);
      }

      // LOGGER.info(realMatrix.toString());

      return realMatrix;
   }


   /**
    * Computes the common coviranz matrix of the set
    * 
    * @return
    */
   private RealMatrix getCovMatrix() {
      final int dim = featureList.length;
      final double[][] commonCovMatrix = new double[dim][dim];

      for (int i = 0; i < dim; i++) {
         for (int j = 0; j < dim; j++) {

            double dividend = 0;
            for (final GestureClass gestureClass : gestureSet
                  .getGestureClasses()) {
               dividend += (this.getCovMatrixPerClass(gestureClass)).getEntry(i,
                     j)
                     / (samples.get(gestureClass).size() - 1);
            }
            double divisor = -gestureSet.size();
            for (final GestureClass gestureClass : gestureSet
                  .getGestureClasses()) {
               divisor += samples.get(gestureClass).size();
            }
            commonCovMatrix[i][j] = dividend / divisor;
         }
      }
      return new RealMatrixImpl(commonCovMatrix);
   }


   /**
    * Computes the weights per class
    * 
    */
   private void computeWeights() throws AlgorithmException {
      weightsVector = new HashMap<GestureClass, DoubleVector>();
      initialWeight = new HashMap<GestureClass, Double>();
      try {
         inverse = matrix.inverse();
      }
      catch (final InvalidMatrixException e) {
         throw new AlgorithmException(
               AlgorithmException.ExceptionType.Initialisation);
      }

      // compute the weights per class
      int classIndex = 0;
      for (final GestureClass gestureClass : gestureSet.getGestureClasses()) {
         final DoubleVector meanVector = meanFeatureVector.get(gestureClass);
         final DoubleVector weightVector = new DoubleVector(meanVector.size());
         for (int j = 0; j < meanVector.size(); j++) {
            double wci = 0;
            for (int i = 0; i < meanVector.size(); i++) {
               wci += inverse.getEntry(i, j) * meanVector.get(i);
            }
            weightVector.set(j, wci);
         }
         this.weightsVector.put(gestureClass, weightVector);

         // compute the initial weight
         double wc0 = 0;
         for (int f = 0; f < meanVector.size(); f++) {
            wc0 += weightVector.get(f) * meanVector.get(f);
         }
         this.initialWeight.put(gestureClass, -0.5 * wc0);
         classIndex++;
      }
   }


   /**
    * Classification Algorithm
    * ------------------------------------------------------
    */
   private ResultSet classify(DoubleVector inputFeatureVector) {
      double max = -Double.MAX_VALUE;
      GestureClass classifiedGesture = null;
      final HashMap<GestureClass, Double> classifiers = new HashMap<GestureClass, Double>();

      for (final GestureClass currentGestureClass : gestureSet
            .getGestureClasses()) {
         final DoubleVector weightVector = this.weightsVector
               .get(currentGestureClass);
         assert (weightVector.size() == inputFeatureVector.size());
         double v = initialWeight.get(currentGestureClass);
         for (int i = 0; i < weightVector.size(); i++) {
            v += weightVector.get(i) * inputFeatureVector.get(i);
         }
         LOGGER.info(currentGestureClass.getName() + ": " + v);
         classifiers.put(currentGestureClass, v);

         if (v > max) {
            max = v;
            classifiedGesture = currentGestureClass;
         }
         if (Double.isNaN(v)) {
            System.out.println("Bloed...");
         }
      }

      // probability
      double divisor = 0;
      for (final GestureClass gestureClass : classifiers.keySet()) {
         divisor += Math.exp(classifiers.get(gestureClass)
               - classifiers.get(classifiedGesture));
      }
      final double probability = 1.0 / divisor;

      // outliers
      final double distance = getMahalanobisDistance(classifiedGesture,
            inputFeatureVector);

      final ResultSet resultSet = new ResultSet();
      if (probability >= this.probability
            && distance <= this.mahalanobisDistance) {
         resultSet.addResult(new Result(classifiedGesture, 1));
         LOGGER.info("Distance: " + distance);
      }
      else {
         LOGGER.info("Distance: " + distance);
         LOGGER.info("Probability: " + probability);
      }

      return resultSet;
   }


   /**
    * Computes the Mahalanobis distance. this distance is used to reject
    * outliners.
    * 
    * @param gestureClass
    * @param inputVector
    * @return
    */
   private double getMahalanobisDistance(GestureClass gestureClass,
         DoubleVector inputVector) {
      double result = 0;
      final DoubleVector meanVector = meanFeatureVector.get(gestureClass);
      for (int j = 0; j < featureList.length; j++) {
         for (int k = 0; k < featureList.length; k++) {
            result += inverse.getEntry(j, k)
                  * (inputVector.get(j) - meanVector.get(j))
                  * (inputVector.get(k) - meanVector.get(k));
         }
      }
      return result;
   }


   public Enum[] getConfigParameters() {
      return Config.values();
   }

}
