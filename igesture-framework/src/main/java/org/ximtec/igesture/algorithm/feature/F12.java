/*
 * @(#)F12.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 
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
/****************************************************************************************
 * Rubine Feature F12
 * The maximum speed (squared) of the gesture
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;


public class F12 implements Feature {

   public double compute(Note note) {
      final Trace trace = FeatureTools.createTrace(note);
      final Point[] points = new Point[trace.getPoints().size()];
      int j = 0;
      for (final Point p : trace.getPoints()) {
         points[j] = p;
         j++;
      }
      double max = Double.MIN_VALUE;
      for (int i = 0; i < trace.getPoints().size() - 1; i++) {
         final double result = getBruch(i, points);
         if (result > max) {
            max = result;
         }
      }
      return max;
   }


   private double getBruch(int i, Point[] points) {
      final double divisor = Math.pow(FeatureTools.getDeltaX(i, points), 2)
            + Math.pow(FeatureTools.getDeltaY(i, points), 2);
      final double dividend = Math.pow(FeatureTools.getDeltaT(i, points), 2);

      return Math.atan(divisor / dividend);
   }

}
