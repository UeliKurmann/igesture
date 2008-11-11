/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   Rubine feature F12. The maximum speed (squared) of
 *                  the gesture.
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

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;


/**
 * Rubine feature F12. The maximum speed (squared) of the gesture.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F12 implements Feature {

   private static final int MINIMAL_NUMBER_OF_POINTS = 2;


   public double compute(Note note) throws FeatureException {
      if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
         throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
      }

      final Trace trace = FeatureTool.createTrace(note);
      final Point[] points = new Point[trace.getPoints().size()];
      int j = 0;

      for (final Point p : trace.getPoints()) {
         points[j] = p;
         j++;
      }

      double max = Double.MIN_VALUE;

      for (int i = 0; i < trace.getPoints().size() - 1; i++) {
         final double result = getFraction(i, points);

         if (result > max) {
            max = result;
         }

      }

      return max;
   } // compute


   private double getFraction(int i, Point[] points) {
      final double divisor = Math.pow(FeatureTool.getDeltaX(i, points), 2)
            + Math.pow(FeatureTool.getDeltaY(i, points), 2);
      final double dividend = Math.pow(FeatureTool.getDeltaT(i, points), 2);
      return Math.atan(divisor / dividend);
   } // getFraction


   public int getMinimalNumberOfPoints() {
      return MINIMAL_NUMBER_OF_POINTS;
   } // getMinimalNumberOfPoints

}
