/*
 * @(#)ExtensionFileFilter.java 1.0   Nov 15, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Main class for the mapping gesture application
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2007     crocimi     Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.geco;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.graphix.GuiTool;
import org.sigtec.ink.Note;
import org.sigtec.input.BufferedInputDeviceEventListener;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.input.InputDeviceEventListener;
import org.sigtec.util.Constant;
import org.sigtec.util.SystemProperty;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.EventManager;
import org.ximtec.igesture.geco.GUI.GestureMappingModel;
import org.ximtec.igesture.geco.GUI.GestureMappingView;
import org.ximtec.igesture.io.ButtonDeviceEventListener;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.io.MouseReader;
import org.ximtec.igesture.io.MouseReaderEventListener;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.GestureConfiguration;
import org.ximtec.igesture.util.XMLTool;



/**
 *  Main class for the mapping gesture application
 * 
 * @version 1.0 Nov 15, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class GestureMappingMain implements ButtonDeviceEventListener {
   
   private static final Logger LOGGER = Logger.getLogger(GestureMappingMain.class
         .getName());

   private static final String DEFAULT_CONFIGURATION = "config.xml";
   
   private static final String GUI_BUNDLE_FILE = "gecoProperties";


	/**
	 * @param args
	 */
	public static void main(String[] args) {
	   try{
       new GestureMappingMain(args);
	   }
       catch(Exception e){
          e.printStackTrace();
       }

	}

	   private static final String INITIALISING = "Initialising...";

	   private static final String INITIALISED = "Initialised.";

	   //private static final String MAPPING_FILE = "gestureMapping.xml";

	   private static final String RUBINE_CONFIGURATION = "rubineconfiguration.xml";

	   private static final String GESTURE_SET = "gestureSets/ms_application_gestures.xml";

	   private Recogniser recogniser;

	   private InputDeviceClient client;
	   
	   private GestureMappingView view;

	   //private EventManager eventManager;


	   public GestureMappingMain(String[] args) throws AlgorithmException {
	          GuiTool.addGuiBundle(GUI_BUNDLE_FILE);
	          Logger.getAnonymousLogger().setLevel(Level.ALL);
	          LOGGER.info(INITIALISING);
	          
//	          initEventManager();

	          
	          GestureConfiguration configuration;
	          
	          if (args.length > 0) {
	             configuration = new GestureConfiguration(args[0]);

	          }
	          else {
	             configuration = new GestureConfiguration(DEFAULT_CONFIGURATION);
	          }
	          
	          


	          String file = System.getProperty(SystemProperty.USER_DIR) + Constant.SLASH
	                + configuration.getDatabase();
	                
	          //StorageEngine engine = StorageManager.createStorageEngine(new File(file));
	          StorageEngine engine = null;
	          GestureMappingModel model = new GestureMappingModel(engine);
	          view = new GestureMappingView(model);
	          
	          ///////////////
	          
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
	      Configuration configuration = XMLTool.importConfiguration(new File(
	            ClassLoader.getSystemResource(RUBINE_CONFIGURATION).getFile()));
	      GestureSet gestureSet = XMLTool.importGestureSet(
	            new File(ClassLoader.getSystemResource(GESTURE_SET).getFile())).get(0);
	      configuration.addGestureSet(gestureSet);
	      configuration.setEventManager(view.getModel().getEventManager());
	      recogniser = new Recogniser(configuration);
	   } // initRecogniser

/*
	   private void initEventManager() {
	      eventManager = new EventManager();

	      for (GestureKeyMapping mapping : XMLImport.importKeyMappings(new File(
	            GestureKeyboard.class.getClassLoader().getResource(MAPPING_FILE)
	                  .getFile()))) {
	         LOGGER.info(mapping.toString());
	         eventManager.registerEventHandler(mapping.getGestureName(),
	               new PressKeystroke(mapping.getKeys()));
	      }

	   } // initEventManager
*/


	   public void handleButtonPressedEvent(InputDeviceEvent event) {
	      Note note = client.createNote(0, event.getTimestamp(), 70);

	      if (note.getPoints().size() > 5) {
	         recogniser.recognise(note);
	      }

	   } // handleButtonPressedEvent

}
