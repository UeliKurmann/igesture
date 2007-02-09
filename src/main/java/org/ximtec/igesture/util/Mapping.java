/*
 * @(#)Mapping.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Name value mapping
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


package org.ximtec.igesture.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.sigtec.jdom.id.Factory;
import org.xml.sax.InputSource;


public class Mapping {

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
         for (final Element element : (List<Element>) document.getRootElement()
               .getChildren(MAP_TAG)) {
            final String value = element.getAttributeValue(VALUE_ATTRIBUTE);
            final String name = element.getAttributeValue(NAME_ATTRIBUTE);
            str2int.put(name, Integer.valueOf(value));
            int2str.put(Integer.valueOf(value), name);
         }
      }
      catch (final FileNotFoundException e) {
         e.printStackTrace();
      }
      catch (final JDOMException e) {
         e.printStackTrace();
      }
      catch (final IOException e) {
         e.printStackTrace();
      }
      initialised = true;
   }


   private static void testInit() {
      if (!initialised) {
         throw new RuntimeException("Mapping: Class not initialised!");
      }
   }


   /**
    * Converts a name to an integer
    * @param name the name to convert
    * @return the integer value
    */
   public static final int toInt(String name) {
      testInit();
      final Integer result = str2int.get(name);
      return (result != null) ? result.intValue() : UNDEFINED;
   }

   /**
    * Returns the name
    * @param i the integer value
    * @return the name
    */
   public static final String toString(int i) {
      testInit();
      final String result = int2str.get(i);
      return (result != null) ? result : null;
   }
}
