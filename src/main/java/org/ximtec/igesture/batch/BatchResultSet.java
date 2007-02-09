/*
 * @(#)BatchResultSet.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Holds a set of BatchResults
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


public class BatchResultSet {

   List<BatchResult> results;


   /**
    * Costructor
    * 
    */
   public BatchResultSet() {
      results = new ArrayList<BatchResult>();
   }


   /**
    * Add a batch result
    * 
    * @param result batch result to be added
    */
   public void addResult(BatchResult result) {
      results.add(result);
   }


   /**
    * Returns the batch results
    * 
    * @return
    */
   public List<BatchResult> getBatchResults() {
      return results;
   }
}
