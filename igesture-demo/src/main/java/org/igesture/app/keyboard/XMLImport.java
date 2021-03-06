/*
 * @(#)$Id$
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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
import org.ximtec.igesture.io.keyboard.Key;
import org.ximtec.igesture.util.XMLTool;


public class XMLImport {

   private static final String GESTURE = "gesture";
   private static final String KEY = "key";
   private static String ROOT_TAG = "gestureMapping";


   @SuppressWarnings("unchecked")
   public static List<GestureKeyMapping> importKeyMappings(File file) {
      final List<GestureKeyMapping> mappings = new ArrayList<GestureKeyMapping>();
      final Document document = XMLTool.importDocument(file);
      final List<Element> mappingElements = document.getRootElement().getChildren(ROOT_TAG);

      for (final Element mappingElement : mappingElements) {
         String keyList = mappingElement.getChildText(KEY);
         String gesture = mappingElement.getChildText(GESTURE);
         try{
            mappings.add(new GestureKeyMapping(gesture, Key.parseKeyList(keyList)));
         }catch(Exception e){
            e.printStackTrace();
         }
      }

      return mappings;
   } // importKeyMappings

}
