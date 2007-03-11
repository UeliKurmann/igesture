package org.igesture.app.keyboard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.ximtec.igesture.util.XMLTools;

public class XMLImport {

	private static final String GESTURE = "gesture";
	private static final String KEY = "key";
	private static String ROOT_TAG = "gestureMapping";

	public static List<GestureKeyMapping> importKeyMappings(File file) {
		final List<GestureKeyMapping> mappings = new ArrayList<GestureKeyMapping>();

		final Document document = XMLTools.importDocument(file);
		final List<Element> mappingElements = document.getRootElement()
				.getChildren(ROOT_TAG);
		for (final Element mappingElement : mappingElements) {
			String key = mappingElement.getChildText(KEY);
			String gesture = mappingElement.getChildText(GESTURE);
			mappings.add(new GestureKeyMapping(gesture, key));
		}

		return mappings;
	}
}
