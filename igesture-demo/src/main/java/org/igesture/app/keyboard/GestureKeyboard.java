/*
 * @(#)GestureKeyboard.java   1.0   March 09, 2007
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
 * Mar 09, 2007     ukurmann    Initial Release
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


package org.igesture.app.keyboard;

import java.io.File;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.sigtec.input.BufferedInputDeviceEventListener;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.input.InputDeviceEventListener;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.GestureActionManager;
import org.ximtec.igesture.io.ButtonDeviceEventListener;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.io.MouseReader;
import org.ximtec.igesture.io.MouseReaderEventListener;
import org.ximtec.igesture.util.XMLTool;


/**
 * @version 1.0 Mar 2007
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureKeyboard implements ButtonDeviceEventListener {

   private static final Logger LOGGER = Logger.getLogger(GestureKeyboard.class
         .getName());

   private static final String INITIALISING = "Initialising...";

   private static final String INITIALISED = "Initialised.";

   private static final String MAPPING_FILE = "gestureMapping.xml";

   private static final String RUBINE_CONFIGURATION = "rubineconfiguration.xml";

   private static final String GESTURE_SET = "gestureSets/ms_application_gestures.xml";

   private Recogniser recogniser;

   private InputDeviceClient client;

   private GestureActionManager eventManager;


   public GestureKeyboard() throws AlgorithmException {
      LOGGER.info(INITIALISING);
      initEventManager();
      initRecogniser();
      initDevice();
      LOGGER.info(INITIALISED);
   }


   private void initDevice() {
      InputDevice device = new MouseReader();
      InputDeviceEventListener listener = new BufferedInputDeviceEventListener(
            new MouseReaderEventListener(), 10000);
      client = new InputDeviceClient(device, listener);
      client.addButtonDeviceEventListener(this);
   } // initDevice


   private void initRecogniser() throws AlgorithmException {
      Configuration configuration = XMLTool.importConfiguration(ClassLoader
            .getSystemResourceAsStream(RUBINE_CONFIGURATION));
      GestureSet gestureSet = XMLTool.importGestureSet(
            ClassLoader.getSystemResourceAsStream(GESTURE_SET)).get(0);
      configuration.addGestureSet(gestureSet);
      configuration.setGestureHandler(eventManager);
      recogniser = new Recogniser(configuration);
   } // initRecogniser


   private void initEventManager() {
      eventManager = new GestureActionManager();

      for (GestureKeyMapping mapping : XMLImport.importKeyMappings(new File(
            GestureKeyboard.class.getClassLoader().getResource(MAPPING_FILE)
                  .getFile()))) {
         LOGGER.info(mapping.toString());
         eventManager.registerEventHandler(mapping.getGestureName(),
               new PressKeystroke(mapping.getKeys()));
      }

   } // initEventManager


   public static void main(String[] args) throws AlgorithmException {
      new GestureKeyboard();
   }


   public void handleButtonPressedEvent(InputDeviceEvent event) {
      Note note = client.createNote();

      if (note.getPoints().size() > 5) {
         recogniser.recognise(note);
      }

   } // handleButtonPressedEvent

}
