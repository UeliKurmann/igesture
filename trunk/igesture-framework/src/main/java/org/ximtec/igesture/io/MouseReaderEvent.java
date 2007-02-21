/*
 * @(#)MouseReaderEvent.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Mouse Reader Event
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

import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.InputDeviceEvent;


public class MouseReaderEvent implements InputDeviceEvent {

   private TimestampedLocation location;


   /**
    * Constructs a new MagicommPenEvent.
    * 
    * @param message the Anoto data packet captured by the Magicomm G303 pen.
    */
   public MouseReaderEvent(TimestampedLocation location) {
      this.location = location;
   }


   /**
    * Returns the time when the event occurred.
    * 
    * @return the time when the event occurred.
    */
   public long getTimestamp() {
      return location.getTimestamp();
   } // getTimestamp


   /**
    * Returns the timestamped location
    * 
    * @return the timestamped location
    */
   public TimestampedLocation getTimestampedLocation() {
      return location;
   }
}
