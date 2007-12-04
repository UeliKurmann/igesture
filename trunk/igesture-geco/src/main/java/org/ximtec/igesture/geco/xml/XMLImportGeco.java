/*
 * @(#)XMLImport.java   1.0   March 09, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Import geco project
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.geco.xml;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.geco.UserAction.KeyboardSimulationAction;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;


public class XMLImportGeco {

   private static final String GESTURE = "gesture";
   private static final String KEY = "key";
   private static String ROOT_TAG = "gestureMapping";
   private static String GESTURE_SET ="gestureSet";
   
   private List<GestureToActionMapping> mappings;
   
   private static GestureSet gestureSet;
   
   
   public void exportProject(File file, List<GestureToActionMapping> mappings, GestureSet gestureSet) {
      
      for(GestureToActionMapping map : mappings){
         
      }

   } // importKeyMappings


   public void importProject(File file) {
      mappings = new ArrayList<GestureToActionMapping>();
      final Document document = XMLGeco.importDocument(file);
      final Element gestureSetElement = document.getRootElement().getChild(GESTURE_SET);
    
      //import gesture set
      String fileName="";
      if(gestureSetElement.getText().equals("MicrosoftApplicationGestures")){
         fileName = "gestureSets/ms_application_gestures.xml";
         gestureSet = XMLGeco.importGestureSet(
               new File(ClassLoader.getSystemResource(fileName).getFile())).get(0);
      }

      final List<Element> mappingElements = document.getRootElement()
            .getChildren(ROOT_TAG);

      //TODO: if the action is not a keypress?
      for (final Element mappingElement : mappingElements) {
         String key = mappingElement.getChildText(KEY);
         String gesture = mappingElement.getChildText(GESTURE);
         mappings.add(new GestureToActionMapping( gestureSet.getGestureClass(gesture), new KeyboardSimulationAction(key)));
      }
   } // importKeyMappings
   
   
   
   public List<GestureToActionMapping> getMappings(){
      return mappings;
   }
   
   public GestureSet getGestureSet(){
      return gestureSet;
   }

}
