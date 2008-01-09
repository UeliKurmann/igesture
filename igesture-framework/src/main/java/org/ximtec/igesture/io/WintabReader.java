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

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.graphix.Orientation;
import org.sigtec.ink.input.CompleteLocation;
import org.sigtec.ink.input.Location;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.util.Constant;
import org.ximtec.igesture.io.wacom.TabletUtils;
import org.ximtec.igesture.io.wacom.WacomCallback;
import org.ximtec.igesture.io.wacom.Win32TabletProxy;
import org.ximtec.igesture.io.wacom.Wintab32.ORIENTATION;
import org.ximtec.igesture.io.wacom.Wintab32.ROTATION;



/**
 * A tablet reader.
 * 
 * @version 1.0, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 */
 
public class WintabReader extends org.sigtec.input.AbstractInputDevice implements
      ButtonDevice, WacomCallback {

   private static final Logger LOGGER = Logger.getLogger(WintabReader.class
         .getName());

   private static final int FREQUENCE = 20;
   
   final WintabReader tabletReader = this;

   private boolean lastKeyState = false;

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
      
      //NEW VERSION: callback --> DOESN'T NOT WORK YET!
      /*
       final Thread t = new Thread() {
          public void run(){
             TabletUtils tu = new TabletUtils(WintabReader.this);
          }
       };
       t.start();
*/
	
      // OLD VERSION: poll the device
      final Thread t2 = new Thread() {
    	 
         @Override
         public void run() {
        	Win32TabletProxy proxy = new Win32TabletProxy();
        	
        	 while (true) {
        	    
        	       proxy.getNextPacket();
        	       
        	   //    if(proxy.getLastPacket()!=null){
        	         // System.out.println("button: "+proxy.buttonPressed());
              	        if (proxy.buttonPressed()==5) {
              	         
                           Location location = new Location("screen", 1, proxy.getLastCursorLocation());
                           System.out.println(location+" - button: "+proxy.buttonPressed());
                           CompleteLocation tbl = null;
                         	  tbl = new CompleteLocation(location, proxy.getTimeStamp(), proxy.getPressure(),
                         	        new Orientation(proxy.getRotation().roYaw, proxy.getRotation().roPitch,
                         	              proxy.getRotation().roRoll));
                           tabletReader.fireInputDeviceEvent(new WintabReaderEvent(tbl));
                           
                          lastKeyState = true;
                         }
                         else {
      
                            if (lastKeyState) {
                               tabletReader.fireTabletButtonEvent(new InputDeviceEvent() {
                                  public long getTimestamp() {
                                     return System.currentTimeMillis();
                                  }
                               });
                               
                               lastKeyState = false;
                            }
                         }


                  try {
                     Thread.sleep(100 / FREQUENCE);
                  }
                  catch (InterruptedException e) {
                     LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
                  }
        //	 }
        	 }
            }
         };
      t2.start();
      
    
     
   } // init
   
  // public void callbackFunction(){

  // }
   
   public void callbackFunction(int x, int y, int z, int npress, int tpress, long timeStamp,
         ORIENTATION orientation, ROTATION rotation, int button){
         
      //   if(button==5){
            final WintabReader tabletReader = this;
            
            Location location = new Location("screen", 1, new Point(x,y));
            System.out.println(location+" - "+button);
            CompleteLocation tbl = null;
             tbl = new CompleteLocation(location, timeStamp, npress,
                   new Orientation(rotation.roYaw, rotation.roPitch,
                         rotation.roRoll));
             tabletReader.fireInputDeviceEvent(new WintabReaderEvent(tbl));
            
        // }
   }


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
      System.out.println("WintabReaderEventListener2!!!!");
      for (final ButtonDeviceEventListener listener : buttonUpEvents) {
         System.out.println("Listener: "+listener.toString());
         listener.handleButtonPressedEvent(event);
      }

   } // fireTabletButtonEvent
   
}

