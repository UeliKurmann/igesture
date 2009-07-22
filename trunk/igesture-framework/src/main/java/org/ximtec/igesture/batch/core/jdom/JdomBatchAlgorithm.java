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
import org.ximtec.igesture.batch.core.BatchAlgorithm;



/**
 * Comment
 * @version 1.0 15.10.2008
 * @author Ueli Kurmann
 */
public class JdomBatchAlgorithm {
   
   public static String ROOT_TAG = "algorithm";

   public static String ATTRIBUTE_NAME = "name";
   
   
   @SuppressWarnings("unchecked")
   public static BatchAlgorithm unmarshal(Element algorithm) {
      final BatchAlgorithm batchAlgorithm = new BatchAlgorithm();
      batchAlgorithm.setName(algorithm
            .getAttributeValue(ATTRIBUTE_NAME));

      final List<Element> parameterElements = algorithm
            .getChildren(JdomBatchParameter.ROOT_TAG);

      for (final Element elem : parameterElements) {
         batchAlgorithm.addParameter(JdomBatchParameter.unmarshal(elem));
      }

      return batchAlgorithm;
   } // unmarshal

}
