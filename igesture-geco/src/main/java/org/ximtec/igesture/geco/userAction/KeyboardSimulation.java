/*
 * @(#)KeyboardSimulator.java	1.0   Nov 27, 2007
 * 
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Simulate keyboard
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 27, 2007		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

/**
 * Comment
 * @version 0.9, Nov 27, 2007
 * @author Michele Croci, mcroci@gmail.com
 */


package org.ximtec.igesture.geco.userAction;

import java.util.ArrayList;
import java.util.List;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventHandler;
import org.ximtec.igesture.io.Win32KeyboardProxy;


/**
 * @version 0.9, Mar 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class KeyboardSimulation implements EventHandler {

   private Integer[] keys;
   private String stringKeys;

   private static final String REGEX = "\\+";
   private static final String KEY_NOT_RECOGNISED = "Key not recognised!";

   /**
    * Constructor.
    * 
    * @param keys the keys corresponding to the action
    */
   public KeyboardSimulation(String keys) {
      setKeys(keys);
   }// KeyboardSimulationAction


   public void run(ResultSet resultSet) {
      Win32KeyboardProxy.pressKey(keys);
   } // run


   /**
    * Set the keys to be pressed.
    * 
    * @param keys the keys corresponding to the action
    */
   public void setKeys(Integer[] keys) {
      this.keys = keys;
   } // setKeys


   /**
    * Set the keys to be pressed.
    * 
    * @param keys the keys corresponding to the action
    */
   public void setKeys(String keys) {
      this.stringKeys = keys;
      List<Integer> codes = new ArrayList<Integer>();
      // int length= keys.split(REGEX).length;
      for (String key : keys.split(REGEX)) {
         key = key.trim();
         key = key.replaceAll(Constant.BLANK, Constant.UNDERSCORE);
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


 
   /**
    * Returns the selected keys
    * 
    * @return the keys to be pressed
    */
   public String getAllKeys() {
      return stringKeys;
   }// toString


   /**
    * Returns a description of the action
    * 
    * @return the keys to be pressed
    */
   public String toString() {
      return stringKeys;
   }// toString

}
