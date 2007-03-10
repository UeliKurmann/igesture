/*
 * @(#)F11.java   1.0   Dec 26, 2006
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
 * Rubine Feature F11
 * The sum of the squared value of the angles at each mouse point
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;


public class F11 implements Feature {

   public double compute(Note note) {
      final Trace trace = FeatureTools.createTrace(note);
      final Point[] points = new Point[trace.getPoints().size()];
      int j = 0;
      for (final Point p : trace.getPoints()) {
         points[j] = p;
         j++;
      }
      double result = 0;
      for (int i = 1; i < trace.getPoints().size() - 1; i++) {
         result += Math.pow(FeatureTools.roh(i, points), 2);
      }
      return result;
   }
}
