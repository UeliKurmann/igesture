/*
 * @(#)JdomGestureSet.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   XML support for GestureSet 
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

package org.ximtec.igesture.core.jdom;

import java.util.List;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;

public class JdomGestureSet extends Element {

	public static final String ROOT_TAG = "set";

	public static final String NAME_ATTRIBUTE = "name";

	public static final String UUID_ATTRIBUTE = "id";

	public static final String REFID_ATTRIBUTE = "idref";

	public JdomGestureSet(GestureSet gestureSet) {
		super(ROOT_TAG);
		setAttribute(NAME_ATTRIBUTE, gestureSet.getName());
		setAttribute(UUID_ATTRIBUTE, gestureSet.getID());
		for (final GestureClass gestureClass : gestureSet.getGestureClasses()) {
			// addContent(new JdomGestureClass(gestureClass));
			final Element classElement = new Element(JdomGestureClass.ROOT_TAG);
			classElement.setAttribute(REFID_ATTRIBUTE, gestureClass.getID());
			addContent(classElement);
		}
	}

	@SuppressWarnings("unchecked")
	public static Object unmarshal(Element setElement) {
		
		final String name = setElement.getAttributeValue(NAME_ATTRIBUTE);
		final String uuid = setElement.getAttributeValue(UUID_ATTRIBUTE);
		final GestureSet gestureSet = new GestureSet(name);
		gestureSet.setID(uuid);
		for (final Element classElement : (List<Element>) setElement
				.getChildren(JdomGestureClass.ROOT_TAG)) {
			final String classID = classElement
					.getAttributeValue(REFID_ATTRIBUTE);
			GestureClass gestureClass = null;
			if (classID != null) {
				Element classInstance = null;
				try {
					classInstance = (Element) XPath.selectSingleNode(setElement
							.getParent(), "./" + JdomGestureClass.ROOT_TAG
							+ "[@" + UUID_ATTRIBUTE + "='" + classID + "']");
					gestureClass = (GestureClass) JdomGestureClass
							.unmarshal(classInstance);

				} catch (final JDOMException e) {
					e.printStackTrace();
				}
			} else {
				gestureClass = (GestureClass) JdomGestureClass
						.unmarshal(classElement);
			}
			gestureSet.addGestureClass(gestureClass);
		}
		return gestureSet;
	} // unmarshal

}