

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


public class HelloWorldXML implements ButtonDeviceEventListener {

   private static final Logger LOGGER = Logger.getLogger(HelloWorldXML.class
         .getName());

   private static final String INITIALISED = "Initialised...";

   private static final String NOT_RECOGNISED = "Not recognised...";

   private Recogniser recogniser;

   private InputDeviceClient client;


   public HelloWorldXML() throws AlgorithmException {

      Configuration configuration = XMLTool.importConfiguration(new File(this
            .getClass().getClassLoader().getResource("configuration.xml")
            .getFile()));

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
      ResultSet result = recogniser.recognise(client.createNote(0, event
            .getTimestamp(), 70));
      client.clearBuffer();
      if (result.isEmpty()) {
         LOGGER.log(Level.INFO, NOT_RECOGNISED);
      }
      else {
         LOGGER.log(Level.INFO, result.getResult().getGestureClassName());
      }
   }
}
