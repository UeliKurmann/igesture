/*
 * @(#)$Id$
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Provides methods with XML import/export 
 *                  functionality.
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.xml;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.sigtec.jdom.JdomDocument;
import org.sigtec.util.FileHandler;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;


/**
 * Provides methods with XML import/export functionality.
 * 
 * @version 0.9, Dec 2006
 * @author Michele Croci, mcroci@gmail.com
 */
public class XMLGeco {

   public static final String ROOT_TAG = "gestureMappings";

   public static final String CONFIG_ROOT_TAG = "configuration";


   /**
    * Exports a gesture set.
    * 
    * @param set the gesture set to be exported.
    * @param file the XML file.
    */
   public static void exportProject(Collection<GestureToActionMapping> mappings,
         GestureSet gestureSet, String gestureSetFileName, File file) {
      final JdomDocument igestureDocument = new JdomDocument(ROOT_TAG);
      igestureDocument.attach(new JdomGestureSetName(gestureSetFileName));

      for (final GestureToActionMapping map : mappings) {
         igestureDocument.attach(new JdomGestureMapping(map, gestureSet));
      }

      FileHandler.writeFile(file.getPath(), igestureDocument.toXml());
   } // exportProject


   /**
    * Imports a configuration.
    * 
    * @param file the XML file
    * @return the configuration.
    */

   public static void exportGestureConfiguration(File file,
         List<String> devices, boolean[] arr, boolean min, String lastProjectPath) {
      final JdomDocument igestureDocument = new JdomDocument(CONFIG_ROOT_TAG);
      igestureDocument.attach(new JdomInputDevicesElement(devices, arr));
      igestureDocument.attach(new JdomMinimizeElement(min));
      igestureDocument.attach(new JdomLastProjectElement(lastProjectPath));
      FileHandler.writeFile(file.getPath(), igestureDocument.toXml());
   } // importConfiguration

}
