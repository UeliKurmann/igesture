/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
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
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.ink.input.Location;
import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.AbstractInputDevice;
import org.sigtec.input.InputDeviceEvent;
import org.ximtec.igesture.io.mouse.MouseEventListener;
import org.ximtec.igesture.io.mouse.MouseUtils;
import org.ximtec.igesture.io.mouse.MouseUtils.MouseButton;


/**
 * A mouse reader.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class MouseReader extends AbstractInputDevice implements ButtonDevice,
      MouseEventListener {

   private static final Logger LOGGER = Logger.getLogger(MouseReader.class
         .getName());

   private boolean lastKeyState = false;

   private Set<ButtonDeviceEventListener> buttonUpEvents;

   private MouseUtils mouseUtils;


   public MouseReader() {
      buttonUpEvents = new HashSet<ButtonDeviceEventListener>();

      mouseUtils = new MouseUtils(this);
      Executors.newSingleThreadExecutor().execute(mouseUtils);
   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.io.mouse.MouseCallback#callbackFunction(int, int,
    *      boolean)
    */
   @Override
   public void mouseEvent(int x, int y, EnumSet<MouseButton> buttons) {

      

      if (buttons.contains(MouseButton.MIDDLE)) {
         LOGGER.log(Level.FINEST, "Mouse Event...");
         Location location = new Location("screen", 1, new Point(x, y));
         TimestampedLocation tsl = new TimestampedLocation(location, System
               .currentTimeMillis());
         this.fireInputDeviceEvent(new MouseReaderEvent(tsl));
         lastKeyState = true;
      }
      else {

         if (lastKeyState) {
            this.fireMouseButtonEvent(new InputDeviceEvent() {

               public long getTimestamp() {
                  return System.currentTimeMillis();
               }
            });
            lastKeyState = false;
         }
      }
   }


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


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.io.ButtonDevice#addButtonDeviceEventListener(org.ximtec.igesture.io.ButtonDeviceEventListener)
    */
   @Override
   public void addButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonUpEvents.add(listener);
   } // addButtonDeviceEventListener


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.io.ButtonDevice#removeButtonDeviceEventListener(org.ximtec.igesture.io.ButtonDeviceEventListener)
    */
   @Override
   public void removeButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonUpEvents.remove(listener);
   } // removeButtonDeviceEventListener


   /**
    * Fires a mouse button event
    * @param event
    */
   private void fireMouseButtonEvent(InputDeviceEvent event) {
      for (final ButtonDeviceEventListener listener : buttonUpEvents) {
         listener.handleButtonPressedEvent(event);
      }

   } // fireMouseButtonEvent


   @Override
   public void mouseButtonPressed(EnumSet<MouseButton> buttons) {
      // TODO Auto-generated method stub

   }


   @Override
   public void mouseMoved(int x, int y) {
      // TODO Auto-generated method stub

   }

}