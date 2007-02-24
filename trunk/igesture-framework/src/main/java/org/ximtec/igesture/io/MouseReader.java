/*
 * @(#)MouseReader.java    1.0   May 04, 2004
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Mouse Reader
 *                  
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dez. 10.2006		uk			initial release
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

import org.sigtec.ink.input.Location;
import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.InputDeviceEvent;




public class MouseReader extends org.sigtec.input.AbstractInputDevice implements ButtonDevice {

   private static final int FREQUENCE = 20;

   private boolean lastKeyState = false;

   private HashSet<ButtonDeviceEventListener> buttonUpEvents;


   public MouseReader() {
      init();
      buttonUpEvents = new HashSet<ButtonDeviceEventListener>();
   }


   /**
    * Initialises the mouse reader and starts a new thread polling the mouse
    * 
    */
   public void init() {
      final MouseReader mouseReader = this;

      final Thread t = new Thread() {

         @Override
         public void run() {
            while (true) {

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
                  e.printStackTrace();
               }
            }
         }
      };
      t.start();
   }


   /**
    * Creates a list of mouse reader. only one mouse reader is created.
    * 
    * @return a list of mouse reader
    */
   public static List<MouseReader> createMouse() {
      final List<MouseReader> list = new ArrayList<MouseReader>();
      list.add(new MouseReader());
      return list;
   }


   public void addButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonUpEvents.add(listener);
   }


   public void removeButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonUpEvents.remove(listener);
   }


   private void fireMouseButtonEvent(InputDeviceEvent event) {
      for (final ButtonDeviceEventListener listener : buttonUpEvents) {
         listener.handleButtonPressedEvent(event);
      }
   }
}