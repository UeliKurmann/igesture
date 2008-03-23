/*
 * @(#)$Id: RootSet.java 456 2008-03-23 17:16:31Z kurmannu $
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.testbench.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.core.DefaultDataObject;


public class AlgorithmList extends DefaultDataObject {

   public static final String PROPERTY_SETS = "algorithms";

   public List<AlgorithmWrapper> algorithms;
   


   public AlgorithmList() {
      algorithms = new ArrayList<AlgorithmWrapper>();
   }


   public void addAlgorithm(AlgorithmWrapper algorithmWrapper) {
      algorithms.add(algorithmWrapper);
   }


   public List<AlgorithmWrapper> getAlgorithms() {
      return algorithms;
   }


   @Override
   public String toString() {
      // FIXME use resource bundle
      return "Algorithms";
   }
}
