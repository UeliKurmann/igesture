/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Implements a vector of double values and provides
 *                  some basic functionality.
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


package org.ximtec.igesture.util;

import java.util.ArrayList;

import org.sigtec.util.Constant;


/**
 * Implements a vector of double values and provides some basic functionality.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class DoubleVector extends ArrayList<Double> {

   /**
    * Constructs a new double vector.
    * 
    * @param size the initial size of the double vector.
    */
   public DoubleVector(int size) {
      super(size);

      for (int i = 0; i < size; i++) {
         add(i, 0d);
      }

   }


   /**
    * Returns a double array of the vector's content.
    * 
    * @return a double array of the vector's content.
    */
   public double[] toDoubleArray() {
      final double[] result = new double[size()];

      for (int i = 0; i < size(); i++) {
         result[i] = get(i);
      }

      return result;
   } // toDoubleArray


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();

      for (final double d : this) {

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
