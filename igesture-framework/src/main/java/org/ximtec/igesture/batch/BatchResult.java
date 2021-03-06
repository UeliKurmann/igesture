/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Represents the result with key figures, etc. of the
 * 					batch process.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 20, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.TestSet;


/**
 * Represents the result with key figures, etc. of the batch process.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
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

   private Map<String, Statistic> classStatistics;

   private Configuration configuration;


   public BatchResult(TestSet testSet, Configuration configuration) {
      this.numberOfCorrects = 0;
      this.numberOfRejectError = 0;
      this.numberOfRejectCorrect = 0;
      this.numberOfErrors = 0;
      this.numberOfNoise = testSet.getNoiseSize();
      this.numberOfSamples = testSet.getNumberOfSamples();
      this.configuration = configuration;
      
      this.classStatistics = new HashMap<String, Statistic>();

      for (GestureClass gestureClass : configuration.getGestureSet()
            .getGestureClasses()) {
         this.classStatistics.put(gestureClass.getName(), new Statistic(gestureClass));
      }

      this.classStatistics.put(TestSet.NOISE, new Statistic(new GestureClass(TestSet.NOISE)));
   }


   /**
    * Returns the number of correctly recognised gestures.
    * 
    * @return number of correctly recognised gestures
    */
   public int getNumberOfCorrects() {
      return numberOfCorrects;
   } // getNumberOfCorrects


   /**
    * Returns the number of incorrectly recognised gestures.
    * 
    * @return number of incorrectly recognised gestures.
    */
   public int getNumberOfErrors() {
      return numberOfErrors;
   } // getNumberOfErrors


   /**
    * Returns the number of gestures which are rejected correctly.
    * 
    * @return number of correctly rejected gestures.
    */
   public int getNumberOfRejectCorrect() {
      return numberOfRejectCorrect;
   } // getNumberOfRejectCorrect


   /**
    * Returns the number of rejected gestures which should have been recognised.
    * 
    * @return number of wrongly rejected gestures.
    */
   public int getNumberOfRejectError() {
      return numberOfRejectError;
   } // getNumberOfRejectError


   /**
    * Returns the size of the complete test set.
    * 
    * @return size of the test set.
    */
   public int getNumberOfSamples() {
      return numberOfSamples;
   } // getNumberOfSamples


   /**
    * Returns the number of samples which should be rejected.
    * 
    * @return number of samples which should be rejected.
    */
   public int getNumberOfNoise() {
      return numberOfNoise;
   } // getNumberOfNoise


   /**
    * Increases the number of errors.
    * 
    * @param className the name of the gesture class for which the number of
    *            errors has to be increased.
    */
   public void incError(String className) {
      numberOfErrors++;
      getClassStatistic(className).incError();
   } // incError


   private Statistic getClassStatistic(String className) {
      Statistic classStatistic = classStatistics.get(className);
      if (classStatistic == null) {
         classStatistic = classStatistics.get(TestSet.NOISE);
      }
      return classStatistic;
   }


   /**
    * Increases the number of correct recognitions.
    * 
    * @param className the name of the gesture class.
    */
   public void incCorrect(String className) {
      numberOfCorrects++;
      getClassStatistic(className).incCorrect();
   } // incCorrect


   /**
    * Increments the number of correctly rejected gestures.
    * 
    * @param className the name of the gesture class.
    */
   public void incRejectCorrect(String className) {
      numberOfRejectCorrect++;
      getClassStatistic(className).incRejectCorrect();
   } // incRejectCorrect


   /**
    * Increments the number of wrongly rejected gestures.
    * 
    * @param className the name of the gesture class.
    */
   public void incRejectError(String className) {
      numberOfRejectError++;
      getClassStatistic(className).incRejectError();
   } // incRejectError


   /**
    * Computes the precision.
    * 
    * @return the precision.
    */
   public double getPrecision() {
      return (double)numberOfCorrects / (numberOfCorrects + numberOfErrors);
   } // getPrecision


   /**
    * Computes the recall.
    * 
    * @return the recall.
    */
   public double getRecall() {
      return (double)numberOfCorrects / (numberOfCorrects + numberOfRejectError);
   } // getRecall


   /**
    * Sets the start time to the current system time.
    * 
    */
   public void setStartTime() {
      this.startTime = System.currentTimeMillis();
   } // setStartTime


   /**
    * Sets the end time to the current system time.
    * 
    */
   public void setEndTime() {
      this.endTime = System.currentTimeMillis();
   } // setEndTime


   /**
    * Returns the running time in milliseconds.
    * 
    * @return the running time in milliseconds.
    */
   public long getRunningTime() {
      return endTime - startTime;
   } // getRunningTime


   /**
    * Returns the configuration.
    * 
    * @return the configuration.
    */
   public Configuration getConfiguration() {
      return configuration;
   } // getConfiguration


   /**
    * Returns a list of statistics.
    * 
    * @return the list of statistics.
    */
   public List<Statistic> getStatistics() {
      return new ArrayList<Statistic>(classStatistics.values());
   }

}
