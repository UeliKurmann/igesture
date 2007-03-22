/*
 * @(#)BatchResultSet.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Container for a set of batch results.
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


package org.ximtec.igesture.batch;

import java.util.ArrayList;
import java.util.List;


/**
 * Container for a set of batch results.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchResultSet {

   List<BatchResult> results;


   /**
    * Constructs a new batch result set.
    */
   public BatchResultSet() {
      results = new ArrayList<BatchResult>();
   }


   /**
    * Adds a batch result.
    * 
    * @param result the batch result to be added.
    */
   public void addResult(BatchResult result) {
      results.add(result);
   } // addResult


   /**
    * Returns a list with all batch results.
    * 
    * @return the list with all batch results.
    */
   public List<BatchResult> getBatchResults() {
      return results;
   } // getBatchResults

}
