/*
 * @(#)HelloWorldXML.java   1.0   Nov 15, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.app.helloworld;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.input.BufferedInputDeviceEventListener;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.input.InputDeviceEventListener;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.io.ButtonDeviceEventListener;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.io.MouseReader;
import org.ximtec.igesture.io.MouseReaderEventListener;
import org.ximtec.igesture.util.XMLTool;


/**
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class HelloWorldXML implements ButtonDeviceEventListener {

   private static final Logger LOGGER = Logger.getLogger(HelloWorldXML.class
         .getName());

   private static final String INITIALISED = "Initialised.";

   private static final String NOT_RECOGNISED = "Not recognised.";

   private Recogniser recogniser;

   private InputDeviceClient client;


   public HelloWorldXML() throws AlgorithmException {
      Configuration configuration = XMLTool.importConfiguration(ClassLoader
            .getSystemResourceAsStream("configuration.xml"));
      recogniser = new Recogniser(configuration);
      InputDevice device = new MouseReader();
      InputDeviceEventListener listener = new BufferedInputDeviceEventListener(
            new MouseReaderEventListener(), 10000);
      client = new InputDeviceClient(device, listener);
      client.addButtonDeviceEventListener(this);
      LOGGER.log(Level.INFO, INITIALISED);
   }


   public static void main(String[] args) throws AlgorithmException {
      new HelloWorldXML();
   }


   public void handleButtonPressedEvent(InputDeviceEvent event) {
      ResultSet result = recogniser.recognise(client.createNote());
      client.clearBuffer();

      if (result.isEmpty()) {
         LOGGER.log(Level.INFO, NOT_RECOGNISED);
      }
      else {
         LOGGER.log(Level.INFO, result.getResult().getGestureClassName());
      }

   } // handleButtonPressedEvent

}
