/*
 * @(#)InputDeviceClient.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Bundles input devices. Used to capture notes.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.sigtec.ink.Note;
import org.sigtec.ink.TraceTool;
import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.BufferedInputDeviceEventListener;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.input.InputDeviceEventListener;
import org.sigtec.input.InputHandler;
import org.sigtec.input.util.CaptureTool;


/**
 * Bundles input devices. Used to capture notes.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class InputDeviceClient implements ButtonDeviceEventListener,
      InputDeviceEventListener, InputHandler, ButtonDevice {

   private InputDeviceEventListener listener = null;

   private InputDevice pen = null;

   private HashSet<InputDeviceEventListener> inputDeviceListeners;

   private HashSet<ButtonDeviceEventListener> buttonDeviceListeners;

   private HashSet<InputHandler> inputHandlerListeners;


   public InputDeviceClient(InputDevice pen, InputDeviceEventListener listener) {
      this.pen = pen;
      this.listener = listener;
      inputDeviceListeners = new HashSet<InputDeviceEventListener>();
      buttonDeviceListeners = new HashSet<ButtonDeviceEventListener>();
      inputHandlerListeners = new HashSet<InputHandler>();
      init();
   }


   /**
    * Initialises the input device client.
    */
   public void init() {
      listener.addInputHandler(this);
      pen.addInputDeviceEventListener(listener);
      pen.addInputDeviceEventListener(this);

      if (pen instanceof ButtonDevice) {
         ((ButtonDevice)pen).addButtonDeviceEventListener(this);
      }

      if (pen instanceof InputDeviceEventListener) {
         ((InputDeviceEventListener)pen).addInputHandler(this);
      }

   } // init


   /**
    * Clears the input device's buffer.
    * 
    */
   public void clearBuffer() {
      ((BufferedInputDeviceEventListener)listener).reset();
   } // clearBuffer


   /**
    * Returns a list of timestamped locations in the interval start - end.
    * 
    * @param start the start time.
    * @param end the end time.
    * @return list of timestamped locations.
    */
   public Collection<TimestampedLocation> getTimestampedLocations(long start,
         long end) {
      return ((BufferedInputDeviceEventListener)listener).getLocations(start,
            end);
   } // getTimestampedLocations


   /**
    * Creates a note from the points in the buffer.
    * 
    * @param start the start time.
    * @param end the end time.
    * @param maxTime the maximal time between two points.
    * @return the note created fom the device buffer.
    */
   public Note createNote(long start, long end, int maxTime) {
      final Collection<TimestampedLocation> locations = getTimestampedLocations(
            start, end);
      clearBuffer();
      final Note note = new Note(TraceTool.detectTraces(CaptureTool
            .toPoints(new ArrayList<TimestampedLocation>(locations)), maxTime));
      return note;
   } // createNote


   public void handleButtonPressedEvent(InputDeviceEvent event) {
      for (final ButtonDeviceEventListener listener : buttonDeviceListeners) {
         listener.handleButtonPressedEvent(event);
      }

   } // handleButtonPressedEvent


   public void addInputDeviceEventListener(InputDeviceEventListener listener) {
      inputDeviceListeners.add(listener);
   } // addInputDeviceEventListener


   public void removeInputDeviceEventListener(InputDeviceEventListener listener) {
      inputDeviceListeners.remove(listener);
   } // removeInputDeviceEventListener


   public void addButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonDeviceListeners.add(listener);
   } // addButtonDeviceEventListener


   public void removeButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonDeviceListeners.remove(listener);
   } // removeButtonDeviceEventListener


   public void handle(Object invoker, TimestampedLocation location) {
      for (final InputHandler listener : inputHandlerListeners) {
         listener.handle(invoker, location);
      }
      
   } // handle


   public void addInputHandler(InputHandler handler) {
      inputHandlerListeners.add(handler);
   } // addInputHandler


   public void inputDeviceEvent(InputDevice inputDevice, InputDeviceEvent event) {
      for (final InputDeviceEventListener listener : inputDeviceListeners) {
         listener.inputDeviceEvent(inputDevice, event);
      }
      
   } // inputDeviceEvent


   public void removeInputHandler(InputHandler handler) {
      inputHandlerListeners.remove(handler);
   } // removeInputHandler
   
}
