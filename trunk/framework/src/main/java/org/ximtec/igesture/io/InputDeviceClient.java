/*
 * @(#)InputDeviceClient.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Bundles input devices. used to capture notes
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
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
import org.ximtec.ipaper.graphics2D.util.CapturingTool;
import org.ximtec.ipaper.input.InputHandler;
import org.ximtec.ipaper.io.BufferedInputDeviceEventListener;
import org.ximtec.ipaper.io.InputDevice;
import org.ximtec.ipaper.io.InputDeviceEvent;
import org.ximtec.ipaper.io.InputDeviceEventListener;
import org.ximtec.ipaper.util.TimestampedLocation;


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
    * Initialises the InputDeviceClient
    * 
    */
   public void init() {
      listener.addInputHandler(this);
	  pen.addInputDeviceEventListener(listener);
	   
	  
      pen.addInputDeviceEventListener(this);
      
      if (pen instanceof ButtonDevice) {
         ((ButtonDevice) pen).addButtonDeviceEventListener(this);
      }

      if (pen instanceof InputDeviceEventListener) {
         ((InputDeviceEventListener) pen).addInputHandler(this);
      }
   }


   /**
    * clears the buffer of the input device
    * 
    */
   public void clearBuffer() {
      ((BufferedInputDeviceEventListener) listener).reset();
   }


   /**
    * returns a list of timestamped locations in the interval start - end
    * 
    * @param start starttime
    * @param end endtime
    * @return list of timestamped locations
    */
   public Collection<TimestampedLocation> getTimestampedLocations(long start,
         long end) {
      return ((BufferedInputDeviceEventListener) listener).getLocations(start,
            end);
   }


   /**
    * Creates a Note from the points in the buffer
    * 
    * @param start
    * @param end
    * @param maxTime
    * @return
    */
   public Note createNote(long start, long end, int maxTime) {
      final Collection<TimestampedLocation> locations = getTimestampedLocations(
            start, end);
      clearBuffer();

      final Note note = new Note(TraceTool.detectTraces(CapturingTool
            .toPoints(new ArrayList<TimestampedLocation>(locations)), maxTime));

      return note;
   }


   public void handleButtonPressedEvent(InputDeviceEvent event) {
      for (final ButtonDeviceEventListener listener : buttonDeviceListeners) {
         listener.handleButtonPressedEvent(event);
      }
   }


   public void addInputDeviceEventListener(InputDeviceEventListener listener) {
      inputDeviceListeners.add(listener);
   }


   public void removeInputDeviceEventListener(InputDeviceEventListener listener) {
      inputDeviceListeners.remove(listener);
   }


   public void addButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonDeviceListeners.add(listener);
   }


   public void removeButtonDeviceEventListener(ButtonDeviceEventListener listener) {
      buttonDeviceListeners.remove(listener);

   }


   public void handle(Object invoker, TimestampedLocation location) {
      for (final InputHandler listener : inputHandlerListeners) {
         listener.handle(invoker, location);
      }
   }


   public void addInputHandler(InputHandler handler) {
      inputHandlerListeners.add(handler);
   }


   public void inputDeviceEvent(InputDevice inputDevice, InputDeviceEvent event) {
      for (final InputDeviceEventListener listener : inputDeviceListeners) {
         listener.inputDeviceEvent(inputDevice, event);
      }
   }


   public void removeInputHandler(InputHandler handler) {
      inputHandlerListeners.remove(handler);
   }
}
