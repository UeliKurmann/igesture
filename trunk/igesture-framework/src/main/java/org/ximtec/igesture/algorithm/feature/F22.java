/*
 * @(#)F22.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   UK Feature F15. The sum of the relative distances
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
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
 * UK Feature F15. The sum of the relative distances between the strokes.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F22 implements Feature {

   public double compute(Note note) {
      double distance = 0;
      final List<Trace> traces = note.getTraces();

      for (int i = 1; i < traces.size(); i++) {
         final Trace t1 = traces.get(i - 1);
         final Trace t2 = traces.get(i);
         distance += Math.abs(t2.getStartPoint().distance(t1.getEndPoint()));
      }

      final double width = note.getBounds2D().getWidth();
      final double height = note.getBounds2D().getHeight();
      final double diag = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

      if (diag != 0) {
         return distance / diag;
      }

      return 1;
   } // compute

}