/*
 * @(#)XMLGeco.java   1.0   Nov 28, 2007
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.sigtec.jdom.JdomDocument;
import org.sigtec.jdom.id.Factory;
import org.sigtec.util.Constant;
import org.sigtec.util.FileHandler;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.jdom.JdomGestureSet;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.xml.sax.InputSource;


/**
 * Provides methods with XML import/export functionality.
 * 
 * @version 0.9, Dec 2006
 * @author Michele Croci, mcroci@gmail.com
 */
public class XMLGeco {

   private static final Logger LOGGER = Logger
         .getLogger(XMLGeco.class.getName());

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
    * Imports an XML document.
    * 
    * @param file the XML file.
    * @return the JDOM document.
    */
   public static Document importDocument(File file) {
      Document document = null; // jdom Document

      try {
         final InputStream inputStream = new FileInputStream(file);
         final SAXBuilder builder = new SAXBuilder(false);
         builder.setFactory(new Factory());
         builder.setIgnoringElementContentWhitespace(true);
         final InputSource is = new InputSource(inputStream);
         document = builder.build(is);
      }
      catch (final IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final JDOMException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

      return document;
   } // importDocument


   /**
    * Imports a gesture set.
    * 
    * @param file the XML file.
    * @return a list of gesture sets.
    */
   @SuppressWarnings("unchecked")
   public static List<GestureSet> importGestureSet(File file) {
      final List<GestureSet> sets = new ArrayList<GestureSet>();
      final Document document = importDocument(file);
      final List<Element> algorithmElements = document.getRootElement()
            .getChildren(JdomGestureSet.ROOT_TAG);

      for (final Element setElement : algorithmElements) {
         sets.add((GestureSet)JdomGestureSet.unmarshal(setElement));
      }

      return sets;
   } // importGestureSet


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
