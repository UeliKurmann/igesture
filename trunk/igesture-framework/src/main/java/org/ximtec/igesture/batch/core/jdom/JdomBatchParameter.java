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
import org.ximtec.igesture.batch.core.BatchParameter;




/**
 * Comment
 * @version 1.0 15.10.2008
 * @author Ueli Kurmann
 */
public class JdomBatchParameter {

   public static String ROOT_TAG = "parameter";

   public static String ATTRIBUTE_NAME = "name";

   
   public static BatchParameter unmarshal(Element parameter) {
      final BatchParameter batchParameter = new BatchParameter();
      batchParameter.setName(parameter.getAttributeValue(ATTRIBUTE_NAME));
      final Element parameterValue = ((Element)parameter.getChildren().get(0));

      if (parameterValue.getName().equals(JdomBatchForValue.ROOT_TAG)) {
         batchParameter.setIncrementalValue(JdomBatchForValue
               .unmarshal(parameterValue));
      }
      else if (parameterValue.getName().equals(JdomBatchPowerSetValue.ROOT_TAG)) {
         batchParameter.setPermutationValue(JdomBatchPowerSetValue
               .unmarshal(parameterValue));
      }
      else if (parameterValue.getName().equals(JdomBatchSequenceValue.ROOT_TAG)) {
         batchParameter.setSequenceValue(JdomBatchSequenceValue
               .unmarshal(parameterValue));
      }
      else if (parameterValue.getName().equals(JdomBatchValue.ROOT_TAG)) {
         batchParameter.setValue(JdomBatchValue.unmarshal(parameterValue));
      }

      return batchParameter;
   } // unmarshal

}
