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

import org.ximtec.igesture.io.mouseclient.SwingMouseReader;
import org.ximtec.igesture.tool.locator.RunnableService;


/**
 * Service Wrapper for InputDeviceClient
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public class InputDeviceClientService extends SwingMouseReader implements
      RunnableService {

   public static final String IDENTIFIER = "inputDeviceClientService";


   public InputDeviceClientService() {
      super();
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
      dispose();
   }


   @Override
   public void reset() {
      // TODO Auto-generated method stub
      
   }

}