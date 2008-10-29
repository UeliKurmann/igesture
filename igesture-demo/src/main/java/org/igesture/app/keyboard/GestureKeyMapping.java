/*
 * @(#)GestureKeyMapping.java   1.0   March 09, 2007
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Gesture-to-keyboard mapping.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Mar 09, 2007     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.igesture.app.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.sigtec.util.Constant;
import org.ximtec.igesture.io.Win32KeyboardProxy;


/**
 * Gesture-to-keyboard mapping.
 * 
 * @version 1.0 Mar 2007
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureKeyMapping {

   private static final String REGEX = "\\+";
   private static final String KEY_NOT_RECOGNISED = "Key not recognised!";

   private Integer[] keys;
   private String gestureName;


   public GestureKeyMapping(String gesture, String keys) {
      super();
      setKeys(keys);
      this.gestureName = gesture;
   }


   public void setGestureName(String gestureName) {
      this.gestureName = gestureName;
   } // setGestureName


   public String getGestureName() {
      return gestureName;
   } // getGestureName


   public void setKeys(Integer[] keys) {
      this.keys = keys;
   } // setKeys


   public void setKeys(String keys) {
      List<Integer> codes = new ArrayList<Integer>();

      for (String key : keys.split(REGEX)) {
         key = key.trim();
         int code = Win32KeyboardProxy.getKey(key);

         if (code > 0) {
            codes.add(code);
         }
         else {
            throw new IllegalStateException(KEY_NOT_RECOGNISED);
         }

      }

      this.keys = codes.toArray(new Integer[0]);
   } // setKeys


   public Integer[] getKeys() {
      return keys;
   } // getKeys


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(Constant.OPEN_ANGULAR_BRACKET);

      for (int key : keys) {
         sb.append(key + Constant.PLUS);
      }

      sb.deleteCharAt(sb.length() - 1);
      sb.append(Constant.BLANK + Constant.SHORT_ARROW_RIGHT + Constant.BLANK
            + gestureName + Constant.CLOSE_ANGULAR_BRACKET);
      return sb.toString();
   } // toString

}
