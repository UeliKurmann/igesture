/*
 * @(#)ClassStatistic.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Holds static information about a gesture class. Used
 * 					by the BatchProcess.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch;

/**
 * Holds static information about a gesture class. Used by the BatchProcess.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ClassStatistic {

   private int correct;

   private int error;

   private int rejectCorrect;

   private int rejectError;

   private String className;


   /**
    * Constructs a new class statistic.
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
    * Increments the number of correctly recognised gestures.
    * 
    */
   public void incCorrect() {
      this.correct++;
   } // incCorrect


   /**
    * Returns the number of correctly recognised gestures.
    * 
    * @return the number of correctly recognised gesture.
    */
   public int getCorrect() {
      return correct;
   } // getCorrect


   /**
    * Increments the number of errors.
    * 
    */
   public void incError() {
      this.error++;
   } // incError


   /**
    * Returns the number of errors.
    * 
    * @return the number of errors.
    */
   public int getError() {
      return error;
   } // getError


   /**
    * Increments the number of wrongly rejected gestures.
    * 
    */
   public void incRejectError() {
      this.rejectError++;
   } // incRejectError


   public int getRejectError() {
      return rejectError;
   } // getRejectError


   /**
    * Increments the number of correctly rejected gestured.
    * 
    */
   public void incRejectCorrect() {
      this.rejectCorrect++;
   } // incRejectCorrect


   /**
    * Returns the number of correctly rejected gestures.
    * 
    * @return the number of correctly rejected gestures.
    */
   public int getRejectCorrect() {
      return rejectCorrect;
   } // getRejectCorrect


   /**
    * Returns the gesture class name.
    * 
    * @return the gesture class name.
    */
   public String getClassName() {
      return className;
   } // getClassName

}
