/*
 * @(#)DoubleVector.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Implements a vector of double values and 
 * 					provides some basic functionaliy
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

/**
 * 
 * 
 * @author Ueli Kurmann
 */


package org.ximtec.igesture.core;

import java.util.Vector;


@SuppressWarnings("serial")
public class DoubleVector extends Vector<Double> {

   /**
    * Constructor
    * 
    * @param size
    */
   public DoubleVector(int size) {
      super(size);
      for (int i = 0; i < size; i++) {
         add(i, 0d);
      }
   }


   /**
    * Returns a double array of the list
    * 
    * @return
    */
   public double[] toDoubleArray() {
      final double[] result = new double[size()];
      for (int i = 0; i < size(); i++) {
         result[i] = get(i);
      }
      return result;
   }


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      for (final double d : this) {
         if (String.valueOf(d).length() > 6) {
            sb.append(String.valueOf(d).substring(0, 6) + " ");
         }
         else {
            sb.append((String.valueOf(d) + "     ").substring(0, 6) + " ");
         }
      }
      return "[" + sb.toString().trim() + "]";
   }
}
