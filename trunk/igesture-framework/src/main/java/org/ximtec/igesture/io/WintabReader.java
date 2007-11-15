/*
 * @(#)TabletReader.java    1.0   Nov 15, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   A tablet reader.
 *                  
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2007		crocimi    Initial release    
 * 
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.graphix.Orientation;
import org.sigtec.ink.input.CompleteLocation;
import org.sigtec.ink.input.DetailedLocation;
import org.sigtec.ink.input.Location;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.util.Constant;
import org.ximtec.igesture.io.wacom.Win32TabletProxy;



/**
 * A tablet reader.
 * 
 * @version 1.0, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 */
 
public class WintabReader extends org.sigtec.input.AbstractInputDevice implements
      ButtonDevice {

   private static final Logger LOGGER = Logger.getLogger(WintabReader.class
         .getName());

   private static final int FREQUENCE = 20;

   //private boolean lastKeyState = false;

   private HashSet<ButtonDeviceEventListener> buttonUpEvents;


   public WintabReader() {
      init();
      buttonUpEvents = new HashSet<ButtonDeviceEventListener>();
   }


   /**
    * Initialises the tablet reader and starts a new thread polling the tablet.
    * 
    */
   public void init() {
      final WintabReader tabletReader = this;

	
      final Thread t = new Thread() {
    	 
         @Override
         public void run() {
        	Win32TabletProxy proxy = new Win32TabletProxy();
        	
        	 while (true) {
        	    //if (Win32TabletProxy. .isMiddleButtonPressed()) {
        	       proxy.getNextPacket();
        	       
        	       if(proxy.getLastPacket()!=null){
        	         
                     Location location = new Location("screen", 1, proxy.getLastCursorLocation());
                     System.out.println(location);
                     CompleteLocation tbl = null;
                   	  tbl = new CompleteLocation(location, proxy.getTimeStamp(), proxy.getPressure(),
                   	        new Orientation(proxy.getRotation().roYaw, proxy.getRotation().roPitch,
                   	              proxy.getRotation().roRoll));
                     tabletReader.fireInputDeviceEvent(new WintabReaderEvent(tbl));
                  }
   
                  try {
                     Thread.sleep(1000 / FREQUENCE);
                  }
                  catch (InterruptedException e) {
                     LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
                  }
        	 //}
        	 }
			
            }

         };

      t.start();
   } // init


   /**
    * Creates a list of tablet readers. Only one tablet reader is created.
    * 
    * @return a list of tablet readers.
    */
   public static List<WintabReader> createTablet() {
      final List<WintabReader> list = new ArrayList<WintabReader>();
      list.add(new WintabReader());
      return list;
   } // createtablet


   public void addButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonUpEvents.add(listener);
   } // addButtonDeviceEventListener


   public void removeButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonUpEvents.remove(listener);
   } // removeButtonDeviceEventListener


   private void fireTabletButtonEvent(InputDeviceEvent event) {
      for (final ButtonDeviceEventListener listener : buttonUpEvents) {
         listener.handleButtonPressedEvent(event);
      }

   } // fireTabletButtonEvent
   
}

