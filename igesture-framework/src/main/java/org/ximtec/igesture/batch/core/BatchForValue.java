/*
 * @(#)BatchForValue.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Represents the for loop of the batch process.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
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
 * Represents the for loop of the batch process.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
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
   } // unmarshal


   /**
    * Sets the upper bound of the loop.
    * @param end the upper bound of the loop.
    */
   public void setEnd(double end) {
      this.end = end;
   } // setEnd


   /**
    * Returns the upper bound of the loop.
    * @return the upper bound of the loop.
    */
   public double getEnd() {
      return end;
   } // getEnd


   /**
    * Sets the lower bound of the loop.
    * @param start the lower bound of the loop.
    */
   public void setStart(double start) {
      this.start = start;
   } // setStart


   /**
    * Returns the lower bound of the loop.
    * @return the lower bound of the loop.
    */
   public double getStart() {
      return start;
   } // getStart


   /**
    * Sets the step size for iterating between the lower and upper bound.
    * @param step the step size for iterating between the lower and upper bound.
    */
   public void setStep(double step) {
      this.step = step;
   } // setStep


   /**
    * Returns the step size for iterating between the lower and upper bound.
    * @return the step size for iterating between the lower and upper bound.
    */
   public double getStep() {
      return step;
   } // getStep


   /**
    * Returns a list of all values matching the constraints.
    * @return a list of all values matching the constraints.
    */
   public List<String> getValues() {
      final List<String> result = new ArrayList<String>();

      for (double value = getStart(); value <= getEnd(); value = value
            + getStep()) {
         result.add(String.valueOf(value));
      }

      return result;
   } // getValues

}
