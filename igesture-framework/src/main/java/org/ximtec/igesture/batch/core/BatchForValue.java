/*
 * @(#)BatchForValue.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Represents the for loop of the batch process
 * 
 * 
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


package org.ximtec.igesture.batch.core;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

/**
 * Represents a for loop
 * @author Ueli Kurmann
 * @version 1.0
 */

public class BatchForValue {

   public static String ROOT_TAG = "for";

   public static String ATTRIBUTE_START = "start";

   public static String ATTRIBUTE_END = "end";

   public static String ATTRIBUTE_STEP = "step";

   private double start;

   private double end;

   private double step;


   public BatchForValue() {
   }


   public static BatchForValue unmarshal(Element parameter) {
      final BatchForValue value = new BatchForValue();

      value.setStart(Double.parseDouble(parameter
            .getAttributeValue(ATTRIBUTE_START)));
      value.setEnd(Double
            .parseDouble(parameter.getAttributeValue(ATTRIBUTE_END)));
      value.setStep(Double.parseDouble(parameter
            .getAttributeValue(ATTRIBUTE_STEP)));

      return value;
   }


   /**
    * Returns the upper bound of the loop
    * @return the upper bound of the loop
    */
   public double getEnd() {
      return end;
   }


   /**
    * Sets the upper bound of the loop
    * @param end the upper bound of the loop
    */
   public void setEnd(double end) {
      this.end = end;
   }


   /**
    * Returns the lower bound of the loop
    * @return the lower bound of the loop
    */
   public double getStart() {
      return start;
   }


   /**
    * Sets the lower bound of the loop
    * @param start the lower bound of the loop
    */
   public void setStart(double start) {
      this.start = start;
   }


   /**
    * Returns the step size for iterating between the lower and upper bound
    * @return the step size for iterating between the lower and upper bound
    */
   public double getStep() {
      return step;
   }


   /**
    * Sets the step size for iterating between the lower and upper bound
    * @param step the step size for iterating between the lower and upper bound
    */
   public void setStep(double step) {
      this.step = step;
   }


   /**
    * Returns a list of all values matching the constraints
    * @return a list of all values matching the constraints
    */
   public List<String> getValues() {
      final List<String> result = new ArrayList<String>();
      for (double value = getStart(); value <= getEnd(); value = value
            + getStep()) {
         result.add(String.valueOf(value));
      }
      return result;
   }

}
