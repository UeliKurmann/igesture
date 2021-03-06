/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   Rubine feature F1. Cosine of the initial angle of the
 *                  gesture.
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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
 * Rubine feature F1. Cosine of the initial angle of the gesture.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class F1 implements Feature {

   private static final int MINIMAL_NUMBER_OF_POINTS = 5;


   /* (non-Javadoc)
    * @see org.ximtec.igesture.algorithm.feature.Feature#compute(org.sigtec.ink.Note)
    */
   public double compute(Note note) throws FeatureException {
      if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
         throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
      }

      final Trace trace = FeatureTool.createTrace(note);
      final Point p0 = trace.get(0);
      Point p2 = trace.get(2);

      if (p0.getX() == p2.getX() && p0.getY() == p2.getY()) {
         p2 = trace.get(4);
      }

      return (p2.getX() - p0.getX())
            / (Math.sqrt(Math.pow(p2.getX() - p0.getX(), 2)
                  + Math.pow(p2.getY() - p0.getY(), 2)));
   } // compute


   public int getMinimalNumberOfPoints() {
      return MINIMAL_NUMBER_OF_POINTS;
   } // getMinimalNumberOfPoints

}
