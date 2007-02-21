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
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

/****************************************************************************************
 * Rubine Feature F6
 * The cosine of the angle between the first and last point
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


public class F6 implements Feature {

   public double compute(Note note) {
      final Trace trace = FeatureTools.createTrace(note);

      final double xn = trace.getEndPoint().getX();
      final double x0 = trace.getStartPoint().getX();

      final F5 f5 = new F5();
      double divisor = f5.compute(note);
      if (divisor == 0) {
         divisor = 1;
      }

      return (xn - x0) / divisor;
   }

}
