/*
 * @(#)XMLImportGeco.java   1.0   Nov 28, 2007
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
 * Nov 28, 2007     crocimi    Initial Release
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
import org.ximtec.igesture.geco.UserAction.CommandExecutor;
import org.ximtec.igesture.geco.UserAction.KeyboardSimulation;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;


public class XMLImportGeco {

   private static final String GESTURE = "gesture";
   private static final String KEY = "key";
   private static final String COMMAND = "command";
   private static String ROOT_TAG = "gestureMapping";
   private static String GESTURE_SET ="gestureSet";
   
   private List<GestureToActionMapping> mappings;
   
   private static GestureSet gestureSet;
   
   

   /**
    * Imports a project.
    * 
    * @param file the XML file in which the project is saved.
    */
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


      final List<Element> mappingElements = (List<Element>)document.getRootElement()
            .getChildren(ROOT_TAG);

      //TODO: if the action is not a keypress?
      for (final Element mappingElement : mappingElements) {
         String key = mappingElement.getChildText(KEY);
         String gesture = mappingElement.getChildText(GESTURE);
         if (key!=null){
            mappings.add(new GestureToActionMapping( gestureSet.getGestureClass(gesture), new KeyboardSimulation(key)));
         }
         else{ 
            String cmd = mappingElement.getChildText(COMMAND);
            if(cmd!=null){
               mappings.add(new GestureToActionMapping( gestureSet.getGestureClass(gesture), new CommandExecutor(cmd)));
            }
         }
      }
   } // importKeyMappings
   
   
   /**
    * Returns the mappings saved in the xml file.
    * 
    * @return the list of mappings
    */
   public List<GestureToActionMapping> getMappings(){
      return mappings;
   }
   
   /**
    * Returns the GestureSet saved in the xml
    * 
    * @return the gesture set
    */
   public GestureSet getGestureSet(){
      return gestureSet;
   }

}
