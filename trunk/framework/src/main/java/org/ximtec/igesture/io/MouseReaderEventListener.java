/*
 * @(#)MouseReaderEventListener.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Mouse Reader Event Listener
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

import org.ximtec.ipaper.io.AbstractInputDeviceEventListener;
import org.ximtec.ipaper.io.InputDevice;
import org.ximtec.ipaper.io.InputDeviceEvent;
import org.ximtec.ipaper.io.InputDeviceEventListener;


public class MouseReaderEventListener extends AbstractInputDeviceEventListener
      implements InputDeviceEventListener {

   public void inputDeviceEvent(InputDevice arg0, InputDeviceEvent event) {
      final MouseReaderEvent mouseEvent = (MouseReaderEvent) event;

      if (mouseEvent.getTimestampedLocation() != null) {
         fireLocationEvent(mouseEvent.getTimestampedLocation());
      }
   }
}