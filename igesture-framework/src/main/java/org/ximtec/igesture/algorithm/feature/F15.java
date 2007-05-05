/*
 * @(#)F15.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   UK Feature F20UK Feature F20. Number of stop points.
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

import java.util.ArrayList;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


/**
 * UK Feature F20. Number of stop points.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F15 implements Feature {

   private static final int MINIMAL_NUMBER_OF_POINTS = 3;

   private static final double AVERAGE_DIVISOR = 3;


   public double compute(Note note) throws FeatureException {
      if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
         throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
      }

      final Trace trace = FeatureTool.createTrace(note);
      final List<Double> velocities = new ArrayList<Double>();

      for (int i = 1; i < trace.size(); i++) {
         final double distance = trace.get(i - 1).distance(trace.get(i));
         final double deltaT = FeatureTool.getDeltaT(trace.get(i - 1), trace
               .get(i));

         if (deltaT > 0) {
            velocities.add(distance / deltaT);
         }

      }

      return detectLocalMinima(velocities);
   } // compute


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
   } // detectLocalMinima


   public double computeAverage(List<Double> list) {
      double sum = 0;

      for (final double d : list) {
         sum += d;
      }

      return sum / list.size();
   } // computeAverage


   public int getMinimalNumberOfPoints() {
      return MINIMAL_NUMBER_OF_POINTS;
   } // getMinimalNumberOfPoints

}
