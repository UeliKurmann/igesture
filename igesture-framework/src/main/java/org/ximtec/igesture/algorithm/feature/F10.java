/*
 * @(#)F10.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Rubine Feature F10. The sum of the absolute value of
 *                  the angle at each point.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 15, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;


/**
 * Rubine Feature F10. The sum of the absolute value of the angle at each point.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F10 implements Feature {

   public double compute(Note note) {
      final Trace trace = FeatureTool.createTrace(note);
      final Point[] points = new Point[trace.getPoints().size()];
      int j = 0;

      for (final Point p : trace.getPoints()) {
         points[j] = p;
         j++;
      }

      double result = 0;

      for (int i = 1; i < trace.getPoints().size() - 1; i++) {
         result += Math.abs(FeatureTool.roh(i, points));
      }

      return result;
   } // compute

}
