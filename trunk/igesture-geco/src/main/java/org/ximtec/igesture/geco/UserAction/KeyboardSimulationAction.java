
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

/**
 * Comment
 * @version 1.0 Nov 27, 2007
 * @author Michele Croci, mcroci@gmail.com
 */

package org.ximtec.igesture.geco.UserAction;

import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventHandler;
import org.ximtec.igesture.io.Win32KeyboardProxy;




/**
 * @version 1.0 Mar 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class KeyboardSimulationAction implements EventHandler{

   private Integer[] keys;
   private String stringKeys;
   
   private static final String REGEX = "\\+";
   private static final String KEY_NOT_RECOGNISED = "Key not recognised!";
   
   private boolean ctrlSelected;
   private boolean altSelected;
   private boolean shiftSelected;
   private String selChar;

   
   /**
    * Constructor.
    * 
    * @param keys the keys corresponding to the action
    */
   public KeyboardSimulationAction(String keys) {
      this.stringKeys = keys;
      setKeys(keys);
   }//KeyboardSimulationAction

   public void run(ResultSet resultSet) {
      Win32KeyboardProxy.pressKey(keys);
   } // run

   


   public void setKeys(Integer[] keys) {
      this.keys = keys;
   } // setKeys


   public void setKeys(String keys) {
      List<Integer> codes = new ArrayList<Integer>();
      int length= keys.split(REGEX).length;
      for (String key : keys.split(REGEX)) {
         key = key.trim();
         if (length==1){
            selChar = key.substring(0, 1);
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

   
   
   /**
    * Is 'control' selected?
    * 
    * @return true, if 'control' is selected
    */
   public boolean isCtrlSelected(){
      return ctrlSelected;
   }//isCtrlSelected
   
   /**
    * Is 'alt' selected?
    * 
    * @return true, if 'control' is selected
    */
   public boolean isAltSelected(){
      return altSelected;
   }//isAltSelected
   
   /**
    * Is 'shift' selected?
    * 
    * @return true, if 'shift' is selected
    */
   public boolean isShiftSelected(){
      return shiftSelected;
   }//isShiftSelected
   
   
   /**
    * Returns selected char
    * 
    * @return a String representing the selected char
    */
   public String getSelectedChar(){
      return selChar;
   }//getSelectedChar
   
   /**
    * Returns a description of the action
    * 
    * @return the keys to be pressed
    */
   public String toString(){
      return stringKeys;
   }//toString

   
   
}
