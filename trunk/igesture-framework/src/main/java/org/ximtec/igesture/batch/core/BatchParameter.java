/*
 * @(#)BatchParameter.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Base class of all batch parameters.
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

import org.jdom.Element;


/**
 * Base class of all batch parameters.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
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
   } // setName


   public String getName() {
      return this.name;
   } // getName


   public static BatchParameter unmarshal(Element parameter) {
      final BatchParameter batchParameter = new BatchParameter();
      batchParameter.setName(parameter.getAttributeValue(ATTRIBUTE_NAME));
      final Element parameterValue = ((Element)parameter.getChildren().get(0));

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
   } // unmarshal


   public void setIncrementalValue(BatchForValue incrementalValue) {
      this.incrementalValue = incrementalValue;
   } // setIncrementalValue


   public BatchForValue getIncrementalValue() {
      return incrementalValue;
   } // getIncrementalValue


   public void setPermutationValue(BatchPowerSetValue permutationValue) {
      this.permutationValue = permutationValue;
   } // setPermutationValue


   public BatchPowerSetValue getPermutationValue() {
      return permutationValue;
   } // getPermutationValue


   public void setSequenceValue(BatchSequenceValue sequenceValue) {
      this.sequenceValue = sequenceValue;
   } // 


   public BatchSequenceValue getSequenceValue() {
      return sequenceValue;
   } // getSequenceValue


   public void setValue(BatchValue value) {
      this.value = value;
   } // setValue


   public BatchValue getValue() {
      return value;
   } // getValue

}
