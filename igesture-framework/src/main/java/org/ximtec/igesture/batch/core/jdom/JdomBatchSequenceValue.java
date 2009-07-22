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

import java.util.List;

import org.jdom.Element;
import org.ximtec.igesture.batch.core.BatchSequenceValue;



/**
 * Comment
 * @version 1.0 15.10.2008
 * @author Ueli Kurmann
 */
public class JdomBatchSequenceValue {
   
   public static String ROOT_TAG = "sequence";

   public static String CHILD_TAG = "value";

   /**
    * Imports a BatchSequenceValue from an XML element.
    * @param parameterValue the XML element the value has to be imported from.
    * @return the BatchSequenceValue imported from the XML element.
    */
   @SuppressWarnings("unchecked")
   public static BatchSequenceValue unmarshal(Element parameterValue) {
      final BatchSequenceValue value = new BatchSequenceValue();
      final List<Element> children = parameterValue.getChildren(CHILD_TAG);

      for (final Element element : children) {
         value.addValue(element.getText());
      }

      return value;
   } // unmarshal


}
