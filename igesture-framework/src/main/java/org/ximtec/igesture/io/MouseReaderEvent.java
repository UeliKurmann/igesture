/*
 * @(#)MouseReaderEvent.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;

import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.InputDeviceEvent;


/**
 * Mouse reader event.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class MouseReaderEvent implements InputDeviceEvent {

   private TimestampedLocation location;


   /**
    * Constructs a new mouse reader event.
    * 
    * @param location the position captured by the mouse.
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
    * Returns the timestamped location.
    * 
    * @return the timestamped location.
    */
   public TimestampedLocation getTimestampedLocation() {
      return location;
   } // getTimestampedLocation

}
