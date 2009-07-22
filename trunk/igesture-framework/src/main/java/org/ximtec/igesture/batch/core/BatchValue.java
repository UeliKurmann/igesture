/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Represents a simple value parameter of the batch
 *                  process.
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch.core;



/**
 * Represents a simple value parameter of the batch process.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchValue {

   
   private String value;


   /**
    * Sets the value.
    * 
    * @param value the value to be set.
    */
   public void setValue(String value) {
      this.value = value;
   } // setValue


   /**
    * Returns the value.
    * 
    * @return the value of this batch value instance.
    */
   public String getValue() {
      return this.value;
   } // getValue



   
}
