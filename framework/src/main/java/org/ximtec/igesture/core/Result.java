/*
 * @(#)Result.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Result of the recognition process
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


package org.ximtec.igesture.core;

/**
 * this class represents a single result of a recognition process
 * 
 * @author Ueli Kurmann
 * 
 */

public class Result {

   private GestureClass gestureClass;

   private double accuracy;

   private Object userObject;


   /**
    * Constructor
    * 
    * @param gestureClass
    * @param accuracy
    */
   public Result(GestureClass gestureClass, double accuracy) {
      this.gestureClass = gestureClass;
      this.accuracy = accuracy;
   }


   /**
    * Returns the accuracy computed by the algorithm.
    * 
    * @return the accuracy
    */
   public double getAccuracy() {
      return accuracy;
   }


   /**
    * Sets the accuracy
    * 
    * @param accuracy
    */
   public void setAccuracy(double accuracy) {
      this.accuracy = accuracy;
   }


   /**
    * Returns the Gesture Class of the Result
    * 
    * @return the gesture class
    */
   public GestureClass getGestureClass() {
      return gestureClass;
   }


   /**
    * Returns the name of the Gesture Class of this Result
    * 
    * @return the name of the gesture class
    */
   public String getName() {
      if (gestureClass != null & gestureClass.getName() != null) {
         return gestureClass.getName();
      }
      else {
         return "";
      }
   }


   /**
    * Returns the userObject. A user object allows the returnment of an arbritary
    * object by the algorithm. the user is responsible to use this functionality.
    * no guarantee about the type is possible.
    * 
    * @return the user object
    */
   public Object getUserObject() {
      return userObject;
   }


   /**
    * Sets the userObject.
    * 
    * @see getUserObject()
    * @param userObject
    */
   public void setUserObject(Object userObject) {
      this.userObject = userObject;
   }

}
