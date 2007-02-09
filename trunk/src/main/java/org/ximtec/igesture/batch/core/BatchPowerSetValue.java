/*
 * @(#)BatchPowerSetValue.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Implements the power set parameter of the batch process
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


package org.ximtec.igesture.batch.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom.Element;
import org.ximtec.igesture.batch.BatchTools;


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
   }


   public List<String> getValues() {
      return values;
   }


   public static BatchPowerSetValue unmarshal(Element parameter) {
      final BatchPowerSetValue value = new BatchPowerSetValue();
      value.setMax(Integer.parseInt(parameter.getAttributeValue(ATTRIBUTE_MAX)));
      value.setMin(Integer.parseInt(parameter.getAttributeValue(ATTRIBUTE_MIN)));

      for (final String s : createPowerSet(parameter.getText(), value.getMin(),
            value.getMax())) {
         value.addValue(s);
      }
      return value;
   }


   private static List<String> createPowerSet(String list, int min, int max) {
      final List<String> result = new ArrayList<String>();
      final StringTokenizer tokenizer = new StringTokenizer(list.trim(), ",");
      final HashSet<String> set = new HashSet<String>();
      while (tokenizer.hasMoreTokens()) {
         final String token = tokenizer.nextToken();
         set.add(token);
      }
      HashSet<HashSet<String>> powerSet = BatchTools.getPowerSet(set);
      powerSet = BatchTools.filterSet(powerSet, min, max);
      for (final HashSet<String> hs : powerSet) {
         final StringBuilder sb = new StringBuilder();
         for (final String s : hs) {
            sb.append(s + ",");
         }
         if (sb.length() > 0) {
            result.add(sb.toString().substring(0, sb.length() - 1));
         }
         else {
            result.add(sb.toString());
         }
      }
      return result;
   }


   public int getMax() {
      return max;
   }


   public void setMax(int max) {
      this.max = max;
   }


   public int getMin() {
      return min;
   }


   public void setMin(int min) {
      this.min = min;
   }

}
