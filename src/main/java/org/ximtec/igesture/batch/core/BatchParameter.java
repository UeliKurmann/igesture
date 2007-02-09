/*
 * @(#)BatchParameter.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Base class of the batch parameters
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

import org.jdom.Element;


public class BatchParameter {

   public static String ROOT_TAG = "parameter";

   public static String ATTRIBUTE_NAME = "name";

   private String name;

   private BatchForValue incrementalValue;

   private BatchPowerSetValue permutationValue;

   private BatchSequenceValue sequenceValue;

   private BatchValue value;


   public void setName(String name) {
      this.name = name;
   }


   public String getName() {
      return this.name;
   }


   public static BatchParameter unmarshal(Element parameter) {
      /**
       * Diese Methode diet als eine art factory f[r diek;nritetn subtypes
       */
      final BatchParameter batchParameter = new BatchParameter();
      batchParameter.setName(parameter.getAttributeValue(ATTRIBUTE_NAME));

      final Element parameterValue = ((Element) parameter.getChildren().get(0));
      if (parameterValue.getName().equals(BatchForValue.ROOT_TAG)) {
         batchParameter.setIncrementalValue(BatchForValue
               .unmarshal(parameterValue));
      }
      else if (parameterValue.getName().equals(BatchPowerSetValue.ROOT_TAG)) {
         batchParameter.setPermutationValue(BatchPowerSetValue
               .unmarshal(parameterValue));
      }
      else if (parameterValue.getName().equals(BatchSequenceValue.ROOT_TAG)) {
         batchParameter.setSequenceValue(BatchSequenceValue
               .unmarshal(parameterValue));
      }
      else if (parameterValue.getName().equals(BatchValue.ROOT_TAG)) {
         batchParameter.setValue(BatchValue.unmarshal(parameterValue));
      }
      return batchParameter;
   }


   public BatchForValue getIncrementalValue() {
      return incrementalValue;
   }


   public void setIncrementalValue(BatchForValue incrementalValue) {
      this.incrementalValue = incrementalValue;
   }


   public BatchPowerSetValue getPermutationValue() {
      return permutationValue;
   }


   public void setPermutationValue(BatchPowerSetValue permutationValue) {
      this.permutationValue = permutationValue;
   }


   public BatchSequenceValue getSequenceValue() {
      return sequenceValue;
   }


   public void setSequenceValue(BatchSequenceValue sequenceValue) {
      this.sequenceValue = sequenceValue;
   }


   public BatchValue getValue() {
      return value;
   }


   public void setValue(BatchValue value) {
      this.value = value;
   }

}
