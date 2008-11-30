/*
 * @(#)$Id$
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      : 	Tablet reader event listener.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2007     crocimi	    Initial Release
 *      
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.wacomclient;

import org.sigtec.input.AbstractInputDeviceEventListener;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.input.InputDeviceEventListener;


/**
 * Tablet reader event listener.
 * 
 * @version 1.0, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 */

public class WacomReaderEventListener extends AbstractInputDeviceEventListener
      implements InputDeviceEventListener {

   public void inputDeviceEvent(InputDevice arg0, InputDeviceEvent event) {
      final WacomReaderEvent tabletEvent = (WacomReaderEvent)event;

      if (tabletEvent.getTimestampedLocation() != null) {
         fireLocationEvent(tabletEvent.getTimestampedLocation());
      }

   }

}
