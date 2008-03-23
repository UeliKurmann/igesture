/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.service;

import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEventListener;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.tool.locator.Service;

/**
 * Service Wrapper for InputDeviceClient
 *
 * @author  UeliKurmann
 * @version 1.0
 * @since   igesture
 */
public class InputDeviceClientService extends InputDeviceClient implements
      Service {

   public static final String IDENTIFIER = "inputDeviceClientService";


   public InputDeviceClientService(InputDevice inputDevice,
         InputDeviceEventListener listener) {
      super(inputDevice, listener);
   }


   @Override
   public String getIdentifier() {
      return IDENTIFIER;
   }


   @Override
   public void start() {
      init();
   }


   @Override
   public void stop() {
   }

}
