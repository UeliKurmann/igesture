/*
 * @(#)F24.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Feature representing the proportion of the stroke
 *                  lengths (first/last point) to each other.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.feature;

import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


/**
 * Feature representing the proportion of the stroke lengths (first/last point)
 * to each other.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F24 implements Feature {

   private static final int MINIMAL_NUMBER_OF_POINTS = 2;


   public double compute(Note note) throws FeatureException {
      if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
         throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
      }

      double proportion = 0;
      final List<Trace> traces = note.getTraces();

      if (traces.size() > 1) {

         for (final Trace trace : traces) {
            final double divisor = trace.getStartPoint().distance(
                  trace.getEndPoint());

            if (proportion == 0) {
               proportion = divisor;
            }
            else if (divisor != 0) {
               proportion /= divisor;
            }

         }

      }
      else {
         proportion = 1;
      }

      return proportion;
   } // compute


   public int getMinimalNumberOfPoints() {
      return MINIMAL_NUMBER_OF_POINTS;
   } // getMinimalNumberOfPoints

}
