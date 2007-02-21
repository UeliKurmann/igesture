/*
 * @(#)ButtonDeviceEventListener.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Event Listenr for Button Devices
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;

import org.ximtec.ipaper.io.InputDeviceEvent;


public interface ButtonDeviceEventListener {

   /**
    * Handles the event
    * 
    * @param event
    */
   public void handleButtonPressedEvent(InputDeviceEvent event);

}
