/*
 * @(#)BatchPowerSetValue.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Implements the power set parameter of the batch
 *                  process.
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


package org.ximtec.igesture.batch.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom.Element;
import org.sigtec.util.Constant;
import org.ximtec.igesture.batch.BatchTools;


/**
 * Implements the power set parameter of the batch process.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchPowerSetValue {

   public static String ROOT_TAG = "powerset";

   public static String ATTRIBUTE_MIN = "min";

   public static String ATTRIBUTE_MAX = "max";

   private List<String> values;

   private int min;

   private int max;


   public BatchPowerSetValue() {
      values = new ArrayList<String>();
   }


   public void addValue(String value) {
      values.add(value);
   } // addValue


   public List<String> getValues() {
      return values;
   } // getValues


   public static BatchPowerSetValue unmarshal(Element parameter) {
      final BatchPowerSetValue value = new BatchPowerSetValue();
      value.setMax(Integer.parseInt(parameter.getAttributeValue(ATTRIBUTE_MAX)));
      value.setMin(Integer.parseInt(parameter.getAttributeValue(ATTRIBUTE_MIN)));

      for (final String s : createPowerSet(parameter.getText(), value.getMin(),
            value.getMax())) {
         value.addValue(s);
      }

      return value;
   } // unmarshal


   /**
    * Creates the powerset for the given list.
    * 
    * @param list the comma seperated list.
    * @param min the minimal lenght of the lists.
    * @param max the maximal lenght of the lists.
    * @return the list containing the powerset of lists with the given
    *         constraint.
    */
   private static List<String> createPowerSet(String list, int min, int max) {
      final List<String> result = new ArrayList<String>();
      final StringTokenizer tokenizer = new StringTokenizer(list.trim(),
            Constant.COMMA);
      final HashSet<String> set = new HashSet<String>();

      while (tokenizer.hasMoreTokens()) {
         final String token = tokenizer.nextToken();
         set.add(token);
      }

      HashSet<HashSet<String>> powerSet = BatchTools.getPowerSet(set);
      powerSet = BatchTools.filterSet(powerSet, min, max);

      for (final HashSet<String> hashSet : powerSet) {
         final StringBuilder stringBuilder = new StringBuilder();

         for (final String s : hashSet) {
            stringBuilder.append(s + Constant.COMMA);
         }

         if (stringBuilder.length() > 0) {
            result.add(stringBuilder.toString().substring(0,
                  stringBuilder.length() - 1));
         }
         else {
            result.add(stringBuilder.toString());
         }

      }
      return result;
   } // createPowerSet


   /**
    * Sets the maximal lenght of the list,
    * 
    * @param max the maximal lenght of the list. The value should be not larger
    *           than the lenght of the original list but it should be larger than
    *           getMin().
    */
   public void setMax(int max) {
      this.max = max;
   } // setMax


   /**
    * Returns the maximal lenght of the list.
    * 
    * @return the maximal lenght of the list.
    */
   public int getMax() {
      return max;
   } // getMax


   /**
    * Sets the minimal lenght of the list.
    * 
    * @param min the minimal lenght of the list. Th evalue should be larger than
    *           0 and smaller than getMax().
    */
   public void setMin(int min) {
      this.min = min;
   } // setMin


   /**
    * Returns the minimal lenght of the list.
    * 
    * @return the minimal lenght of the powerset. The lenght should be larger
    *         than 0 and smaller than getMax().
    */
   public int getMin() {
      return min;
   } // getMin

}
