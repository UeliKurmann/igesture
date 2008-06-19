/*
 * @(#)$Id:$
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

import org.sigtec.ink.Note;
import org.sigtec.input.InputDeviceEvent;
import org.ximtec.igesture.core.Gesture;


/**
 * Comment
 * @version 1.0 20.06.2008
 * @author Ueli Kurmann
 */
public class BlockingDeviceClient implements ButtonDeviceEventListener {

   InputDeviceClient client;

   volatile boolean wait;


   public BlockingDeviceClient(InputDeviceClient client) {
      this.client = client;
      this.client.addButtonDeviceEventListener(this);
      this.wait = true;
   }


   public Gesture<Note> getGesture() {
      Gesture<Note> gesture;

      while (wait) {
         try {
            Thread.sleep(20);
         }
         catch (InterruptedException e) {
            e.printStackTrace();
         }
      }

      gesture = client.getGesture();
      client.clearBuffer();
      wait = true;

      return gesture;
   }


   @Override
   public void handleButtonPressedEvent(InputDeviceEvent event) {
      wait = false;
   }

}
