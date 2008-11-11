/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 20.06.2008			ukurmann	Initial Release
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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.sigtec.ink.Note;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;


/**
 * Comment
 * @version 1.0 20.06.2008
 * @author Ueli Kurmann
 */
public class BlockingDeviceClient implements ButtonDeviceEventListener {

   private InputDeviceClient client;

   private BlockingQueue<Gesture<Note>> gestureQueue;


   public BlockingDeviceClient(InputDeviceClient client) {
      gestureQueue = new LinkedBlockingQueue<Gesture<Note>>();
      this.client = client;
      this.client.addButtonDeviceEventListener(this);
   }


   public Gesture<Note> getGesture() {
      try {
         return gestureQueue.take();
      }
      catch (InterruptedException e) {
         return new GestureSample(Constant.EMPTY_STRING, new Note());
      }
   }


   @Override
   public void handleButtonPressedEvent(InputDeviceEvent event) {
      Gesture<Note> gesture = client.getGesture();
      try {
         gestureQueue.put(gesture);
      }
      catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
}
