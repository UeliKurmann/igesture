/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   Rubine Feature F1. Cosine of the initial angle of the
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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
 * F26, proportion of points below / over the line start - endpoint
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F26 implements Feature {

	private static final int MINIMAL_NUMBER_OF_POINTS = 5;

	public double compute(Note note) throws FeatureException {
		if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
			throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
		}

		Trace trace = FeatureTool.createTrace(note);

		double m = (trace.getEndPoint().getY() - trace.getStartPoint().getY())
				/ (trace.getEndPoint().getX() - trace.getStartPoint().getX());

		double b = trace.getStartPoint().getY() - trace.getStartPoint().getX()
				* m;

		int below = 1;
		int above = 1;

		for (Point point : trace.getPoints()) {
			if (m * point.getX() + b > point.getY()) {
				below++;
			} else {
				above++;
			}
		}


		return (double) above / (double) below;
	} // compute

	public int getMinimalNumberOfPoints() {
		return MINIMAL_NUMBER_OF_POINTS;
	} // getMinimalNumberOfPoints

}
