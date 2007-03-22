/*
 * @(#)BatchAlgorithm.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Datastructure to handle algorithms in batch process.
 * 					This class aslo provides XML import functionality.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
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
 * Datastructure to handle algorithms in batch process. This class aslo provides
 * XML import functionality.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchAlgorithm {

   public static String ROOT_TAG = "algorithm";

   public static String ATTRIBUTE_NAME = "name";

   private List<BatchParameter> parameters;

   private String name;


   public BatchAlgorithm() {
      parameters = new ArrayList<BatchParameter>();
   }


   public void setName(String name) {
      this.name = name;
   } // setName


   public String getName() {
      return name;
   } // getName


   public void addParameter(BatchParameter parameter) {
      parameters.add(parameter);
   } // addParameter


   public List<BatchParameter> getParameters() {
      return parameters;
   } // getParameters


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
