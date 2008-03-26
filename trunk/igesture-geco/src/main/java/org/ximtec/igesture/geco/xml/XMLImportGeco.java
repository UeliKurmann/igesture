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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.xml;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdom.Document;
import org.jdom.Element;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.geco.gui.Constant;
import org.ximtec.igesture.geco.gui.MainModel;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.geco.userAction.CommandExecutor;
import org.ximtec.igesture.geco.userAction.KeyboardSimulation;
import org.ximtec.igesture.util.XMLTool;


public class XMLImportGeco {

   private static final String GESTURE = "gesture";
   private static final String KEY = "key";
   private static final String COMMAND = "command";
   private static final String ROOT_TAG = "gestureMapping";
   private static final String GESTURE_SET = "gestureSet";

   private List<GestureToActionMapping> mappings;

   private static GestureSet gestureSet;
   private MainView view;
   private String gestureSetFileName;

   private boolean importError;


   public XMLImportGeco(MainView view) {
      this.view = view;
   }


   /**
    * Imports a project.
    * 
    * @param file the XML file in which the project is saved.
    */
   public void importProject(File file) {
      GestureSet newGestureSet = null;
      mappings = new ArrayList<GestureToActionMapping>();
      final Document document = XMLTool.importDocument(file);
      final Element gestureSetElement = document.getRootElement().getChild(
            GESTURE_SET);

      // import gesture set
      String gsFile = gestureSetElement.getText();
      File f = null;
      if (gsFile.contains(Constant.BACKSLASH)) {
         // load from specified position
         f = new File(gsFile);
         gestureSetFileName = f.getAbsolutePath();

         if (f.exists()) {
            newGestureSet = XMLTool.importGestureSet(f).get(0);
         }

      }
      else { // load from classpath
         newGestureSet = XMLTool.importGestureSet(
               ClassLoader.getSystemResourceAsStream(MainModel.GESTURE_SET))
               .get(0);
      }

      if (newGestureSet != null) {
         gestureSet = newGestureSet;

         final List<Element> mappingElements = (List<Element>)document
               .getRootElement().getChildren(ROOT_TAG);

         for (final Element mappingElement : mappingElements) {
            String key = mappingElement.getChildText(KEY);
            String gesture = mappingElement.getChildText(GESTURE);
            if (key != null) {
               mappings.add(new GestureToActionMapping(gestureSet
                     .getGestureClass(gesture), new KeyboardSimulation(key)));
            }
            else {
               String cmd = mappingElement.getChildText(COMMAND);
               if (cmd != null) {
                  mappings.add(new GestureToActionMapping(gestureSet
                        .getGestureClass(gesture), new CommandExecutor(cmd)));
               }
            }
         }

      }
      else {
         // file not exists, display error message!
         importError = true;
         gestureSetFileName = Constant.EMPTY_STRING;
         JOptionPane.showMessageDialog(view, Constant.GESTURE_SET_NOT_FOUND);
      }

   } // importKeyMappings


   /**
    * Returns the mappings saved in the xml file.
    * 
    * @return the list of mappings
    */
   public List<GestureToActionMapping> getMappings() {
      return mappings;
   }


   /**
    * Returns the GestureSet saved in the xml
    * 
    * @return the gesture set
    */
   public GestureSet getGestureSet() {
      return gestureSet;
   }


   public String getGestureSetFileName() {
      return gestureSetFileName;
   }


   public boolean hasError() {
      return importError;
   }

}
