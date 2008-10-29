/*
 * @(#)F5.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   Rubine feature F5. The distance between the first
 *                  and the last point.
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
import org.sigtec.ink.Trace;


/**
 * Rubine feature F5. The distance between the first and the last point.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F5 implements Feature {

   private static final int MINIMAL_NUMBER_OF_POINTS = 2;


   public double compute(Note note) throws FeatureException {
      if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
         throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
      }

      final Trace trace = FeatureTool.createTrace(note);
      final double xn = trace.getEndPoint().getX();
      final double x0 = trace.getStartPoint().getX();
      final double yn = trace.getEndPoint().getY();
      final double y0 = trace.getStartPoint().getY();
      return Math.sqrt(Math.pow(xn - x0, 2) + Math.pow(yn - y0, 2));
   } // compute


   public int getMinimalNumberOfPoints() {
      return MINIMAL_NUMBER_OF_POINTS;
   } // getMinimalNumberOfPoints

}
