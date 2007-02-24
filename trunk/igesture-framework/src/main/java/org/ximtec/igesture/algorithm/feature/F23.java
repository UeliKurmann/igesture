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
 * UK Feature F17
 * Sum of angles between strokes
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;
import org.sigtec.ink.TraceTool;


public class F23 implements Feature {

   public double compute(Note note) {
      double angleSum = 0;

      final List<Trace> traces = FeatureTools.removeShortTraces(
            note.getTraces(), 3);
      for (int i = 1; i < traces.size(); i++) {
         final Trace t1 = TraceTool.filterTrace(traces.get(i - 1), 3);
         final Trace t2 = TraceTool.filterTrace(traces.get(i), 3);

         final double angle1 = FeatureTools.getAngle(t1.getStartPoint(), t1
               .getEndPoint());
         final double angle2 = FeatureTools.getAngle(t2.getStartPoint(), t2
               .getEndPoint());

         angleSum += Math.max(angle1, angle2) - Math.min(angle1, angle2);

      }

      return angleSum;
   }
}
