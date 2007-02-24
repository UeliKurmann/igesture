/*
 * @(#)BatchResult.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Represents the result with key figures, etc. of the
 * 					batch process
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


package org.ximtec.igesture.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.TestSet;

/**
 * Represents the result with key figures, etc. of the
 * batch process
 * @author Ueli Kurmann
 * @version 1.0
 *
 */
public class BatchResult {

   private int numberOfSamples;

   private int numberOfNoise;

   private int numberOfRejectError;

   private int numberOfRejectCorrect;

   private int numberOfErrors;

   private int numberOfCorrects;

   private long startTime;

   private long endTime;

   private HashMap<String, ClassStatistic> classStatistics;

   private Configuration configuration;


   public BatchResult(TestSet testSet, Configuration configuration) {
      this.numberOfCorrects = 0;
      this.numberOfRejectError = 0;
      this.numberOfRejectCorrect = 0;
      this.numberOfErrors = 0;
      this.numberOfNoise = testSet.getNoiseSize();
      this.numberOfSamples = testSet.getSamples().size();
      this.configuration = configuration;
      this.classStatistics = new HashMap<String, ClassStatistic>();
      for (final GestureClass gestureClass : configuration.getGestureSet()
            .getGestureClasses()) {
         this.classStatistics.put(gestureClass.getName(), new ClassStatistic(
               gestureClass.getName()));
      }
      this.classStatistics.put(TestSet.NOISE, new ClassStatistic(TestSet.NOISE));
   }


   /**
    * Returns the number of correct recognised gestures
    * 
    * @return number of correct recognised gestures
    */
   public int getNumberOfCorrects() {
      return numberOfCorrects;
   }


   /**
    * Returns the number of incorrect recognised gestures
    * 
    * @return number of incorrect recognised gestures
    */
   public int getNumberOfErrors() {
      return numberOfErrors;
   }


   /**
    * Returns the number of gestures which are rejected correctly
    * 
    * @return number of correctly rejected gestures
    */
   public int getNumberOfRejectCorrect() {
      return numberOfRejectCorrect;
   }


   /**
    * Returns the number of rejected gestures which shuld be recognised
    * 
    * @return number of rejected gestures
    */
   public int getNumberOfRejectError() {
      return numberOfRejectError;
   }


   /**
    * Returns the size of the test set at all
    * 
    * @return size of the test set
    */
   public int getNumberOfSamples() {
      return numberOfSamples;
   }


   /**
    * Returns the number of samples which shuld be rejected
    * 
    * @return number of samples which should be rejected
    */
   public int getNumberOfNoise() {
      return numberOfNoise;
   }


   /**
    * Increases the number of errors
    * 
    * @param className the name of the gesture class
    */
   public void incError(String className) {
      numberOfErrors++;
      classStatistics.get(className).incError();
   }


   /**
    * Increases the number of corrects
    * 
    * @param className the name of the gesture class
    */
   public void incCorrect(String className) {
      numberOfCorrects++;
      classStatistics.get(className).incCorrect();
   }


   /**
    * Increments the number of correclty rejected gestures
    * 
    * @param className the name of the gesture class
    */
   public void incRejectCorrect(String className) {
      numberOfRejectCorrect++;
      classStatistics.get(className).incRejectCorrect();
   }


   /**
    * Increments the number of wrong rejected gestures
    * 
    * @param className the name of the gesture class
    */
   public void incRejectError(String className) {
      numberOfRejectError++;
      classStatistics.get(className).incRejectError();
   }


   /**
    * Computes the precision
    * 
    * @return the precision
    */
   public double getPrecision() {
      return (double) numberOfCorrects / (numberOfCorrects + numberOfErrors);
   }


   /**
    * Computes the recall
    * 
    * @return the recall
    */
   public double getRecall() {
      return (double) numberOfCorrects / (numberOfSamples - numberOfNoise);
   }


   /**
    * Sets the start time
    * 
    */
   public void setStartTime() {
      this.startTime = System.currentTimeMillis();
   }


   /**
    * Sets the end time
    * 
    */
   public void setEndTime() {
      this.endTime = System.currentTimeMillis();
   }


   /**
    * Returns the running time
    * 
    * @return the running time
    */
   public long getRunningTime() {
      return endTime - startTime;
   }


   /**
    * Returns the configuration
    * 
    * @return the configuration
    */
   public Configuration getConfiguration() {
      return configuration;
   }


   /**
    * Returns a list of statistics
    * 
    * @return
    */
   public List<ClassStatistic> getStatistics() {
      return new ArrayList<ClassStatistic>(classStatistics.values());
   }

}
