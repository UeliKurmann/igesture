/*
 * @(#)F5.java   1.0   Dec 26, 2006
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
 * Rubine Feature F5
 * The distance betweend the first and the last point
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


public class F5 implements Feature {

   public double compute(Note note) {
      final Trace trace = FeatureTools.createTrace(note);

      final double xn = trace.getEndPoint().getX();
      final double x0 = trace.getStartPoint().getX();
      final double yn = trace.getEndPoint().getY();
      final double y0 = trace.getStartPoint().getY();

      return Math.sqrt(Math.pow(xn - x0, 2) + Math.pow(yn - y0, 2));
   }

}
