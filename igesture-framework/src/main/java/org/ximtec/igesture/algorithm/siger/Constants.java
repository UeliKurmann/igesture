/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose		: 	Constants used by the Siger algorithm.
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

import org.sigtec.util.Constant;


/**
 * Constants used by the Siger algorithm.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Constants {

   // directions
   public static String N = "(N,|NW,|NE,)+";

   public static String NW = "(NW,|N,|W,)+";

   public static String W = "(W,|NW,|SW,)+";

   public static String SW = "(SW,|W,|S,)+";

   public static String S = "(S,|SW,|SE,)+";

   public static String SE = "(SE,|S,|E,)+";

   public static String E = "(E,|SE,|NE,)+";

   public static String NE = "(NE,|E,|N,)+";

   // start
   public static String START = "([A-Z]{1,2},){0,5}";

   // end
   public static String END = "([A-Z]{1,2},){0,5}";


   /**
    * Returns the regular expression of the given direction.
    * 
    * @param direction the direction to be tested.
    * @return the regular expression.
    */
   public static String getRegex(Direction direction) {
      switch (direction) {
         case N:
            return N;
         case NW:
            return NW;
         case W:
            return W;
         case SW:
            return SW;
         case S:
            return S;
         case SE:
            return SE;
         case E:
            return E;
         case NE:
            return NE;
         default:
            return Constant.EMPTY_STRING;
      }

   } // getRegex

}
