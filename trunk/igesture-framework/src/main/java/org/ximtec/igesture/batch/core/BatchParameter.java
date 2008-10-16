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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch.core;



/**
 * Base class of all batch parameters.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchParameter {

   
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
