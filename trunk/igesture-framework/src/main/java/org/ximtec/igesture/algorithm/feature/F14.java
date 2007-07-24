/*
 * @(#)F14.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   UK Feature F16. Proportion of the stroke lenghts
 *                  (first/last point) vs. the lenght of the gesture
 *                  (point to point distance).
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
import org.sigtec.ink.Trace;


/**
 * UK Feature F16. Proportion of the stroke lenghts (first/last point) vs. the
 * lenght of the gesture (point to point distance)
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F14 implements Feature {

   private static final int MINIMAL_NUMBER_OF_POINTS = 2;
   
   public double compute(Note note) throws FeatureException {
      if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
         throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
      }

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
   } // compute
   

   public int getMinimalNumberOfPoints() {
      return MINIMAL_NUMBER_OF_POINTS;
   } // getMinimalNumberOfPoints

}
