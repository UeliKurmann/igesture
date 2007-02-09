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
 * UK Feature F15
 * The sum of the relative distances between the strokes
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


public class F22 implements Feature {

   public double compute(Note note) {
      double distance = 0;
      final List<Trace> traces = note.getTraces();
      for (int i = 1; i < traces.size(); i++) {
         final Trace t1 = traces.get(i - 1);
         final Trace t2 = traces.get(i);
         distance += Math.abs(t2.getStartPoint().distance(t1.getEndPoint()));
      }
      final double width = note.getBounds2D().getWidth();
      final double height = note.getBounds2D().getHeight();
      final double diag = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

      if (diag != 0) {
         return distance / diag;
      }

      return 1;
   }
}
