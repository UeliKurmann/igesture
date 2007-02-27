/*
 * @(#)Constants.java	1.0   Dec 6, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Constants used by the Siger algorithm
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.algorithm.siger;

import org.sigtec.util.Constant;

/**
 * Comment
 * 
 * @version 1.0 Dec 6, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class Constants {

	// directions
	public static String N = "(N,|NW,|NE,)+";

	public static String NW = "(NW,|N,|W,)+";

	public static String W = "(W,|NW,|SW,)+";

	public static String SW = "(SW,|W,|S,)+";

	public static String S = "(S,|SW,|SE,)+";

	public static String SE = "(SE,|S,|E,)+";

	public static String E = "(E,|SE,|NE,)+";

	public static String NE = "(NE,|E,|N,)+";

	// start/end
	public static String START = "([A-Z]{1,2},){0,5}";

	public static String END = "([A-Z]{1,2},){0,5}";

	/**
	 * Returns the regular expression of the given direction
	 * 
	 * @param direction
	 *            the direction
	 * @return the regular expression
	 */
	public static String getRegex(Direction direction) {
		switch (direction) {
		case N:
			return N;
		case NW:
			return NW;
		case W:
			return W;
		case SW:
			return SW;
		case S:
			return S;
		case SE:
			return SE;
		case E:
			return E;
		case NE:
			return NE;
		default:
			return Constant.EMPTY_STRING;
		}
	}
}
