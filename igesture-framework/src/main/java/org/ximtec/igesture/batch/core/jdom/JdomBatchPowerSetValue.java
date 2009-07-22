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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch.core.jdom;

import org.jdom.Element;
import org.ximtec.igesture.batch.core.BatchPowerSetValue;



/**
 * Comment
 * @version 1.0 15.10.2008
 * @author Ueli Kurmann
 */
public class JdomBatchPowerSetValue {
   
   public static String ROOT_TAG = "powerset";

   public static String ATTRIBUTE_MIN = "min";

   public static String ATTRIBUTE_MAX = "max";

   
   public static BatchPowerSetValue unmarshal(Element parameter) {
      final BatchPowerSetValue value = new BatchPowerSetValue();
      value.setMax(Integer.parseInt(parameter.getAttributeValue(ATTRIBUTE_MAX)));
      value.setMin(Integer.parseInt(parameter.getAttributeValue(ATTRIBUTE_MIN)));

      for (final String s : BatchPowerSetValue.createPowerSet(parameter.getText(), value.getMin(),
            value.getMax())) {
         value.addValue(s);
      }

      return value;
   } // unmarshal

}
