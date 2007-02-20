/*
 * @(#)BatchAlgorithm.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Datastructure to handle algorithm in batch process.
 * 					Provides also XML import functionality.
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

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

/**
 * Datastructure for an Algorithm
 * @author Ueli Kurmann
 * @version 1.00
 *
 */
public class BatchAlgorithm {

   public static String ROOT_TAG = "algorithm";

   public static String ATTRIBUTE_NAME = "name";

   private List<BatchParameter> parameters;

   private String name;


   public BatchAlgorithm() {
      parameters = new ArrayList<BatchParameter>();
   }


   public String getName() {
      return name;
   }


   public void setName(String name) {
      this.name = name;
   }


   public void addParameter(BatchParameter parameter) {
      parameters.add(parameter);
   }


   public List<BatchParameter> getParameters() {
      return parameters;
   }


   @SuppressWarnings("unchecked")
   public static BatchAlgorithm unmarshal(Element algorithm) {
      final BatchAlgorithm batchAlgorithm = new BatchAlgorithm();
      batchAlgorithm.setName(algorithm
            .getAttributeValue(BatchAlgorithm.ATTRIBUTE_NAME));

      final List<Element> parameterElements = algorithm
            .getChildren(BatchParameter.ROOT_TAG);
      for (final Element elem : parameterElements) {
         batchAlgorithm.addParameter(BatchParameter.unmarshal(elem));
      }
      return batchAlgorithm;
   } // unmarshal

}
