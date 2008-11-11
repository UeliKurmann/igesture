/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;

import java.util.HashSet;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;
import org.sigtec.ink.input.TimestampedInputEvent;
import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.AbstractInputDevice;
import org.sigtec.input.BufferedInputDeviceEventListener;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.input.InputDeviceEventListener;
import org.sigtec.input.InputHandler;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;


/**
 * Bundles input devices. Used to capture notes.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class InputDeviceClient implements ButtonDeviceEventListener,
      InputDeviceEventListener, InputHandler, ButtonDevice {

   private InputDeviceEventListener listener = null;

   private InputDevice inputDevice = null;

   private HashSet<InputDeviceEventListener> inputDeviceListeners;

   private HashSet<ButtonDeviceEventListener> buttonDeviceListeners;

   private HashSet<InputHandler> inputHandlerListeners;


   public InputDeviceClient(InputDevice inputDevice,
         InputDeviceEventListener listener) {
      this.inputDevice = inputDevice;
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
      inputDevice.addInputDeviceEventListener(listener);
      inputDevice.addInputDeviceEventListener(this);

      if (inputDevice instanceof ButtonDevice) {
         ((ButtonDevice)inputDevice).addButtonDeviceEventListener(this);
      }

      if (inputDevice instanceof InputDeviceEventListener) {
         ((InputDeviceEventListener)inputDevice).addInputHandler(this);
      }

   } // init


   public InputDevice getInputDevice() {
      clearBuffer();
      return inputDevice;
   }


   /**
    * Reset the input device client.
    */
   public void reset() {
      listener.removeInputHandler(this);
      inputDevice.removeInputDeviceEventListener(listener);
      inputDevice.removeInputDeviceEventListener(this);

      if (inputDevice instanceof ButtonDevice) {
         ((ButtonDevice)inputDevice).removeButtonDeviceEventListener(this);
      }

      if (inputDevice instanceof InputDeviceEventListener) {
         ((InputDeviceEventListener)inputDevice).removeInputHandler(this);
      }

      if (inputDevice instanceof AbstractInputDevice) {
         ((ExtendedInputDevice)inputDevice).stopLoop();
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
    * Creates a note from the points in the buffer.
    * 
    * @param start the start time.
    * @param end the end time.
    * @param maxTime the maximal time between two points.
    * @return the note created from the device buffer.
    */
   public Note createNote() {
      List<List<TimestampedInputEvent>> strokes = ((BufferedInputDeviceEventListener)listener)
            .getStrokes();
      Note note = new Note();
      for (List<TimestampedInputEvent> stroke : strokes) {
         Trace trace = new Trace();
         for (TimestampedInputEvent event : stroke) {
            if (event instanceof TimestampedLocation) {
               TimestampedLocation location = (TimestampedLocation)event;
               Point point = new Point(location.getPosition().getX(), location
                     .getPosition().getY(), location.getTimestamp());
               trace.add(point);
            }
         }
         if (trace.size() > 0) {
            note.add(trace);
         }
      }

      clearBuffer();
      return note;
   } // createNote


   public synchronized Gesture<Note> getGesture() {
      Gesture<Note> gesture = new GestureSample(Constant.EMPTY_STRING,
            createNote());
      return gesture;
   }


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


   @Override
   public void handle(Object invoker, TimestampedInputEvent timestampedEvent) {
      if (timestampedEvent instanceof TimestampedLocation) {
         TimestampedLocation location = (TimestampedLocation)timestampedEvent;

         for (final InputHandler listener : inputHandlerListeners) {
            listener.handle(invoker, location);
         }

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
