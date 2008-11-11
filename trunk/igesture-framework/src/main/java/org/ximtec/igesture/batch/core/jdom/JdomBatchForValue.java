/*
 * @(#)$Id$
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
 * 15.10.2008		ukurmann	Initial Release
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
import org.ximtec.igesture.batch.core.BatchForValue;


/**
 * Comment
 * @version 1.0 15.10.2008
 * @author Ueli Kurmann
 */
public class JdomBatchForValue {

   public static String ROOT_TAG = "for";

   public static String ATTRIBUTE_START = "start";

   public static String ATTRIBUTE_END = "end";

   public static String ATTRIBUTE_STEP = "step";


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

}
