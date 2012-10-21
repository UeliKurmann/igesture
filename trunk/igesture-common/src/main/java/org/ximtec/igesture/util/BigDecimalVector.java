/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, ueli.kurmann@bbv.ch
 *
 * Purpose      : 	Implements a vector of BigDecimal values and provides
 *                  some basic functionality.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Oct 10, 2008     ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.util;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.sigtec.util.Constant;


/**
 * Implements a vector of BigDecimal values and provides some basic functionality.
 * 
 * @version 1.0, Oct 2008
 * @author Ueli Kurmann, ueli.kurmann@bbv.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class BigDecimalVector extends ArrayList<BigDecimal> {

   /**
    * Constructs a new double vector.
    * 
    * @param size the initial size of the double vector.
    */
   public BigDecimalVector(int size) {
      super(size);

      for (int i = 0; i < size; i++) {
         add(i, new BigDecimal(0));
      }

   }


   /**
    * Returns a double array of the vector's content.
    * 
    * @return a double array of the vector's content.
    */
   public BigDecimal[] toBigDecimalArray() {
      final BigDecimal[] result = new BigDecimal[size()];

      for (int i = 0; i < size(); i++) {
         result[i] = get(i);
      }

      return result;
   } // toDoubleArray


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();

      for (final BigDecimal d : this) {

         if (String.valueOf(d).length() > 6) {
            sb.append(String.valueOf(d).substring(0, 6) + Constant.BLANK);
         }
         else {
            sb.append((String.valueOf(d) + "     ").substring(0, 6)
                  + Constant.BLANK);
         }

      }

      return Constant.OPEN_ANGULAR_BRACKET + sb.toString().trim()
            + Constant.CLOSE_ANGULAR_BRACKET;
   } // toString

}
