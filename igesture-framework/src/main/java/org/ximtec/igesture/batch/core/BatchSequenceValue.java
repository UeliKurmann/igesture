/*
 * @(#)BatchSequenceValue.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Represents the value of a sequence. 
 * 					Used by the BatchProcess.
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

package org.ximtec.igesture.batch.core;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

public class BatchSequenceValue extends BatchParameter {

	public static String ROOT_TAG = "sequence";

	public static String CHILD_TAG = "value";

	List<String> values;

	public BatchSequenceValue() {
		values = new ArrayList<String>();
	}

	/**
	 * Adds a value
	 * @param value
	 */
	public void addValue(String value) {
		values.add(value);
	}

	/**
	 * Returns the list of values of the sequence
	 * 
	 * @return the list of values of the sequence
	 */
	public List<String> getValues() {
		return values;
	}

	/**
	 * Imports a BatchSequenceValue from an XML element
	 * @param parameterValue an XML element
	 * @return a BatchSequenceValue from an XML element
	 */
	@SuppressWarnings("unchecked")
	public static BatchSequenceValue unmarshal(Element parameterValue) {
		final BatchSequenceValue value = new BatchSequenceValue();
		final List<Element> children = parameterValue.getChildren(CHILD_TAG);
		for (final Element elem : children) {
			value.addValue(elem.getText());
		}
		return value;
	}
}
