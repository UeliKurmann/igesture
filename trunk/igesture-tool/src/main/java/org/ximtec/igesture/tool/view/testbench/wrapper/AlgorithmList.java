/*
 * @(#)$Id$
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

import org.ximtec.igesture.core.DefaultPropertyChangeOwner;


public class AlgorithmList extends DefaultPropertyChangeOwner{

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
   
// FIXME use resource bundle
   public String getName(){
      return "Algorithms";
   }


   @Override
   public String toString() {  
      return getName();
   }
}