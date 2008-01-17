/*
 * @(#)MouseReader.java    1.0   Dec 10, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   A mouse reader.
 *                  
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 10, 2006		ukurmann	Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 * Jan 17, 2008     crocimi     added dependency from MouseCallback
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

import org.sigtec.ink.input.Location;
import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.util.Constant;
import org.ximtec.igesture.io.mouse.MouseCallback;
import org.ximtec.igesture.io.mouse.MouseUtils;


/**
 * A mouse reader.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class MouseReader extends ExtendedInputDevice implements
      ButtonDevice, MouseCallback {

   private static final Logger LOGGER = Logger.getLogger(MouseReader.class
         .getName());

   private static final int FREQUENCE = 20;

   private boolean lastKeyState = false;


   private HashSet<ButtonDeviceEventListener> buttonUpEvents;
   
   final MouseReader mouseReader = this;
   
   //final MouseUtils mouseUtils = new MouseUtils();


   public MouseReader() {
      init();
      buttonUpEvents = new HashSet<ButtonDeviceEventListener>();
   }


   /**
    * Initialises the mouse reader and starts a new thread polling the mouse.
    * 
    */
   public void init() {

      final Thread t = new Thread() {

         @Override
         public void run() {
            while (!stop) {

               if (Win32MouseProxy.isMiddleButtonPressed()) {
                  Location location = new Location("screen", 1, Win32MouseProxy
                        .getCursorLocation());
                  TimestampedLocation tsl = new TimestampedLocation(location,
                        System.currentTimeMillis());
                  mouseReader.fireInputDeviceEvent(new MouseReaderEvent(tsl));
                  lastKeyState = true;
               }
               else {

                  if (lastKeyState) {
                     mouseReader.fireMouseButtonEvent(new InputDeviceEvent() {

                        public long getTimestamp() {
                           return System.currentTimeMillis();
                        }
                     });
                     lastKeyState = false;
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

      t.start();
   } // init
   



   /**
    * Creates a list of mouse readers. Only one mouse reader is created.
    * 
    * @return a list of mouse readers.
    */
   public static List<MouseReader> createMouse() {
      final List<MouseReader> list = new ArrayList<MouseReader>();
      list.add(new MouseReader());
      return list;
   } // createMouse


   public void addButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonUpEvents.add(listener);
   } // addButtonDeviceEventListener


   public void removeButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonUpEvents.remove(listener);
   } // removeButtonDeviceEventListener


   private void fireMouseButtonEvent(InputDeviceEvent event) {
      for (final ButtonDeviceEventListener listener : buttonUpEvents) {
         listener.handleButtonPressedEvent(event);
      }

   } // fireMouseButtonEvent
   


   public void callbackFunction(int x, int y, int button){

      if (button ==4) {
         Location location = new Location("screen", 1, new Point(x,y));
         TimestampedLocation tsl = new TimestampedLocation(location,
               System.currentTimeMillis());
         mouseReader.fireInputDeviceEvent(new MouseReaderEvent(tsl));
         lastKeyState = true;
      }
      else {

         if (lastKeyState) {
            mouseReader.fireMouseButtonEvent(new InputDeviceEvent() {

               public long getTimestamp() {
                  return System.currentTimeMillis();
               }
            });
            lastKeyState = false;
         }
      }
      
   }
   /* 
   public void disableCallback(){
      stop=true;
      mouseUtils.disableCallback();
      
   }
*/
}