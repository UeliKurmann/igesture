/*
 * @(#)BatchTools.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Helper metods used by the batch tools
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.batch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Toll methods used for batch processing
 * @author Ueli Kurmann
 * @version 1.0
 */
public class BatchTools {

	/**
	 * Returns a collection of String Elements. The input is a String List with
	 * a given delimiter.
	 * 
	 * @param list
	 * @param delimiter
	 * @return
	 */
	public static List<String> parseList(String list, String delimiter) {
		final StringTokenizer tokenizer = new StringTokenizer(list, delimiter);
		final List<String> elements = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			elements.add(tokenizer.nextToken());
		}
		return elements;
	}

	/**
	 * Filters a Set of Sets. The condition are the number of elements in the
	 * set.
	 * 
	 * @param powerSet
	 *            the set to filter
	 * @param min
	 *            the minimal number of elements in the set
	 * @param max
	 *            the maximal number of elements in the set
	 * @return the filtered set
	 */
	public static HashSet<HashSet<String>> filterSet(
			HashSet<HashSet<String>> powerSet, int min, int max) {
		final HashSet<HashSet<String>> result = new HashSet<HashSet<String>>();
		for (final HashSet<String> set : powerSet) {
			if (set.size() >= min && set.size() <= max) {
				result.add(set);
			}
		}
		return result;
	}

	/**
	 * Creates the power set of a given set.
	 * 
	 * @param set
	 *            the set to create the power set
	 * @return the power set of the set
	 */
	public static HashSet<HashSet<String>> getPowerSet(HashSet<String> set) {
		final HashSet<HashSet<String>> powerSet = new HashSet<HashSet<String>>();
		final HashSet<String> currSet = new HashSet<String>();
		return createPowerSet(powerSet, currSet, 0, set);
	}

	/**
	 * Creates the power set recursively
	 * 
	 * @param powerSet
	 * @param currentSet
	 * @param index
	 * @param set
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static HashSet<HashSet<String>> createPowerSet(
			HashSet<HashSet<String>> powerSet, HashSet<String> currentSet,
			int index, HashSet<String> set) {
		if (index == set.size()) {
			final HashSet<HashSet<String>> ps = (HashSet<HashSet<String>>) powerSet
					.clone();
			ps.add(currentSet);
			return ps;
		} else {
			powerSet = createPowerSet(powerSet, currentSet, index + 1, set);
			currentSet = (HashSet<String>) currentSet.clone();
			currentSet.add((String) set.toArray()[index]);
			powerSet = createPowerSet(powerSet, currentSet, index + 1, set);
			return powerSet;
		}
	}

}
