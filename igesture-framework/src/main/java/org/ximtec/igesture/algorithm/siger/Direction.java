/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose		: 	Directions used by the Siger algorithm.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 6, 2006	    ukurmann	Initial Release
 * Mar 16, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.siger;

/**
 * Directions used by the Siger algorithm.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public enum Direction {
   N, NE, E, SE, S, SW, W, NW;

   public static Direction parse(String s) {
      for (final Direction d : Direction.values()) {

         if (d.name().equals(s.trim())) {
            return d;
         }

      }
      return null;
   } // parse

}
