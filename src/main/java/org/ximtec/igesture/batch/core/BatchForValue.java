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


   public double getEnd() {
      return end;
   }


   public void setEnd(double end) {
      this.end = end;
   }


   public double getStart() {
      return start;
   }


   public void setStart(double start) {
      this.start = start;
   }


   public double getStep() {
      return step;
   }


   public void setStep(double step) {
      this.step = step;
   }


   public List<String> getValues() {
      final List<String> result = new ArrayList<String>();
      for (double value = getStart(); value <= getEnd(); value = value
            + getStep()) {
         result.add(String.valueOf(value));
      }
      return result;
   }

}
