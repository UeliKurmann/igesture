/*
 * @(#)$Id$
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.wacomclient;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import org.sigtec.graphix.Orientation;
import org.sigtec.ink.input.CompleteLocation;
import org.sigtec.ink.input.Location;
import org.sigtec.input.AbstractInputDevice;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.util.Constant;
import org.ximtec.igesture.io.button.ButtonDeviceEventListener;
import org.ximtec.igesture.io.button.ButtonDeviceHandler;
import org.ximtec.igesture.io.wacom.WacomCallback;
import org.ximtec.igesture.io.wacom.WacomUtils;
import org.ximtec.igesture.io.wacom.Wintab32.ORIENTATION;
import org.ximtec.igesture.io.wacom.Wintab32.ROTATION;


/**
 * A wacom tablet PC reader.
 * 
 * @version 1.0, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 */

public class WacomReader extends AbstractInputDevice implements ButtonDeviceEventListener,
      WacomCallback {

   private static final Logger LOGGER = Logger.getLogger(WacomReader.class.getName());

   private boolean lastKeyState = false;
   private WacomUtils wacomUtils;

   private int BUTTON_0 = 0;
   private int BUTTON_5 = 5;

   private HashSet<ButtonDeviceHandler> buttonUpEvents;


   public WacomReader() {
      init();
      buttonUpEvents = new HashSet<ButtonDeviceHandler>();
   }


   /**
    * Initialises the tablet reader and starts a new thread polling the tablet.
    * 
    */
   public void init() {

      init_callback();
   } // init


   void init_callback() {
      // FIXME uk: refactoring
      wacomUtils = new WacomUtils(this);
      wacomUtils.start();
   }


   public void callbackFunction(int x, int y, int z, int pkstatus, int npress,
         int tpress, long timeStamp, ORIENTATION orientation, ROTATION rotation,
         int button) {

      if (button == BUTTON_5) {
         Location location = new Location("screen", 1, new Point(x, y));
         CompleteLocation tbl = null;
         tbl = new CompleteLocation(location, timeStamp, npress,
               Constant.NOT_AVAILABLE, new Orientation(rotation.roYaw,
                     rotation.roPitch, rotation.roRoll));
         WacomReader.this.fireInputDeviceEvent(new WacomReaderEvent(tbl));
         lastKeyState = true;

      }
      else {

         if (lastKeyState) {
            WacomReader.this.fireTabletButtonEvent(new InputDeviceEvent() {

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


   public void addButtonDeviceEventListener(ButtonDeviceHandler listener) {
      buttonUpEvents.add(listener);
   } // addButtonDeviceEventListener


   public void removeButtonDeviceEventListener(ButtonDeviceHandler listener) {
      buttonUpEvents.remove(listener);
   } // removeButtonDeviceEventListener


   private void fireTabletButtonEvent(InputDeviceEvent event) {
      for (final ButtonDeviceHandler listener : buttonUpEvents) {
         listener.handleButtonPressedEvent(event);
      }

   } // fireTabletButtonEvent

}
