/*
 * @(#)F167.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Feature representing the cosine of the angle between
 *                  the first and second part of the stroke.
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
 * Feature representing the cosine of the angle between the first and second part
 * of the stroke.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F17 implements Feature {

   private static final int MINIMAL_NUMBER_OF_POINTS = 3;


   public double computeOld(Note note) throws FeatureException {
      if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
         throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
      }

      final Trace trace = FeatureTool.createTrace(note);
      final double a1 = FeatureTool.getAngle(trace.getStartPoint(), trace
            .get(trace.size() / 2));
      final double a2 = FeatureTool.getAngle(trace.get(trace.size() / 2), trace
            .getEndPoint());
      return Math.sin(Math.toRadians(a1 - a2));
   } // computeOld


   public double compute(Note note) throws FeatureException {
      if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
         throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
      }

      double a = Math.pow(FeatureTool.computeD1(note), 2)
            + Math.pow(FeatureTool.computeD2(note), 2)
            - Math.pow(FeatureTool.computeD3(note), 2);
      double b = 2 * FeatureTool.computeD1(note) * FeatureTool.computeD2(note);
      return a / b;
   } // compute


   public int getMinimalNumberOfPoints() {
      return MINIMAL_NUMBER_OF_POINTS;
   } // getMinimalNumberOfPoints

}
