/*
 * @(#)$Id$
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.igesture.app.keyboard;

import org.sigtec.util.Constant;
import org.ximtec.igesture.io.keyboard.Key;


/**
 * Gesture-to-keyboard mapping.
 * 
 * @version 1.0 Mar 2007
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class GestureKeyMapping {


   private Key[] keys;
   private String gestureName;


   public GestureKeyMapping(String gesture, Key... keys) {
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


   public void setKeys(Key[] keys) {
      this.keys = keys;
   } // setKeys


   public Key[] getKeys() {
      return keys;
   } // getKeys


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(Constant.OPEN_ANGULAR_BRACKET);

      for (Key key : keys) {
         sb.append(key + Constant.PLUS);
      }

      sb.deleteCharAt(sb.length() - 1);
      sb.append(Constant.BLANK + Constant.SHORT_ARROW_RIGHT + Constant.BLANK
            + gestureName + Constant.CLOSE_ANGULAR_BRACKET);
      return sb.toString();
   } // toString

}
