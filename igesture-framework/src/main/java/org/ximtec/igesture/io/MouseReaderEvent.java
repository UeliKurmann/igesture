/*
 * @(#)MouseReaderEvent.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Mouse reader event.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;

import org.sigtec.ink.input.TimestampedInputEvent;
import org.sigtec.input.InputDeviceEvent;


/**
 * Mouse reader event.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class MouseReaderEvent implements InputDeviceEvent {

   private TimestampedInputEvent timestampedInputEvent;


   /**
    * Constructs a new mouse reader event.
    * 
    * @param location the position captured by the mouse.
    */
   public MouseReaderEvent(TimestampedInputEvent location) {
      this.timestampedInputEvent = location;
   }


   /**
    * Returns the time when the event occurred.
    * 
    * @return the time when the event occurred.
    */
   public long getTimestamp() {
      return timestampedInputEvent.getTimestamp();
   } // getTimestamp


   /**
    * Returns the time stamped location.
    * 
    * @return the time stamped location.
    */
   public TimestampedInputEvent getTimestampedEvent() { 
      return timestampedInputEvent;
   } // getTimestampedLocation

}
