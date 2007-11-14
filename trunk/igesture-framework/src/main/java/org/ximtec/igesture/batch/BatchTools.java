/*
 * @(#)BatchTools.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Some helper methods.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchTools {

   /**
    * Returns a collection of string elements. The input is a string list with a
    * given delimiter.
    * 
    * @param list the string list.
    * @param delimiter the delimiter used within the string list.
    * @return the parsed list of string elements.
    */
   // FIXME: that is general functionality that could be added to sigtec. Would
   // that be ok for you Ueli if I move that in sigtec?
   public static List<String> parseList(String list, String delimiter) {
      final StringTokenizer tokenizer = new StringTokenizer(list, delimiter);
      final List<String> elements = new ArrayList<String>();

      while (tokenizer.hasMoreTokens()) {
         elements.add(tokenizer.nextToken());
      }

      return elements;
   } // parseList


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

}
