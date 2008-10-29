/*
 * @(#)InputDeviceClient.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   Factory to create different input devices.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;

import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.sigtec.exception.NotImplementedException;
import org.sigtec.input.BufferedInputDeviceEventListener;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEventListener;


/**
 * Factory to create different input devices.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class InputDeviceFactory {

   public static final String MAGICOM_PEN_SOCKET = "MagicommPenSocket";

   public static final String MOUSE = "Mouse";
   
   public static final String TABLET_PC = "TabletPC";

   private static final String MAPPING_FILE = "mappingFile";

   public static final int DEFAULT_BUFFER_SIZE = 10000;

   public static final String PORT = "port";

   public static final String COM = "com";


   /**
    * Creates a new input device based on the identifier.
    * @param identifier the identifier of the input device.
    * @param configuration the configuration object containing the parameters.
    * @return the newly created input device.
    */
   public static InputDevice createInputDevice(String identifier,
         XMLConfiguration configuration) {
      if (identifier.equals(MOUSE)) {
         return createMouseInputDevice();
      }
      else if (identifier.equals(MAGICOM_PEN_SOCKET)) {
         return createMagicommPenSocketInputDevice(configuration);
      }
      else if(identifier.equals(TABLET_PC)){
    	  return createWintabInputDevice();
    	 
      }

      return null;
   } // createInputDevice


   /**
    * Creates a new input event device listener.
    * @param identifier the identifier of the input device.
    * @param configuration the configuration object containing the parameters.
    * @return the newly created input event device listener.
    */
   public static InputDeviceEventListener createInputDeviceEventListener(
         String identifier, XMLConfiguration configuration) {
      if (identifier.equals(MOUSE)) {
         return getMouseListener();
      }
      else if (identifier.equals(MAGICOM_PEN_SOCKET)) {
         return getMagicommListener(configuration);
      }
      else if (identifier.equals(TABLET_PC)){
    	  return getTabletListener();
      }

      return null;
   } // createInputDeviceEventListener


   /**
    * Creates a new mouse input device.
    * 
    * @return the newly created mouse input device.
    */
   private static MouseReader createMouseInputDevice() {
      return new MouseReader();
   } // createMouseInputDevice

   
   /**
    * Creates a new tablet input device.
    * 
    * @return the newly created tablet input device.
    */
   private static WacomReader createWintabInputDevice() {
      return new WacomReader();
   } // createTabletInputDevice

   
   
   /**
    * Creates a new MagicommPenSocket device.
    * 
    * @param configuration the configuration object containing the parameters.
    * @return a newly created MagicommPenSocket device.
    */
   private static InputDevice createMagicommPenSocketInputDevice(
         XMLConfiguration configuration) {
      throw new NotImplementedException();
      // return
      // MagicommPenSocket.createPens(getParameter(configuration,MAGICOM_PEN_SOCKET,PORT)).get(0);
   } // createMagicommPenSocketInputDevice


   /**
    * Returns an XPath expression.
    * 
    * @return the XPath expression.
    */
   private static String getParameterXPathString(String deviceIdentifier,
         String parameterName) {
      return "inputdevices/device[@name='" + deviceIdentifier
            + "']/parameters/parameter[@name='" + parameterName + "']";
   } // getParameterXPathString


   /**
    * Returns the parameter value as string.
    * @return the parameter value as string.
    */
   private static String getParameter(XMLConfiguration configuration,
         String deviceIdentifier, String parameterName) {
      List<?> list = configuration.getList(getParameterXPathString(
            deviceIdentifier, parameterName));

      if (!list.isEmpty()) {
         return (String)list.get(0);
      }

      return null;
   } // getParameter


   /**
    * Returns a buffered MagicommPenEventListener.
    * 
    * @return a buffered MagicommPenEventListener.
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
   } // getMagicommListener


   /**
    * Returns a buffered MouseReaderEventListener.
    * 
    * @return a buffered MouseReaderEventListener.
    */
   private static BufferedInputDeviceEventListener getMouseListener() {
      return new BufferedInputDeviceEventListener(
            new MouseReaderEventListener(), DEFAULT_BUFFER_SIZE);
   } // getMouseListener
   
   /**
    * Returns a buffered TabletReaderEventListener.
    * 
    * @return a buffered TabletReaderEventListener.
    */
   private static BufferedInputDeviceEventListener getTabletListener() {
      return new BufferedInputDeviceEventListener(
            new WacomReaderEventListener(), DEFAULT_BUFFER_SIZE);
   } // getTabletListener

   /**
    * Returns an Anoto document handler
    * 
    * @param filename filename of the XML mapping file
    * @return an Anoto document handler
    * 
    * public static DocumentHandler getAnotoMappings(String filename) { try {
    * return JdomDocument.load(InputDeviceFactory.class.getClassLoader()
    * .getResourceAsStream(filename.trim())); } catch (final NullPointerException
    * e) { e.printStackTrace(); return null; } }
    */

}
