/*
 * @(#)Direction.java	1.0   Dec 6, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Directions used by the Siger algorithm
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.siger;

/**
 * Comment
 * 
 * @version 1.0 Dec 6, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
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
   }
}
