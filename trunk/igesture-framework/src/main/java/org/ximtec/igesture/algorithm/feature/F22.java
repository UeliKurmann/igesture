/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   Feature representing the sum of the relative distances
 *                  between the strokes.
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

import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


/**
 * Feature representing the sum of the relative distances between the strokes.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class F22 implements Feature {

   private static final int MINIMAL_NUMBER_OF_POINTS = 2;


   public double compute(Note note) throws FeatureException {
      if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
         throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
      }

      double distance = 0;
      final List<Trace> traces = note.getTraces();

      for (int i = 1; i < traces.size(); i++) {
         final Trace t1 = traces.get(i - 1);
         final Trace t2 = traces.get(i);
         distance += t2.getStartPoint().distance(t1.getEndPoint());
      }

      final double width = note.getBounds2D().getWidth();
      final double height = note.getBounds2D().getHeight();
      final double diag = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

      if (diag != 0) {
         return distance / diag;
      }

      return 1;
   } // compute


   public int getMinimalNumberOfPoints() {
      return MINIMAL_NUMBER_OF_POINTS;
   } // getMinimalNumberOfPoints

}
