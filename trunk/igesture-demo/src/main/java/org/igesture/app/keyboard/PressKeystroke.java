/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	
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

import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.GestureAction;
import org.ximtec.igesture.io.keyboard.Key;
import org.ximtec.igesture.io.keyboard.Win32KeyboardProxy;


/**
 * @version 1.0 Mar 2007
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class PressKeystroke implements GestureAction {

   private Key[] keys;


   public PressKeystroke(Key... keys) {
      this.keys = keys;
   }


   public void actionPerformed(ResultSet resultSet) {
      Win32KeyboardProxy.pressKey(keys);
   } // run

}
