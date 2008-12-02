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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.GestureEventListener;
import org.ximtec.igesture.io.mouseclient.MouseReader;
import org.ximtec.igesture.util.XMLTool;


/**
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class HelloWorldXML implements GestureEventListener {

   private static final Logger LOGGER = Logger.getLogger(HelloWorldXML.class
         .getName());

   private static final String INITIALISED = "Initialised.";

   private static final String NOT_RECOGNISED = "Not recognised.";

   private Recogniser recogniser;

   private GestureDevice<Note, Point> client;


   public HelloWorldXML() throws AlgorithmException {
      Configuration configuration = XMLTool.importConfiguration(ClassLoader
            .getSystemResourceAsStream("configuration.xml"));
      recogniser = new Recogniser(configuration);

      client = new MouseReader();
      client.init();
      client.addGestureHandler(this);

      LOGGER.log(Level.INFO, INITIALISED);
   }

   public static void main(String[] args) throws AlgorithmException {
      new HelloWorldXML();
   }


   @Override
   public void handleChunks(List< ? > chunks) {
      // TODO Auto-generated method stub

   }


   @Override
   public void handleGesture(Gesture< ? > gesture) {
      if (gesture.getGesture() instanceof Note) {
         Note note = (Note)gesture.getGesture();
         ResultSet result = recogniser.recognise(note);

         if (result.isEmpty()) {
            LOGGER.log(Level.INFO, NOT_RECOGNISED);
         }
         else {
            LOGGER.log(Level.INFO, result.getResult().getGestureClassName());
         }
      }
   }

}
