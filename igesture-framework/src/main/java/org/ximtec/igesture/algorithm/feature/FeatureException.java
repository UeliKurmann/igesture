/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Feature exception that is thrown if there is a problem
 *                  computing a feature.
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
 * Feature exception that is thrown if there is a problem computing a feature.
 * 
 * @version 1.0 May 2007
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class FeatureException extends Exception {

   public static final String NOT_ENOUGH_POINTS = "Not enough points";


   public FeatureException(String message) {
      super(message);
   }

}
