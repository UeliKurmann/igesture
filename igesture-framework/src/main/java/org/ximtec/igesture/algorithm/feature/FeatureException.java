/*
 * $Id$
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Feature Exception
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * May 5, 2007      ukurmann    Initial Release
 * 
 * $Id$
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.algorithm.feature;

/**
 * Feature Exception
 * 
 * @version 1.0 May 2007
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class FeatureException extends Exception {

   public static final String NOT_ENOUGH_POINTS = "Not enough points";
 
   public FeatureException(String message) {
      super(message);
   }
   
}
