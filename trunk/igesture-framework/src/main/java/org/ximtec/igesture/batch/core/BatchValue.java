/*
 * @(#)BatchValue.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Represents a simple value parameter of the batch
 *                  process.
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

import org.jdom.Element;


/**
 * Represents a simple value parameter of the batch process.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchValue {

   public static String ROOT_TAG = "value";

   private String value;


   /**
    * Sets the value.
    * 
    * @param value the value to be set.
    */
   public void setValue(String value) {
      this.value = value;
   } // setValue


   /**
    * Returns the value.
    * 
    * @return the value of this batch value instance.
    */
   public String getValue() {
      return this.value;
   } // getValue


   /**
    * Imports a simple value from an XML element.
    * 
    * @param parameterValue the XML element the value has to be imported form.
    * @return the batch value imported from the XML element.
    */
   public static BatchValue unmarshal(Element parameterValue) {
      final BatchValue value = new BatchValue();
      value.setValue(parameterValue.getText());
      return value;
   } // unmarshal
   
}
