/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   Rubine feature F8. The total gesture length.
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
 * Rubine feature F8. The total gesture length.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class F8 implements Feature {

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

      double result = 0;

      for (int i = 0; i < trace.getPoints().size() - 1; i++) {
         final double deltaX = points[i + 1].getX() - points[i].getX();
         final double deltaY = points[i + 1].getY() - points[i].getY();
         result += Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
      }

      return result;
   } // compute


   public int getMinimalNumberOfPoints() {
      return MINIMAL_NUMBER_OF_POINTS;
   } // getMinimalNumberOfPoints

}
