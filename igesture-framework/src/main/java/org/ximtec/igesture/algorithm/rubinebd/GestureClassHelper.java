/*
 * @(#)$Id: GestureClassHelper.java 736 2009-08-14 09:20:17Z kurmannu $
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


package org.ximtec.igesture.algorithm.rubinebd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math.linear.BigMatrix;
import org.apache.commons.math.linear.BigMatrixImpl;
import org.sigtec.ink.Note;
import org.ximtec.igesture.algorithm.feature.FeatureException;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.util.BDVectorTools;
import org.ximtec.igesture.util.BigDecimalVector;
import org.ximtec.igesture.util.DoubleVector;



/**
 * Comment
 * @version 1.0 11.06.2008
 * @author Ueli Kurmann
 */
public class GestureClassHelper implements Runnable{
   
   private static final Logger LOGGER = Logger.getLogger(GestureClassHelper.class.getName());
   
   private GestureClass gestureClass;
   private RubineConfiguration configuration;
   
   private List<Gesture<Note>> samples;
   private BigDecimalVector meanFeatureVector;
   /**
    * data structure for storing the feature vector per sample
    */
   private Map<Gesture<Note>, GestureSampleHelper> sampleFeatureVector;
   
   private BigMatrixImpl realMatrix;
   
   private BigDecimal initialWeight;
   
   private BigDecimalVector weightsVector;
   
   private CountDownLatch latch;
   
   public GestureClassHelper(GestureClass gestureClass, RubineConfiguration configuration, CountDownLatch latch){
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
   private void computeFeatureVectors(GestureClass gestureClass) throws FeatureException {

      // computes the feature vector per class
      List<BigDecimalVector> vectors = new ArrayList<BigDecimalVector>();

      for (Gesture<Note> sample : samples) {
         try{
            /**
             * FIXME
             * This method may throw a FeatureException. If an exception occurs,
             * the vector is not added to the list BUT we should go further to
             * the next sample. Before this refactoring, the process was interrupted
             * and therefore the algorithm can't be instantiated as soon as one sample
             * does not fulfill the requirements defined in the features. 
             */
           
            // FIXME correct?  
            GestureSampleHelper helper = new GestureSampleHelper(sample.getGesture(), configuration);
            sampleFeatureVector.put(sample, helper);
            
            if(BDVectorTools.hasValidValues(helper.getFeatureVector())){
               vectors.add(helper.getFeatureVector());
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
         meanFeatureVector =  BDVectorTools.mean(vectors);
      }else{
         throw new FeatureException("There are not enough samples for Gesture Class"+gestureClass.getName());
      }
   } // computeFeatureVectors
   
   /**
    * Returns a list of GestureSamples
    * @return a list of GestureSamples
    */
   private List<Gesture<Note>> getSamples(){
      SampleDescriptor descriptor = gestureClass.getDescriptor(SampleDescriptor.class);
      return descriptor.getSamples();
   }
   
   @Override
   public void run(){
      try {
         computeFeatureVectors(gestureClass);
      }
      catch (FeatureException exception) {
         exception.printStackTrace();
         // FIXME throw new AlgorithmException(ExceptionType.Initialisation, exception);
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
   public BigMatrixImpl getCovarianceMatrix() {
      
      if (realMatrix == null) {
         int numOfFeatures = configuration.getNumberOfFeatures();
         BigDecimal[][] matrix = new BigDecimal[numOfFeatures][numOfFeatures];

         for (int i = 0; i < numOfFeatures; i++) {

            for (int j = 0; j < numOfFeatures; j++) {
               BigDecimal sum = new BigDecimal(0);

               for (Gesture<?> sample : samples) {
                  try{
                    BigDecimal a1 = sampleFeatureVector.get(sample).getFeatureVector().get(i).subtract(meanFeatureVector.get(i));
                    BigDecimal a2 = sampleFeatureVector.get(sample).getFeatureVector().get(j).subtract(meanFeatureVector.get(j));
                    sum = sum.add(a1.multiply(a2));
                  }catch(Exception e){
                     e.printStackTrace();
                  }
               }
               matrix[i][j] = sum;
            }
         }

         realMatrix = new BigMatrixImpl(matrix);
      }

      return realMatrix;
   } // getCovarianceMatrixPerClass
   
   public BigDecimalVector getMeanFeatureVector(){
      return meanFeatureVector;
   }
   
   public int getNumberOfSamples(){
      return samples.size();
   }
   
   public void computeWeights(BigMatrix inverse){
      weightsVector = new BigDecimalVector(meanFeatureVector.size());

      for (int j = 0; j < meanFeatureVector.size(); j++) {
         BigDecimal wci = new BigDecimal(0);

         for (int i = 0; i < meanFeatureVector.size(); i++) {
            wci = wci.add(inverse.getEntry(i, j).multiply(meanFeatureVector.get(i)));
         }

         weightsVector.set(j, wci);
      }

      // compute the initial weight
      BigDecimal wc0 = new BigDecimal(0);

      // TODO inline in for loop above? after weightVector.set(j, wci)
      for (int f = 0; f < meanFeatureVector.size(); f++) {
         wc0 = wc0.add(weightsVector.get(f).multiply( meanFeatureVector.get(f)));
      }

      this.initialWeight =  wc0.multiply(new BigDecimal(-0.5));
   }
   
   public BigDecimal getInitialWeight(){
      return initialWeight;
   }
   
   public BigDecimalVector getWeights(){
      return weightsVector;
   }
   
   public GestureClass getGestureClass(){
      return gestureClass;
   }


}
