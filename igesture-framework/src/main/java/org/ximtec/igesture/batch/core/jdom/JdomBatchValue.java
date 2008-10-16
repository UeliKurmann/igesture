/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 15.10.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch.core.jdom;

import org.jdom.Element;
import org.ximtec.igesture.batch.core.BatchValue;



/**
 * Comment
 * @version 1.0 15.10.2008
 * @author Ueli Kurmann
 */
public class JdomBatchValue {
   
   public static String ROOT_TAG = "value";
   
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
