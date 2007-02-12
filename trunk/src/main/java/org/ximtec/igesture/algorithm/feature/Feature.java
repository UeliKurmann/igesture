/*
 * @(#)Feature.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Interface implemented by features describing gestures
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;

public interface Feature {

	/**
	 * Computes the feature
	 * 
	 * @param note
	 *            the note to be used
	 * @return the value of the feature
	 */
	public double compute(Note note);

}
