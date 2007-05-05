/*
 * @(#)F23.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   UK Feature F17. Sum of angles between strokes.
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
import org.sigtec.ink.TraceTool;


/**
 * UK Feature F17. Sum of angles between strokes.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F23 implements Feature {

	private static int minimalNumberOfPoints = 2;
	
   public double compute(Note note) throws FeatureException{
      double angleSum = 0;
      final List<Trace> traces = FeatureTool.removeShortTraces(
            note.getTraces(), 3);

      for (int i = 1; i < traces.size(); i++) {
         final Trace t1 = TraceTool.filterTrace(traces.get(i - 1), 3);
         final Trace t2 = TraceTool.filterTrace(traces.get(i), 3);
         final double angle1 = FeatureTool.getAngle(t1.getStartPoint(), t1
               .getEndPoint());
         final double angle2 = FeatureTool.getAngle(t2.getStartPoint(), t2
               .getEndPoint());
         angleSum += Math.max(angle1, angle2) - Math.min(angle1, angle2);
      }

      return angleSum;
   } // compute
   
   public int getMinimalNumberOfPoints() {
		return minimalNumberOfPoints;
	} // getMinimalNumberOfPoints

}
