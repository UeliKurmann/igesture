/*
 * @(#)F19.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   UK Feature F24. Cos to x axis midpoint - endpont.
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
 * UK Feature F24. Cos to x axis midpoint - endpoint.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F19 implements Feature {

	private static int minimalNumberOfPoints = 3;

	public double compute(Note note) throws FeatureException{
		final Trace trace = FeatureTool.createTrace(note);
		final double a1 = FeatureTool.getAngle(trace.get(trace.size() / 2),
				trace.getEndPoint());
		return Math.cos(Math.toRadians(a1));
	} // compute

	public int getMinimalNumberOfPoints() {
		return minimalNumberOfPoints;
	} // getMinimalNumberOfPoints

}
