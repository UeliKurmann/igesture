/*
 * @(#)BatchProcessContainer.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Used for the configurations of the batch process
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


package org.ximtec.igesture.batch;

import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.batch.core.BatchAlgorithm;
import org.ximtec.igesture.core.GestureSet;


public class BatchProcessContainer {

   private List<BatchAlgorithm> algorithms;

   private List<GestureSet> sets;


   /**
    * Constructor
    * 
    */
   public BatchProcessContainer() {
      this.algorithms = new ArrayList<BatchAlgorithm>();
      this.sets = new ArrayList<GestureSet>();
   }


   /**
    * Returns the BatchAlgorithms
    * 
    * @return
    */
   public List<BatchAlgorithm> getAlgorithms() {
      return algorithms;
   }


   /**
    * Adds a Batch Algorithm
    * 
    * @param algorithm
    */
   public void addAlgorithm(BatchAlgorithm algorithm) {
      algorithms.add(algorithm);
   }


   /**
    * Returns a list of gesture sets
    * 
    * @return
    */
   public List<GestureSet> getGestureSets() {
      return sets;
   }


   /**
    * Adds a gesture set
    * 
    * @param set
    */
   public void addGestureSet(GestureSet set) {
      sets.add(set);
   }

}
