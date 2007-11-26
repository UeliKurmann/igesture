/*
 * @(#)GestureKeyMapping.java	1.0   Nov 19, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  Map a gesture to a keystroke
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 19, 2007		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.mapping;

import java.util.ArrayList;
import java.util.List;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.io.Win32KeyboardProxy;



/**
 * Comment
 * @version 1.0 Nov 19, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class GestureToKeyMapping extends GestureToActionMapping {

      private static final String REGEX = "\\+";
      private static final String KEY_NOT_RECOGNISED = "Key not recognised!";

      private Integer[] keys;
      private String[] stringKeys;
      
      private boolean ctrlSelected;
      private boolean altSelected;
      private boolean shiftSelected;
      private char selChar;



      public GestureToKeyMapping(GestureClass gesture, String keys) {
         super();
         setKeys(keys);
         this.gestureClass = gesture;
      }
      
      public void executeAction(){
         
      }


      public void setKeys(Integer[] keys) {
         this.keys = keys;
      } // setKeys


      public void setKeys(String keys) {
         List<Integer> codes = new ArrayList<Integer>();
         stringKeys = keys.split(REGEX);
         int length= stringKeys.length;
         for (String key : keys.split(REGEX)) {
            key = key.trim();
            if (length==1){
               selChar = key.charAt(0);
            }else{
               if(key.equals("CONTROL")){
                  ctrlSelected = true;
               }
               else if(key.equals("ALT")){
                  altSelected = true;
               }
               else if(key.equals("SHIFT")){
                  shiftSelected = true;
               }
            }
            int code = Win32KeyboardProxy.getKey(key);

            if (code > 0) {
               codes.add(code);
            }
            else {
               throw new IllegalStateException(KEY_NOT_RECOGNISED);
            }
            length--;

         }

         this.keys = codes.toArray(new Integer[0]);
      } // setKeys


      public Integer[] getKeys() {
         return keys;
      } // getKeys
      
      public String[] getStringKeys() {
         return stringKeys;
      } // getstringKeys


      @Override
      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append(Constant.OPEN_ANGULAR_BRACKET);

         for (int key : keys) {
            sb.append(key + Constant.PLUS);
         }

         sb.deleteCharAt(sb.length() - 1);
         sb.append(Constant.BLANK + Constant.SHORT_ARROW_RIGHT + Constant.BLANK
               + gestureClass.getName() + Constant.CLOSE_ANGULAR_BRACKET);
         return sb.toString();
      } // toString

      
        public boolean isCtrlSelected(){
           return ctrlSelected;
        }
        
        public boolean isAltSelected(){
           return altSelected;
        }
        
        public boolean isShiftSelected(){
           return shiftSelected;
        }
        
        public char getSelectedChar(){
           return selChar;
        }


}
