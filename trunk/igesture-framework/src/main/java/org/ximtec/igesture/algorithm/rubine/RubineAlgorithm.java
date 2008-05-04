/*
 * @(#)RubineAlgorithm.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.rubine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math.linear.InvalidMatrixException;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.sigtec.ink.Note;
import org.sigtec.util.Constant;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.algorithm.AlgorithmTool;
import org.ximtec.igesture.algorithm.SampleBasedAlgorithm;
import org.ximtec.igesture.algorithm.AlgorithmException.ExceptionType;
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
import org.ximtec.igesture.algorithm.feature.FeatureException;
import org.ximtec.igesture.algorithm.feature.FeatureTool;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.util.DoubleVector;
import org.ximtec.igesture.util.GestureTool;
import org.ximtec.igesture.util.VectorTools;


/**
 * Implementation of the Rubine algorithm.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class RubineAlgorithm extends SampleBasedAlgorithm {

   private static final Logger LOGGER = Logger.getLogger(AlgorithmFactory.class
         .getName());

   private static final String DEFAULT_MAHALANOBIS_DISTANCE = "100000";

   private static final String DEFAULT_PROBABILITY = "0.95";

   private static final String DEFAULT_MIN_DISTANCE = "1";

   private static final String RESULT = "Result: ";

   private static final String NO_RESULT = "No result";

   private static final String DISTANCE = "Distance: ";

   private static final String PROBABILITY = "Probability: ";

   private static final String COMPUTATION_FAILED = "Computation failed because of NaN fields in the matrix";

   private int minimalNumberOfPoints;

   /**
    * data structure for storing the samples per gesture class
    */
   private HashMap<GestureClass, List<Gesture<Note>>> samples;

   /**
    * data structure for storing the feature vector per sample
    */
   private HashMap<Gesture<Note>, DoubleVector> sampleFeatureVector;

   /**
    * data structure for storing the mean feature vector per gesture class
    */
   private HashMap<GestureClass, DoubleVector> meanFeatureVector;

   /**
    * data structure for storing the weight vector per gesture class
    */
   private HashMap<GestureClass, DoubleVector> weightsVector;

   /**
    * data structure for storing the covariant matrix per gesture class
    */
   private HashMap<GestureClass, RealMatrix> covMatrices;

   /**
    * data structure for storing the initial weight per gesture class
    */
   private HashMap<GestureClass, Double> initialWeight;

   /**
    * the common covariant matrix for the set
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
      MIN_DISTANCE, FEATURE_LIST, MAHALANOBIS_DISTANCE, PROBABILITY
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
       * Parameter default values
       */
      DEFAULT_CONFIGURATION
            .put(Config.MIN_DISTANCE.name(), DEFAULT_MIN_DISTANCE);
      DEFAULT_CONFIGURATION.put(Config.MAHALANOBIS_DISTANCE.name(),
            DEFAULT_MAHALANOBIS_DISTANCE);
      DEFAULT_CONFIGURATION.put(Config.PROBABILITY.name(), DEFAULT_PROBABILITY);
      DEFAULT_CONFIGURATION.put(Config.FEATURE_LIST.name(), F1.class.getName()
            + Constant.COMMA + F2.class.getName() + Constant.COMMA
            + F3.class.getName() + Constant.COMMA + F4.class.getName()
            + Constant.COMMA + F5.class.getName() + Constant.COMMA
            + F6.class.getName() + Constant.COMMA + F7.class.getName()
            + Constant.COMMA + F8.class.getName() + Constant.COMMA
            + F9.class.getName() + Constant.COMMA + F10.class.getName());
      LOGGER.setLevel(Level.SEVERE);
   }


   /**
    * Default constructor
    */
   public RubineAlgorithm() {
      super();
   }


   public ResultSet recognise(Gesture<?> gesture) throws AlgorithmException {

      ResultSet resultSet = new ResultSet();
      Note note = null;
      if(gesture instanceof GestureSample){
         note = ((GestureSample)gesture).getGesture();
      }
      
      if (isApplicable(note)) {

         try {
            resultSet = classify(computeFeatureVector(note, featureList));
         }
         catch (FeatureException exception) {
            throw new AlgorithmException(ExceptionType.Recognition);
         }

         resultSet.setNote(note);

         if (resultSet.getResult() != null) {
            LOGGER.info(RESULT
                  + resultSet.getResult().getGestureClass().getName());
         }
         else {
            LOGGER.info(NO_RESULT);
         }
      }

      fireEvent(resultSet);
      return resultSet;
   } // recognise


   public void init(Configuration config) throws AlgorithmException {
      Map<String, String> parameters = config.getParameters(this
            .getClass().getCanonicalName());
      minDistance = AlgorithmTool.getDoubleParameterValue(Config.MIN_DISTANCE
            .name(), parameters, DEFAULT_CONFIGURATION);
      LOGGER.info(Config.MIN_DISTANCE + Constant.COLON_BLANK + minDistance);

      mahalanobisDistance = AlgorithmTool.getDoubleParameterValue(
            Config.MAHALANOBIS_DISTANCE.name(), parameters,
            DEFAULT_CONFIGURATION);
      LOGGER.info(Config.MAHALANOBIS_DISTANCE + Constant.COLON_BLANK
            + mahalanobisDistance);

      String featureNames = AlgorithmTool.getParameterValue(
            Config.FEATURE_LIST.name(), parameters, DEFAULT_CONFIGURATION);
      LOGGER.info(Config.FEATURE_LIST + Constant.COLON_BLANK + featureNames);

      featureList = FeatureTool.createFeatureList(featureNames).toArray(
            new Feature[0]);

      minimalNumberOfPoints = FeatureTool
            .computeMinimalNumberOfRequiredPoints(featureList);
      LOGGER.info("Minimal required points: " + minimalNumberOfPoints);

      probability = AlgorithmTool.getDoubleParameterValue(Config.PROBABILITY
            .name(), parameters, DEFAULT_CONFIGURATION);
      LOGGER.info(Config.PROBABILITY + Constant.COLON_BLANK + probability);

      addEventManagerListener(config.getEventManager());

      preprocess(GestureTool.combine(config.getGestureSets()));
   } // init


   private void preprocess(GestureSet gestureSet) throws AlgorithmException {
      this.gestureSet = gestureSet;

      // make the samples directly available for each gesture class
      this.samples = new HashMap<GestureClass, List<Gesture<Note>>>();

      for (GestureClass gestureClass : gestureSet.getGestureClasses()) {
         this.samples.put(gestureClass, getSamples(gestureClass));
      }

      // Computes the Feature Vectors for each gesture class and its samples
      this.sampleFeatureVector = new HashMap<Gesture<Note>, DoubleVector>();
      this.meanFeatureVector = new HashMap<GestureClass, DoubleVector>();
      this.covMatrices = new HashMap<GestureClass, RealMatrix>();

      for (GestureClass gestureClass : gestureSet.getGestureClasses()) {
         try {
            computeFeatureVectors(gestureClass, featureList);
         }
         catch (FeatureException exception) {
            throw new AlgorithmException(ExceptionType.Initialisation, exception);
         }
         this.covMatrices.put(gestureClass,
               getCovarianceMatrixPerClass(gestureClass));

      }

      // Computes the common covariance matrix
      this.matrix = getCovarianceMatrix();

      // Computes weights
      computeWeights();
   } // preprocess


   /**
    * Iterates over each sample of the gesture class and computes the feature
    * vector. in a second step the mean feature vector of the samples is
    * computed. Both vectors are stored in the corresponding datastructure.
    * 
    * @param gestureClass the gesture class.
    * @param featureList the feature list.
    */
   private void computeFeatureVectors(GestureClass gestureClass,
         Feature[] featureList) throws FeatureException {

      // computes the feature vector per class
      List<DoubleVector> vectors = new ArrayList<DoubleVector>();

      for (Gesture<Note> sample : samples.get(gestureClass)) {
         try{
            /**
             * FIXME
             * This method may throw a FeatureException. If an exception occurs,
             * the vector is not added to the list BUT we should go further to
             * the next sample. Before this refactoring, the process was interrupted
             * and therefore the algorithm can't be instantiated as soon as one sample
             * does not fulfill the requirements defined in the features. 
             */
            DoubleVector vector = computeFeatureVector(sample.getGesture(), featureList);
            sampleFeatureVector.put(sample, vector);
            if(VectorTools.hasValidValues(vector)){
               vectors.add(vector);
            }
         }catch(FeatureException exception){
            LOGGER.warning("Could not compute the Feature Vector.");
         }
      }

      // computes the mean vector
      /**
       * FIXME
       * The if statement is inserted to guarantee a minimal number of feature vectors. It has
       * to be checked, if 1 vector is enough. Maybe this is a mathematical issue. 
       */
      if(vectors.size() > 0){
         meanFeatureVector.put(gestureClass, VectorTools.mean(vectors));
      }else{
         throw new FeatureException("There are not enough samples for Gesture Class"+gestureClass.getName());
      }
   } // computeFeatureVectors


   /**
    * Computes the feature vector for a given sample. The features to compute are
    * passed as an array. The method returns a double vector representing the
    * feature vector.
    * 
    * @param sample the sample.
    * @param featureList the features to compute.
    * @return the feature vector for the given sample.
    */
   private DoubleVector computeFeatureVector(Note note, Feature[] featureList)
         throws FeatureException {

      // clone the note to avoid side effects
      Note clone = (Note)note.clone();
      clone.scaleTo(200, 200);

      // filter the note using the min distance
      clone.filter(minDistance);

      // create the feature vector
      DoubleVector featureVector = new DoubleVector(featureList.length);

      for (int i = 0; i < featureList.length; i++) {
         featureVector.set(i, featureList[i].compute(clone));
      }

      LOGGER.info(featureVector.toString());
      return featureVector;
   } // computeFeatureVector


   /**
    * Computes the covariance matrix for a given gesture class. The covariance
    * matrix is cached.
    * 
    * @param gestureClass the gesture class to compute the covariance matrix.
    * @return the covariance matrix of the gesture class.
    */
   private RealMatrix getCovarianceMatrixPerClass(GestureClass gestureClass) {
      RealMatrix realMatrix = covMatrices.get(gestureClass);

      if (realMatrix == null) {
         int numOfFeatures = featureList.length;
         DoubleVector meanVector = meanFeatureVector.get(gestureClass);
         double[][] matrix = new double[numOfFeatures][numOfFeatures];

         for (int i = 0; i < numOfFeatures; i++) {

            for (int j = 0; j < numOfFeatures; j++) {
               double sum = 0;

               for (Gesture<Note> sample : samples.get(gestureClass)) {
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

      return realMatrix;
   } // getCovarianceMatrixPerClass


   /**
    * Computes the common covariance matrix of the set.
    * 
    * @return the common covariance matrix of the set.
    */
   private RealMatrix getCovarianceMatrix() {
      int dim = featureList.length;
      double[][] commonCovMatrix = new double[dim][dim];

      for (int i = 0; i < dim; i++) {
         for (int j = 0; j < dim; j++) {
            double dividend = 0;

            for (GestureClass gestureClass : gestureSet.getGestureClasses()) {
               dividend += (this.getCovarianceMatrixPerClass(gestureClass))
                     .getEntry(i, j)
                     / (samples.get(gestureClass).size() - 1);
            }

            double divisor = -gestureSet.size();

            for (GestureClass gestureClass : gestureSet.getGestureClasses()) {
               divisor += samples.get(gestureClass).size();
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
      weightsVector = new HashMap<GestureClass, DoubleVector>();
      initialWeight = new HashMap<GestureClass, Double>();

      
      try {
         inverse = matrix.inverse();
      }
      catch (InvalidMatrixException e) {
         throw new AlgorithmException(
               AlgorithmException.ExceptionType.Initialisation, e);
      }

      // compute the weights per class
      int classIndex = 0; 

      for (GestureClass gestureClass : gestureSet.getGestureClasses()) {
         DoubleVector meanVector = meanFeatureVector.get(gestureClass);
         DoubleVector weightVector = new DoubleVector(meanVector.size());

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

         // TODO inline in for loop above? after weightVector.set(j, wci)
         for (int f = 0; f < meanVector.size(); f++) {
            wc0 += weightVector.get(f) * meanVector.get(f);
         }

         this.initialWeight.put(gestureClass, -0.5 * wc0);
         classIndex++;
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

      for (GestureClass currentGestureClass : gestureSet
            .getGestureClasses()) {
         DoubleVector weightVector = this.weightsVector
               .get(currentGestureClass);
         // FIXME: Cleanup
         assert (weightVector.size() == inputFeatureVector.size());
         double v = initialWeight.get(currentGestureClass);

         for (int i = 0; i < weightVector.size(); i++) {
            v += weightVector.get(i) * inputFeatureVector.get(i);
         }

         LOGGER.info(currentGestureClass.getName() + Constant.COLON_BLANK + v);
         classifiers.put(currentGestureClass, v);

         if (v > max) {
            max = v;
            classifiedGesture = currentGestureClass;
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

      if (probability >= this.probability
            && distance <= this.mahalanobisDistance) {
         resultSet.addResult(new Result(classifiedGesture, 1));
         LOGGER.info(DISTANCE + distance);
      }
      else {
         LOGGER.info(DISTANCE + distance);
         LOGGER.info(PROBABILITY + probability);
      }

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
      DoubleVector meanVector = meanFeatureVector.get(gestureClass);

      for (int j = 0; j < featureList.length; j++) {

         for (int k = 0; k < featureList.length; k++) {
            result += inverse.getEntry(j, k)
                  * (inputVector.get(j) - meanVector.get(j))
                  * (inputVector.get(k) - meanVector.get(k));
         }
      }

      return result;
   } // getMahalanobisDistance


   public Config[] getConfigParameters() {
      return Config.values();
   } // getConfigParameters


   /**
    * Tests if the note can be recognised
    * @param note
    * @return
    */
   private boolean isApplicable(Note note) {
      return note != null && note.getPoints().size() >= minimalNumberOfPoints;
   }

}
