/*
 * @(#)WacomReader.java    1.0   Nov 15, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   A wacom tablet PC reader.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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
import org.ximtec.igesture.io.mouse.MouseUtils;
import org.ximtec.igesture.io.wacom.WacomUtils;
import org.ximtec.igesture.io.wacom.WacomCallback;
import org.ximtec.igesture.io.wacom.Win32TabletProxy;
import org.ximtec.igesture.io.wacom.Wintab32.ORIENTATION;
import org.ximtec.igesture.io.wacom.Wintab32.ROTATION;



/**
 * A wacom tablet PC reader.
 * 
 * @version 1.0, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 */
 
public class WacomReader extends ExtendedInputDevice implements
      ButtonDevice, WacomCallback {

   private static final Logger LOGGER = Logger.getLogger(WacomReader.class
         .getName());

   private static final int FREQUENCE = 20;
   final WacomReader tabletReader = this;
   private boolean lastKeyState = false;
   private WacomUtils wacomUtils;
   
   private int BUTTON_0 = 0;
   private int BUTTON_5 = 5;

   private HashSet<ButtonDeviceEventListener> buttonUpEvents;


   public WacomReader() {
      init();
      buttonUpEvents = new HashSet<ButtonDeviceEventListener>();
   }


   /**
    * Initialises the tablet reader and starts a new thread polling the tablet.
    * 
    */
   public void init() {
      final WacomReader tabletReader = this;
      
      //New version: callback
      init_callback();
	
      // Old version: poll the device
      //init_loop();
   } // init
   
   void init_callback(){

      wacomUtils = new WacomUtils(this);
      wacomUtils.start();
   }
   
   void init_loop(){
      
   final Thread t2 = new Thread() {
         
         @Override
         public void run() {
          
            Win32TabletProxy proxy = new Win32TabletProxy();
             while (!stop) {
                   proxy.getNextPacket();
                   if (!(proxy.buttonPressed()==BUTTON_0)) {
                       if (proxy.buttonPressed()==BUTTON_5) {
                         
                           Location location = new Location("screen", 1, proxy.getLastCursorLocation());
                           CompleteLocation tbl = null;
                              tbl = new CompleteLocation(location, proxy.getTimeStamp(), proxy.getPressure(),
                                    new Orientation(proxy.getRotation().roYaw, proxy.getRotation().roPitch,
                                          proxy.getRotation().roRoll));
                           tabletReader.fireInputDeviceEvent(new WacomReaderEvent(tbl));
                           
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

                   }
                  try {
                     Thread.sleep(1000 / FREQUENCE);
                  }
                  catch (InterruptedException e) {
                     LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
                  }  
             }
              
            }
         };
      t2.start(); 
   }
   

   public void callbackFunction(int x, int y, int z, int pkstatus, int npress, int tpress, long timeStamp,
         ORIENTATION orientation, ROTATION rotation, int button){
       
               if(button==BUTTON_5){
                  Location location = new Location("screen", 1, new Point(x,y));
                  CompleteLocation tbl = null;
                   tbl = new CompleteLocation(location, timeStamp, npress,
                         new Orientation(rotation.roYaw, rotation.roPitch,
                               rotation.roRoll));
                   tabletReader.fireInputDeviceEvent(new WacomReaderEvent(tbl));
                   lastKeyState = true;
                  
               }     else {
                  

                  if (lastKeyState) {
                     tabletReader.fireTabletButtonEvent(new InputDeviceEvent() {
                        public long getTimestamp() {
                           return System.currentTimeMillis();
                        }
                     });
                     
                     lastKeyState = false;
                  }
               }
               
      }


   /**
    * Creates a list of tablet readers. Only one tablet reader is created.
    * 
    * @return a list of tablet readers.
    */
   public static List<WacomReader> createTablet() {
      final List<WacomReader> list = new ArrayList<WacomReader>();
      list.add(new WacomReader());
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

