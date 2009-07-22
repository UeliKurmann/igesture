/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	A keyboard simulator.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Mar 09, 2007     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
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

import java.util.logging.Logger;



/**
 * A keyboard simulator.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Win32KeyboardProxy {

   private static final Logger LOGGER = Logger
         .getLogger(Win32KeyboardProxy.class.getName());

   private static KeyboardUtils keyboardUtils = new KeyboardUtils();


   /**
    * Simulates a keyboard input
    * @param keys the keys
    */
   public static void pressKey(Key... keys) {
      
      for (Key key : keys) {
         LOGGER.info("Key: " + key.toString() + "; State: " + Key.KEY_DOWN.toString());
         keyboardUtils.keyEvent(key, Key.KEY_DOWN);
      }

      for (Key key : keys) {
         LOGGER.info("Key: " + key.toString() + "; State: " + Key.KEY_UP.toString());
         keyboardUtils.keyEvent(key, Key.KEY_UP);
      }

   } // pressKey

}
