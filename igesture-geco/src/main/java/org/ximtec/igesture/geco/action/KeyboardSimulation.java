/*
 * @(#)$Id$
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
 * Nov 27, 2008     kurmannu    Cleanup, Refactoring
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.action;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.GestureAction;
import org.ximtec.igesture.io.keyboard.Key;
import org.ximtec.igesture.io.keyboard.Win32KeyboardProxy;


/**
 * @version 0.9, Mar 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Ueli Kurmann, ueli.kurmann@bbv.ch
 */
public class KeyboardSimulation implements GestureAction {

   private Key[] keys;


   /**
    * Constructor.
    * 
    * @param keys the keys corresponding to the action
    */
   public KeyboardSimulation(String keys) {
      setKeys(Key.parseKeyList(keys));
   }// KeyboardSimulationAction


   public void actionPerformed(ResultSet resultSet) {
      Win32KeyboardProxy.pressKey(keys);
   } // run


   /**
    * Set the keys to be pressed.
    * 
    * @param keys the keys corresponding to the action
    */
   public void setKeys(Key[] keys) {
      this.keys = keys;
   } // setKeys


   /**
    * Returns the selected keys
    * 
    * @return the keys to be pressed
    */
   public Key[] getAllKeys() {
      return keys;
   }// toString


   /**
    * Returns a description of the action
    * 
    * @return the keys to be pressed
    */
   public String toString() {

      StringBuilder sb = new StringBuilder();
      for (Key key : keys) {
         sb.append(key.toString());
         sb.append(Constant.PLUS);
      }

      return sb.length() > 0 ? sb.deleteCharAt(sb.length() - 1).toString() : sb
            .toString();
   }// toString

}
