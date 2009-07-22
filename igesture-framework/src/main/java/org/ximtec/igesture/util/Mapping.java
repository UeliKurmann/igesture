/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   Name value mapping.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.sigtec.jdom.id.Factory;
import org.sigtec.util.Constant;
import org.xml.sax.InputSource;


/**
 * Name value mapping.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Mapping {

   private static final Logger LOGGER = Logger
         .getLogger(Mapping.class.getName());

   public static final int UNDEFINED = -1;

   public static final String MAP_TAG = "map";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String VALUE_ATTRIBUTE = "value";

   private static HashMap<String, Integer> str2int;

   private static HashMap<Integer, String> int2str;

   private static boolean initialised;

   static {
      initialised = false;
   }


   @SuppressWarnings("unchecked")
   public static void init(File file) {
      str2int = new HashMap<String, Integer>();
      int2str = new HashMap<Integer, String>();

      try {
         final InputStream inputStream = new FileInputStream(file);
         final SAXBuilder builder = new SAXBuilder(false);
         builder.setFactory(new Factory());
         builder.setIgnoringElementContentWhitespace(true);
         final InputSource is = new InputSource(inputStream);
         final Document document = builder.build(is);

         for (final Element element : (List<Element>)document.getRootElement()
               .getChildren(MAP_TAG)) {
            final String value = element.getAttributeValue(VALUE_ATTRIBUTE);
            final String name = element.getAttributeValue(NAME_ATTRIBUTE);
            str2int.put(name, Integer.valueOf(value));
            int2str.put(Integer.valueOf(value), name);
         }

      }
      catch (final FileNotFoundException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final JDOMException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

      initialised = true;
   } // init


   private static void testInit() {
      if (!initialised) {
         throw new RuntimeException("Mapping: Class not initialised!");
      }

   } // testInit


   /**
    * Converts a name to an integer.
    * @param name the name to be converted.
    * @return the integer value.
    */
   public static final int toInt(String name) {
      testInit();
      final Integer result = str2int.get(name);
      return (result != null) ? result.intValue() : UNDEFINED;
   } // toInt


   /**
    * Returns the name.
    * @param value the integer value.
    * @return the name.
    */
   public static final String toString(int value) {
      testInit();
      final String result = int2str.get(value);
      return (result != null) ? result : null;
   } // toString

}
