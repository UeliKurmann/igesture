/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Used for the configurations of the batch process.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 20, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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


/**
 * Used for the configurations of the batch process.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class BatchProcessContainer {

   private List<BatchAlgorithm> algorithms;

   private List<GestureSet> sets;


   /**
    * Constructs a new batch process container.
    * 
    */
   public BatchProcessContainer() {
      this.algorithms = new ArrayList<BatchAlgorithm>();
      this.sets = new ArrayList<GestureSet>();
   }


   /**
    * Returns the batch algorithm.
    * 
    * @return the batch algorithm.
    */
   public List<BatchAlgorithm> getAlgorithms() {
      return algorithms;
   } // getAlgorithms


   /**
    * Adds a batch algorithm.
    * 
    * @param algorithm the algorithm to be added.
    */
   public void addAlgorithm(BatchAlgorithm algorithm) {
      algorithms.add(algorithm);
   } // addAlgorithm


   /**
    * Returns a list of gesture sets.
    * 
    * @return list of gesture sets.
    */
   public List<GestureSet> getGestureSets() {
      return sets;
   } // getGestureSets


   /**
    * Adds a gesture set.
    * 
    * @param set the gesture set to be added.
    */
   public void addGestureSet(GestureSet set) {
      sets.add(set);
   } // addGestureSet

}
