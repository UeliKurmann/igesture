/*
 * @(#)F2.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Rubine Feature F2. Sine of the initial angle of the
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
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
 * Rubine Feature F2. Sine of the initial angle of the gesture.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F2 implements Feature {

	private static int minimalNumberOfPoints = 5;
	
   public double compute(Note note) throws FeatureException{
      final Trace trace = FeatureTool.createTrace(note);
      final Point p0 = trace.get(0);
      Point p2 = trace.get(2);

      if (p0.getX() == p2.getX() && p0.getY() == p2.getY()) {
         p2 = trace.get(4);
      }

      return (p2.getY() - p0.getY())
            / (Math.sqrt(Math.pow(p2.getX() - p0.getX(), 2)
                  + Math.pow(p2.getY() - p0.getY(), 2)));
   } // compute
   
   public int getMinimalNumberOfPoints() {
		return minimalNumberOfPoints;
	} // getMinimalNumberOfPoints

}
