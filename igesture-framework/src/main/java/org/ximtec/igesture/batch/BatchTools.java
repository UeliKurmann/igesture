/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Some helper methods.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch;

import java.io.File;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;
import org.ximtec.igesture.util.XMLTool;


/**
 * Some helper methods.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchTools {

   private static final String XSL_HTML = "xml/batch.xsl";
   private static final String OUT_FILE_HTML = "result.html";
   private static final String OUT_FILE_XML = "result.xml";


   /**
    * Filters a set of sets. The condition are the number of elements in the set.
    * 
    * @param powerSet the set to be filtered.
    * @param min the minimal number of elements in the set.
    * @param max the maximal number of elements in the set.
    * @return the filtered set.
    */
   public static HashSet<HashSet<String>> filterSet(
         HashSet<HashSet<String>> powerSet, int min, int max) {
      final HashSet<HashSet<String>> result = new HashSet<HashSet<String>>();

      for (final HashSet<String> set : powerSet) {

         if (set.size() >= min && set.size() <= max) {
            result.add(set);
         }

      }
      return result;
   } // filterSet


   /**
    * Creates the power set of a given set.
    * 
    * @param set the set a power set has to be created from.
    * @return the power set.
    */
   public static HashSet<HashSet<String>> getPowerSet(HashSet<String> set) {
      final HashSet<HashSet<String>> powerSet = new HashSet<HashSet<String>>();
      final HashSet<String> currSet = new HashSet<String>();
      return createPowerSet(powerSet, currSet, 0, set);
   } // getPowerSet


   /**
    * Creates the power set (recursively).
    */
   @SuppressWarnings("unchecked")
   private static HashSet<HashSet<String>> createPowerSet(
         HashSet<HashSet<String>> powerSet, HashSet<String> currentSet,
         int index, HashSet<String> set) {

      if (index == set.size()) {
         final HashSet<HashSet<String>> ps = (HashSet<HashSet<String>>)powerSet
               .clone();
         ps.add(currentSet);
         return ps;
      }
      else {
         powerSet = createPowerSet(powerSet, currentSet, index + 1, set);
         currentSet = (HashSet<String>)currentSet.clone();
         currentSet.add((String)set.toArray()[index]);
         powerSet = createPowerSet(powerSet, currentSet, index + 1, set);
         return powerSet;
      }

   } // createPowerSet


   public static void writeResultsOnDisk(File outputDirectory,
         BatchResultSet resultSet) {

      try {

         XMLTool.exportBatchResultSet(resultSet, new File(outputDirectory,
               OUT_FILE_XML));

         String html = XMLTool.transform(
               XMLTool.exportBatchResultSet(resultSet), BatchTools.class
                     .getClassLoader().getResourceAsStream(XSL_HTML));
         FileUtils.writeStringToFile(new File(outputDirectory, OUT_FILE_HTML),
               html);

      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

}
