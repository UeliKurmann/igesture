/*
 * @(#)ClassStatistic.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Holds statatic information about a gesture class. Used
 * 					by the BatchProcess.
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
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch;
/**
 * Statistic for a specific gesture class
 * @author Ueli Kurmann
 * @version 1.0
 *
 */
public class ClassStatistic {

   private int correct;

   private int error;

   private int rejectCorrect;

   private int rejectError;

   private String className;


   /**
    * Constructor
    * 
    * @param className the name of the gesture class
    */
   public ClassStatistic(String className) {
      this.className = className;
      this.correct = 0;
      this.error = 0;
      this.rejectError = 0;
      this.rejectCorrect = 0;
   }


   /**
    * Returns the number of correct recognised gestures
    * 
    * @return number of correct recognised gestures
    */
   public int getCorrect() {
      return correct;
   }


   /**
    * Returns the number of errors
    * 
    * @return the number of errors
    */
   public int getError() {
      return error;
   }


   public int getRejectError() {
      return rejectError;
   }


   /**
    * Returns the number of correct rejected gestures
    * 
    * @return the number of correct rejected gestures
    */
   public int getRejectCorrect() {
      return rejectCorrect;
   }


   /**
    * Increments the number of corrects
    * 
    */
   public void incCorrect() {
      this.correct++;
   }


   /**
    * Increments the number of errors
    * 
    */
   public void incError() {
      this.error++;
   }


   /**
    * Increments the number of correct rejects
    * 
    */
   public void incRejectCorrect() {
      this.rejectCorrect++;
   }


   /**
    * Increments the number of wrong rejects
    * 
    */
   public void incRejectError() {
      this.rejectError++;
   }


   /**
    * Returns the gesture class name
    * 
    * @return the gesture class name
    */
   public String getClassName() {
      return this.className;
   }
}
