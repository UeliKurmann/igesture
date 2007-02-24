

package org.ximtec.igesture.io;

import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.sigtec.exception.NotImplementedException;
import org.sigtec.input.BufferedInputDeviceEventListener;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEventListener;


public class InputDeviceFactory {

   public static final String MAGICOM_PEN_SOCKET = "MagicommPenSocket";

   public static final String MOUSE = "Mouse";

   private static final String MAPPING_FILE = "mappingFile";

   public static final int DEFAULT_BUFFER_SIZE = 10000;

   public static final String PORT = "port";

   public static final String COM = "com";


   /**
    * Creates a new InputDevice based on the identifier
    * @param identifier the identifier of the input device
    * @param configuration the configuration object holding parameters
    * @return the input device
    */
   public static InputDevice createInputDevice(String identifier,
         XMLConfiguration configuration) {
      if (identifier.equals(MOUSE)) {
         return createMouseInputDevice();
      }
      else if (identifier.equals(MAGICOM_PEN_SOCKET)) {
         return createMagicommPenSocketInputDevice(configuration);
      }
      return null;
   }


   /**
    * Creates a new InputDeviceEventListener
    * @param identifier the identifier of the input device
    * @param configuration the configuration object holding parameters
    * @return
    */
   public static InputDeviceEventListener createInputDeviceEventListener(
         String identifier, XMLConfiguration configuration) {
      if (identifier.equals(MOUSE)) {
         return getMouseListener();
      }
      else if (identifier.equals(MAGICOM_PEN_SOCKET)) {
         return getMagicommListener(configuration);
      }
      return null;
   }


   /**
    * Creates a new Mouse device
    * 
    * @return a new Mouse device
    */
   private static MouseReader createMouseInputDevice() {
      return new MouseReader();
   }


   /**
    * Creates a new MagicommPenSocket device
    * 
    * @param configuration
    * @return a new MagicommPenSocket device
    */
   private static InputDevice createMagicommPenSocketInputDevice(
         XMLConfiguration configuration) {
      throw new NotImplementedException();
      // return
      // MagicommPenSocket.createPens(getParameter(configuration,MAGICOM_PEN_SOCKET,PORT)).get(0);
   }


   /**
    * Returns an XPath expression
    * 
    * @param deviceIdentifier
    * @param parameterName
    * @return
    */
   private static String getParameterXPathString(String deviceIdentifier,
         String parameterName) {
      return "inputdevices/device[@name='" + deviceIdentifier
            + "']/parameters/parameter[@name='" + parameterName + "']";
   }


   /**
    * Returns the parameter value as string
    * @param configuration
    * @param deviceIdentifier
    * @param parameterName
    * @return
    */
   private static String getParameter(XMLConfiguration configuration,
         String deviceIdentifier, String parameterName) {
      List list = configuration.getList(getParameterXPathString(
            deviceIdentifier, parameterName));
      if (!list.isEmpty()) {
         return (String)list.get(0);
      }
      return null;
   }


   /**
    * Returns a buffered MagicommPenEventListener
    * 
    * @return a buffered MagicommPenEventListener
    */
   private static BufferedInputDeviceEventListener getMagicommListener(
         XMLConfiguration configuration) {
      String mappingFile = getParameter(configuration, MAGICOM_PEN_SOCKET,
            MAPPING_FILE);
      /**
       * return new BufferedInputDeviceEventListener( new
       * MagicommPenEventListener(new AnotoTransformer(
       * getAnotoMappings(mappingFile))), DEFAULT_BUFFER_SIZE);
       */
      throw new NotImplementedException();
   }


   /**
    * Returns a buffered MouseReaderEventListener
    * 
    * @return a buffered MouseReaderEventListener
    */
   private static BufferedInputDeviceEventListener getMouseListener() {
      return new BufferedInputDeviceEventListener(
            new MouseReaderEventListener(), DEFAULT_BUFFER_SIZE);
   }

   /**
    * Returns an Anoto document handler
    * 
    * @param filename filename of the xml mapping file
    * @return an anoto document handler
    * 
    * public static DocumentHandler getAnotoMappings(String filename) { try {
    * return JdomDocument.load(InputDeviceFactory.class.getClassLoader()
    * .getResourceAsStream(filename.trim())); } catch (final NullPointerException
    * e) { e.printStackTrace(); return null; } }
    */

}
