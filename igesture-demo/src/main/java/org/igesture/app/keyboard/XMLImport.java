/*
 * @(#)XMLImport.java   1.0   March 09, 2007
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Mar 09, 2007     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.igesture.app.keyboard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.ximtec.igesture.util.XMLTool;


public class XMLImport {

   private static final String GESTURE = "gesture";
   private static final String KEY = "key";
   private static String ROOT_TAG = "gestureMapping";


   public static List<GestureKeyMapping> importKeyMappings(File file) {
      final List<GestureKeyMapping> mappings = new ArrayList<GestureKeyMapping>();
      final Document document = XMLTool.importDocument(file);
      final List<Element> mappingElements = document.getRootElement().getChildren(ROOT_TAG);

      for (final Element mappingElement : mappingElements) {
         String key = mappingElement.getChildText(KEY);
         String gesture = mappingElement.getChildText(GESTURE);
         mappings.add(new GestureKeyMapping(gesture, key));
      }

      return mappings;
   } // importKeyMappings

}
