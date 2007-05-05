/*
 * @(#)F5.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Rubine Feature F5. The distance betweend the first
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
 * Rubine Feature F5. The distance betweend the first and the last point.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F5 implements Feature {
	
	private static int minimalNumberOfPoints = 2;

   public double compute(Note note) throws FeatureException{
      final Trace trace = FeatureTool.createTrace(note);
      final double xn = trace.getEndPoint().getX();
      final double x0 = trace.getStartPoint().getX();
      final double yn = trace.getEndPoint().getY();
      final double y0 = trace.getStartPoint().getY();
      return Math.sqrt(Math.pow(xn - x0, 2) + Math.pow(yn - y0, 2));
   } // compute
   
   public int getMinimalNumberOfPoints() {
		return minimalNumberOfPoints;
	} // getMinimalNumberOfPoints

}
