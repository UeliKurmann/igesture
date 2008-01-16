/*
 * @(#)Configuration.java  1.0   Jan 24, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com,
 *        
 * Purpose      :   Geco configuration.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Jan 24, 2007     crocimi    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEventListener;
import org.sigtec.util.Constant;
import org.ximtec.igesture.io.InputDeviceFactory;


/**
 * Geco configuration.
 * 
 * @version 1.0, Jan 2008
 * @author Michele Croci, mcroci@gmail.com
 */
public class Configuration {

   private static final Logger LOGGER = Logger.getLogger(Configuration.class
         .getName());

   private static final String ALL_INPUT_DEVICES = "inputdevices/device/@name";
   private static final String SELECTED_INPUT_DEVICE = "inputdevices/device[@selected='true']/@name";
   private static final String SELECTED_INPUT_DEVICE2 = "inputdevices/device/name";

   private static final String PROPERTY_DATABASE = "database";
   private static final String PROPERTY_ALGORITHM = "algorithm/class";
   private static final String PROPERTY_TAB = "tab/class";
   
   private static final String MINIMIZE = "minimize";
   
   private static final String TRUE = "true";
   private static final String FALSE = "false";
   

   private XMLConfiguration configuration;


   public Configuration(String file) {
      try {
         File confFile;
         try {
            confFile = new File(ClassLoader.getSystemResource(file).toURI());
         }
         catch (URISyntaxException e) {
            confFile = new File(ClassLoader.getSystemResource(file).getPath());
         }
         configuration = new XMLConfiguration();
         configuration.setFileName(confFile.getPath());
         configuration.setExpressionEngine(new XPathExpressionEngine());
         configuration.setAutoSave(true);
         configuration.load();
         selectDevice();
      }
      catch (ConfigurationException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

   }


   /**
    * Returns the filename of the database.
    * 
    * @return the filename of the database.
    */
   public String getDatabase() {
      return configuration.getString(PROPERTY_DATABASE);
   } // getDatabase


   /**
    * Returns the list of algorithms.
    * 
    * @return the list of algorithms.
    */
   @SuppressWarnings("unchecked")
   public List<String> getAlgorithms() {
      return configuration.getList(PROPERTY_ALGORITHM);
   } // getAlgorithms


   /**
    * Returns the list of tabs.
    * 
    * @return the list of tabs.
    */
   @SuppressWarnings("unchecked")
   public List<String> getTabs() {
      return configuration.getList(PROPERTY_TAB);
   } // getTabs


   /**
    * Returns the input device.
    * 
    * @return the input device.
    * 
    */
   public InputDevice getInputDevice() {
      List list = configuration.getList(SELECTED_INPUT_DEVICE);
      if (!list.isEmpty()) {
         String deviceName = (String)list.get(0);
         return InputDeviceFactory.createInputDevice(deviceName, configuration);
      }

      return null;
   } // getInputDevice


   /**
    * Returns the selected input device name.
    * 
    * @return the selected input device name.
    * 
    */
   public String getInputDeviceName() {
      List list = configuration.getList(SELECTED_INPUT_DEVICE);
      if (!list.isEmpty()) {
         return (String)list.get(0);
      }
      return null;
   } // getInputDeviceName
   
   /**
    * Returns true if the value of <minimize> is true, otherwise false;
    * 
    * @return the value of <minimize>
    * 
    */
   public boolean getMinimize() {
      List list = configuration.getList(MINIMIZE);
      if (!list.isEmpty()) {
         if(((String)list.get(0)).equals(TRUE)){
            return true;
         }
         else{
            return false;
         }
      }
      return false;
   } // getInputDeviceName


   /**
    * Returns all the available input devices.
    * 
    * @return the input devices.
    * 
    */
   public List getInputDevices() {
      List list = configuration.getList(ALL_INPUT_DEVICES);
      return list;
   } // getInputDevices


   /**
    * Returns the input device.
    * 
    * @return the input device.
    * 
    */
   public InputDeviceEventListener getInputDeviceEventListener() {
      List list = configuration.getList(SELECTED_INPUT_DEVICE);

      if (!list.isEmpty()) {
         String deviceName = (String)list.get(0);
         return InputDeviceFactory.createInputDeviceEventListener(deviceName,
               configuration);
      }

      return null;
   } // getInputDeviceEventListener


   private synchronized void selectDevice() {
      int i = 1;

      for (String deviceName : (List<String>)configuration
            .getList(SELECTED_INPUT_DEVICE2)) {
         // configuration.setProperty("inputdevices/device["+i+"] @selected",
         // "true");
         i++;
      }

   } // selectDevice

}
