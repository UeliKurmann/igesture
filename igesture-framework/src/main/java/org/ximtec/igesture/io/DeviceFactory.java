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
 * 19.06.2008			ukurmann	Initial Release
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

import org.sigtec.input.BufferedInputDeviceEventListener;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEventListener;
 

/**
 * Comment
 * @version 1.0 19.06.2008
 * @author Ueli Kurmann
 */
public class DeviceFactory {
     

   public static InputDeviceClient newMouseDevice(){
      InputDevice device = new MouseReader();
      InputDeviceEventListener listener = new BufferedInputDeviceEventListener(
            new MouseReaderEventListener(), 10000);
      InputDeviceClient client = new InputDeviceClient(device, listener);
      return client;
   }
    
   public static BlockingDeviceClient newBlockingMouseDevice(){
      return new BlockingDeviceClient(newMouseDevice());
   }

}
