/*
 * @(#)SetTools.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Provides static methods with XML import/export 
 *                  functionality.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 23, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.util.adhoc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.util.GestureTool;
import org.ximtec.igesture.util.XMLTool;


public class SetTools {

   private static String PATH = "C:/workspace/igesture/src/main/data/sets/";


   public static void main(String[] args) {
      createMS();
   }


   public static void createGraffitiLetters() {
      List<GestureSet> list = new ArrayList<GestureSet>();
      list.add(XMLTool.importGestureSet(
            new File(PATH + "graffiti_numbers_bea.xml")).get(0));
      list.add(XMLTool.importGestureSet(
            new File(PATH + "graffiti_numbers_juerg.xml")).get(0));
      list.add(XMLTool.importGestureSet(
            new File(PATH + "graffiti_numbers_stefan.xml")).get(0));
      list.add(XMLTool.importGestureSet(
            new File(PATH + "graffiti_numbers_ueli_.xml")).get(0));
      GestureSet set = GestureTool.combineSampleData(list);
      XMLTool.exportGestureSet(set, new File(PATH + "graffiti_numbers.xml"));
   } // createGraffitiLetters


   public static void createMS() {
      List<GestureSet> list = new ArrayList<GestureSet>();
      list.add(XMLTool.importGestureSet(new File(PATH + "msgestures_ueli.xml"))
            .get(0));
      list.add(XMLTool.importGestureSet(new File(PATH + "msgestures5.xml")).get(
            0));
      GestureSet set = GestureTool.combineSampleData(list);
      XMLTool.exportGestureSet(set, new File(PATH
            + "ms_application_gestures.xml"));
   } // createGraffitiLetters

}
