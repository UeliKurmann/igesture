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
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.EventManager;
import org.ximtec.igesture.geco.GUI.GecoMainModel;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.xml.XMLGeco;
import org.ximtec.igesture.io.ButtonDeviceEventListener;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.io.MouseReader;
import org.ximtec.igesture.io.MouseReaderEventListener;
import org.ximtec.igesture.storage.StorageEngine;




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
   
   //FIXME: ddd
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
       
       GecoMainView view;



       private EventManager eventManager;


       public GestureMappingMain(String[] args) throws AlgorithmException {
              GuiTool.addGuiBundle(GUI_BUNDLE_FILE);
              Logger.getAnonymousLogger().setLevel(Level.ALL);
              LOGGER.info(INITIALISING);
              
              /*
              GestureConfiguration configuration;
              
              if (args.length > 0) {
                 configuration = new GestureConfiguration(args[0]);

              }
              else {
                 configuration = new GestureConfiguration(DEFAULT_CONFIGURATION);
              }
              
              


              String file = System.getProperty(SystemProperty.USER_DIR) + Constant.SLASH
                    + configuration.getDatabase();
                    
              StorageEngine engine = StorageManager.createStorageEngine(new File(file));
              */
              StorageEngine engine = null;
              GecoMainModel model = new GecoMainModel(engine);
              view = new GecoMainView(model);
              
//            initEventManager();
              initRecogniser();
              initDevice();
              



              
              ///////////////



          
          

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
          Configuration configuration = XMLGeco.importConfiguration(new File(
                ClassLoader.getSystemResource(RUBINE_CONFIGURATION).getFile()));
          GestureSet gestureSet = XMLGeco.importGestureSet(
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
