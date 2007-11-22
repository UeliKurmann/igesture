/*
 * @(#)GestureKeyMapping.java	1.0   Nov 19, 2007
 *
 * Author		:	Beat Signer, signer@inf.ethz.ch
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					bsigner		Initial Release
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
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureKeyMapping extends GestureMapping{

      private static final String REGEX = "\\+";
      private static final String KEY_NOT_RECOGNISED = "Key not recognised!";

      private Integer[] keys;



      public GestureKeyMapping(GestureClass gesture, String keys) {
         super();
         setKeys(keys);
         this.gestureClass = gesture;
         this.test=1;
      }
      
      public void executeAction(){
         
      }


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
               + gestureClass.getName() + Constant.CLOSE_ANGULAR_BRACKET);
         return sb.toString();
      } // toString



}
