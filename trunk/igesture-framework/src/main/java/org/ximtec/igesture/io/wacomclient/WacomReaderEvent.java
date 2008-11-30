/*
 * @(#)$Id$
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      : 	Tablet reader event.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2007     crocimi     Initial release    
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

import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.InputDeviceEvent;


/**
 * Tablet reader event.
 * 
 * @version 1.0, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class WacomReaderEvent implements InputDeviceEvent {

   private TimestampedLocation location;


   /**
    * Constructs a new tablet reader event.
    * 
    * @param location the position captured by the tablet.
    */
   public WacomReaderEvent(TimestampedLocation location) {
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
   
   
   /*
    * TODO: return others location type,
    *       provide other constructors
    */

}
