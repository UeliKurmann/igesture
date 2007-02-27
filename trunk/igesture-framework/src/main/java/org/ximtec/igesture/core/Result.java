/*
 * @(#)Result.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	A single result of the recognition process.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Feb 27, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import org.sigtec.util.Constant;


/**
 * A single result of the recognition process.
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Result {

   private GestureClass gestureClass;

   private double accuracy;

   private Object userObject;


   /**
    * Constructs a new result.
    * 
    * @param gestureClass the recognised gesture class.
    * @param accuracy the accurancy for the recognised gesture class.
    */
   public Result(GestureClass gestureClass, double accuracy) {
      this.gestureClass = gestureClass;
      this.accuracy = accuracy;
   }


   /**
    * Sets the accuracy for the gesture class of this result object.
    * 
    * @param accuracy the accuracy for the gesture class of this result object.
    */
   public void setAccuracy(double accuracy) {
      this.accuracy = accuracy;
   } // setAccuracy


   /**
    * Returns the accuracy for the gesture class of this result object.
    * 
    * @return the accuracy for the gesture class of this result object.
    */
   public double getAccuracy() {
      return accuracy;
   } // getAccuracy


   /**
    * Returns the Gesture Class of the Result
    * 
    * @return the gesture class
    */
   public GestureClass getGestureClass() {
      return gestureClass;
   }


   /**
    * Sets a a user object. A user object enables an algorithm to return an
    * arbritary object. However, the user is responsible for the correct handling
    * since no explicit type information is available.
    * 
    * @param userObject the user object to be set.
    */
   public void setUserObject(Object userObject) {
      this.userObject = userObject;
   } // setUserObject


   /**
    * Returns a user object. A user object enables an algorithm to return an
    * arbritary object. However, the user is responsible for the correct handling
    * since no explicit type information is available.
    * 
    * @return the user object.
    */
   public Object getUserObject() {
      return userObject;
   } // getUserObject


   /**
    * Returns the name of the gesture class represented by this result or an
    * empty string if no gesture class is associated with this result object.
    * 
    * @return the name of the gesture class represented by this result.
    */
   public String getGestureClassName() {
      return (gestureClass != null & gestureClass.getName() != null)
            ? gestureClass.getName() : Constant.EMPTY_STRING;
   } // getGestureClassName

}
