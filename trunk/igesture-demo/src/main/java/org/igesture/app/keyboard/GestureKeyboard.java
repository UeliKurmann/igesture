/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.igesture.app.keyboard;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.GestureActionManager;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.GestureEventListener;
import org.ximtec.igesture.io.mouseclient.MouseReader;
import org.ximtec.igesture.util.XMLTool;


/**
 * @version 1.0 Mar 2007
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureKeyboard implements GestureEventListener {

   private static final Logger LOGGER = Logger.getLogger(GestureKeyboard.class
         .getName());

   private static final String INITIALISING = "Initialising...";

   private static final String INITIALISED = "Initialised.";

   private static final String MAPPING_FILE = "gestureMapping.xml";

   private static final String RUBINE_CONFIGURATION = "rubineconfiguration.xml";

   private static final String GESTURE_SET = "gestureSet/msApplicationGestures.xml";

   private Recogniser recogniser;

   private GestureDevice<Note, Point> gestureDevice;

   private GestureActionManager eventManager;


   public GestureKeyboard() throws AlgorithmException {
      LOGGER.info(INITIALISING);
      initEventManager();
      initRecogniser();
      initDevice();
      LOGGER.info(INITIALISED);
   }


   private void initDevice() {
      gestureDevice = new MouseReader();
      gestureDevice.init();
      gestureDevice.addGestureHandler(this);

   } // initDevice


   private void initRecogniser() throws AlgorithmException {
      Configuration configuration = XMLTool.importConfiguration(ClassLoader
            .getSystemResourceAsStream(RUBINE_CONFIGURATION));
      GestureSet gestureSet = XMLTool.importGestureSet(ClassLoader
            .getSystemResourceAsStream(GESTURE_SET));
      configuration.addGestureSet(gestureSet);
      
      recogniser = new Recogniser(configuration);
      recogniser.addGestureHandler(eventManager);
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


   @Override
   public void handleChunks(List< ? > chunks) {
      LOGGER.info("Do nothing...");

   }


   @Override
   public void handleGesture(Gesture< ? > gesture) {

      LOGGER.info("Recoginse gesture...");
      if (gesture.getGesture() instanceof Note) {
         Note note = (Note)gesture.getGesture();

         if (note.getPoints().size() > 5) {
            recogniser.recognise(note);
         }
      }

   }

}
