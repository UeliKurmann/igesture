/*
 * @(#)$Id$
 *
 * Author       :   Croci Michele, mcroci@gmail.com,
 *                  based on a first version of Abdullah Jibaly
 *
 * Purpose      :  Example of setting an hook function for keyboard.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 14, 2007    crocimi     Initial Release
 * Nov 27, 2008     kuu         Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.keyboard;

import org.ximtec.igesture.io.win32.User32;
import org.ximtec.igesture.io.win32.Win32;


/**
 * Example of setting an hook function for keyboard. Implemented using JNA.
 * 
 * @author Michele Croci
 * @author Ueli Kurmann
 */

public class KeyboardUtils {

   private final static User32 user32 = Win32.USER32_INSTANCE;

   /**
    * Simulates a keyboard input 
    * @param key the key
    * @param status the status (UP, DOWN)
    */
   public void keyEvent(Key key, Key status) {
      user32.keybd_event(key.getKeyId(), 0, status.getKeyId(), 0);
   }

}
