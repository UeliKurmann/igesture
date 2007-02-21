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
 * UK Feature F20
 * Number of stop points
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import java.util.ArrayList;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


public class F15 implements Feature {

   private static final double AVERAGE_DIVISOR = 3;


   public double compute(Note note) {
      final Trace trace = FeatureTools.createTrace(note);
      final List<Double> velocities = new ArrayList<Double>();

      for (int i = 1; i < trace.size(); i++) {
         final double distance = trace.get(i - 1).distance(trace.get(i));
         final double deltaT = FeatureTools.getDeltaT(trace.get(i - 1), trace
               .get(i));
         if (deltaT > 0) {
            velocities.add(distance / deltaT);
         }
      }

      return detectLocalMinima(velocities);
   }


   public int detectLocalMinima(List<Double> list) {
      int numOfMinimas = 0;
      final double average = computeAverage(list) / AVERAGE_DIVISOR;
      for (int i = 2; i < list.size(); i++) {
         if (list.get(i - 2) > list.get(i - 1) && list.get(i) > list.get(i - 1)
               && list.get(i) < average) {
            numOfMinimas++;
         }
      }
      return numOfMinimas;
   }


   public double computeAverage(List<Double> list) {
      double sum = 0;

      for (final double d : list) {
         sum += d;
      }

      return sum / list.size();
   }

}
