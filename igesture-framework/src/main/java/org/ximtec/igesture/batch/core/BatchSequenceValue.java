/*
 * @(#)BatchSequenceValue.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Represents the value of a sequence. Used by the
 *                  BatchProcess.
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

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the value of a sequence. Used by the BatchProcess.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchSequenceValue extends BatchParameter {

  
   List<String> values;


   public BatchSequenceValue() {
      values = new ArrayList<String>();
   }


   /**
    * Adds a value to the batch sequence value.
    * @param value the value to be added.
    */
   public void addValue(String value) {
      values.add(value);
   } // addValue


   /**
    * Returns the list of values of the sequence.
    * 
    * @return the list of values of the sequence.
    */
   public List<String> getValues() {
      return values;
   } // getValues


   
}
