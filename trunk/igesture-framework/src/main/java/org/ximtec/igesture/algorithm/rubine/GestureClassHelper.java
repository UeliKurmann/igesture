/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 11.06.2008			ukurmann	Initial Release
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.sigtec.ink.Note;
import org.ximtec.igesture.algorithm.feature.FeatureException;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.util.DoubleVector;
import org.ximtec.igesture.util.VectorTools;


/**
 * Comment
 * @version 1.0 11.06.2008
 * @author Ueli Kurmann
 */
public class GestureClassHelper implements Runnable {

   private static final Logger LOGGER = Logger
         .getLogger(GestureClassHelper.class.getName());

   private GestureClass gestureClass;
   private RubineConfiguration configuration;

   private List<Gesture<Note>> samples;
   private DoubleVector meanFeatureVector;

   /**
    * Data structure for storing the feature vector per sample.
    */
   private Map<Gesture<Note>, GestureSampleHelper> sampleFeatureVector;

   private RealMatrix realMatrix;

   private double initialWeight;

   private DoubleVector weightsVector;

   private CountDownLatch latch;


   public GestureClassHelper(GestureClass gestureClass,
         RubineConfiguration configuration, CountDownLatch latch) {
      this.gestureClass = gestureClass;
      this.samples = getSamples();
      this.configuration = configuration;
      this.sampleFeatureVector = new HashMap<Gesture<Note>, GestureSampleHelper>();
      this.latch = latch;
      LOGGER.setLevel(Level.SEVERE);
   }


   /**
    * Iterates over each sample of the gesture class and computes the feature
    * vector. in a second step the mean feature vector of the samples is
    * computed. Both vectors are stored in the corresponding data structure.
    * 
    * @param gestureClass the gesture class.
    * @param featureList the feature list.
    */
   private void computeFeatureVectors(GestureClass gestureClass)
         throws FeatureException {

      // computes the feature vector per class
      List<DoubleVector> vectors = new ArrayList<DoubleVector>();

      for (Gesture<Note> sample : samples) {
         try {
            /**
             * FIXME This method may throw a FeatureException. If an exception
             * occurs, the vector is not added to the list BUT we should go
             * further to the next sample. Before this refactoring, the process
             * was interrupted and therefore the algorithm can't be instantiated
             * as soon as one sample does not fulfill the requirements defined in
             * the features.
             */

            // FIXME correct?
            GestureSampleHelper helper = new GestureSampleHelper(sample
                  .getGesture(), configuration);
            sampleFeatureVector.put(sample, helper);

            if (VectorTools.hasValidValues(helper.getFeatureVector())) {
               vectors.add(helper.getFeatureVector());
            }
         }
         catch (FeatureException exception) {
            LOGGER.warning("Could not compute the Feature Vector.");
         }
      }

      // computes the mean vector
      /**
       * FIXME The if statement is inserted to guarantee a minimal number of
       * feature vectors. It has to be checked, if 1 vector is enough. Maybe this
       * is a mathematical issue.
       */
      if (vectors.size() > 0) {
         meanFeatureVector = VectorTools.mean(vectors);
      }
      else {
         throw new FeatureException(
               "There are not enough samples for Gesture Class"
                     + gestureClass.getName());
      }
   } // computeFeatureVectors


   /**
    * Returns a list of GestureSamples
    * @return a list of GestureSamples
    */
   private List<Gesture<Note>> getSamples() {
      SampleDescriptor descriptor = gestureClass
            .getDescriptor(SampleDescriptor.class);
      return descriptor.getSamples();
   }


   @Override
   public void run() {
      try {
         computeFeatureVectors(gestureClass);
      }
      catch (FeatureException exception) {
         exception.printStackTrace();
         // FIXME throw new AlgorithmException(ExceptionType.Initialisation,
         // exception);
      }

      getCovarianceMatrix();
      latch.countDown();
   }


   /**
    * Computes the covariance matrix for a given gesture class. The covariance
    * matrix is cached.
    * 
    * @param gestureClass the gesture class to compute the covariance matrix.
    * @return the covariance matrix of the gesture class.
    */
   public RealMatrix getCovarianceMatrix() {

      if (realMatrix == null) {
         int numOfFeatures = configuration.getNumberOfFeatures();
         double[][] matrix = new double[numOfFeatures][numOfFeatures];

         for (int i = 0; i < numOfFeatures; i++) {

            for (int j = 0; j < numOfFeatures; j++) {
               double sum = 0;

               for (Gesture< ? > sample : samples) {
                  try {
                     sum += (sampleFeatureVector.get(sample).getFeatureVector()
                           .get(i) - meanFeatureVector.get(i))
                           * (sampleFeatureVector.get(sample).getFeatureVector()
                                 .get(j) - meanFeatureVector.get(j));
                  }
                  catch (Exception e) {
                     e.printStackTrace();
                  }
               }
               matrix[i][j] = sum;
            }
         }

         realMatrix = new RealMatrixImpl(matrix);
      }

      return realMatrix;
   } // getCovarianceMatrixPerClass


   public DoubleVector getMeanFeatureVector() {
      return meanFeatureVector;
   } // getMeanFeatureVector


   public int getNumberOfSamples() {
      return samples.size();
   } // getNumberOfSamples


   public void computeWeights(RealMatrix inverse) {
      weightsVector = new DoubleVector(meanFeatureVector.size());

      for (int j = 0; j < meanFeatureVector.size(); j++) {
         double wci = 0;

         for (int i = 0; i < meanFeatureVector.size(); i++) {
            wci += inverse.getEntry(i, j) * meanFeatureVector.get(i);
         }

         weightsVector.set(j, wci);
      }

      // compute the initial weight
      double wc0 = 0;

      // TODO inline in for loop above? after weightVector.set(j, wci)
      for (int f = 0; f < meanFeatureVector.size(); f++) {
         wc0 += weightsVector.get(f) * meanFeatureVector.get(f);
      }

      this.initialWeight = -0.5 * wc0;
   }


   public double getInitialWeight() {
      return initialWeight;
   } // getInitialWeight


   public DoubleVector getWeights() {
      return weightsVector;
   } // getWeights


   public GestureClass getGestureClass() {
      return gestureClass;
   } // getGestureClass

}
