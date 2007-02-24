/*
 * @(#)Direction.java   1.0   Dec 26, 2006
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
 * Rubine Feature F1
 * Cosine of the initial angle of the gesture
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;


public class F1 implements Feature {

   public double compute(Note note) {
      final Trace trace = FeatureTools.createTrace(note);

      final Point p0 = trace.get(0);
      Point p2 = trace.get(2);
      if (p0.getX() == p2.getX() && p0.getY() == p2.getY()) {
         p2 = trace.get(4);
      }

      return (p2.getX() - p0.getX())
            / (Math.sqrt(Math.pow(p2.getX() - p0.getX(), 2)
                  + Math.pow(p2.getY() - p0.getY(), 2)));
   }
}
