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
 * UK Feature F16
 * Proportion of the stroke lenghts (first/last point) vs. the lenght of the gesture
 * (point to point distance)
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


public class F14 implements Feature {

   public double compute(Note note) {
      double traceLength = 0;
      double gestureLength = 0;

      for (final Trace trace : note.getTraces()) {
         if (trace.getMinDistance() > 0) {
            traceLength += Math.abs(trace.getStartPoint().distance(
                  trace.getEndPoint()));
            gestureLength += trace.getLength();
         }
      }

      if (Double.isNaN(gestureLength / traceLength)) {
         return 1;
      }

      return gestureLength / traceLength;
   }
}
