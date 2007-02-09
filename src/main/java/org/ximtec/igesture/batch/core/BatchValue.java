/*
 * @(#)BatchValue.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Represents a simple value parameter of the batch process.
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


public class BatchValue {

   public static String ROOT_TAG = "value";

   private String value;


   public String getValue() {
      return this.value;
   }


   public void setValue(String value) {
      this.value = value;
   }


   public static BatchValue unmarshal(Element parameterValue) {
      final BatchValue value = new BatchValue();
      value.setValue(parameterValue.getText());
      return value;
   }
}
