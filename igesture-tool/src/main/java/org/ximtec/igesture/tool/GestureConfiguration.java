/*
 * @(#)GestureConfiguration.java  1.0   Jan 24, 2007
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Gesture Configuration
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 24.1.2007        ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEventListener;
import org.ximtec.igesture.io.InputDeviceFactory;


/**
 * @author Ueli Kurmann
 *
 */
public class GestureConfiguration {
   
   /**
    * 
    */
   private static final String SELECTED_INPUT_DEVICE = "inputdevices/device[@selected='true']/@name";
   private static final String SELECTED_INPUT_DEVICE2 = "inputdevices/device/name";
   
   private static final String PROPERTY_DATABASE = "database";
   private static final String PROPERTY_ALGORITHM = "algorithm/class";
   private static final String PROPERTY_TAB = "tab/class";
    
   private XMLConfiguration configuration;
   
   public GestureConfiguration(String file){
      try {
         configuration = new XMLConfiguration();
         configuration.setFileName(file);
         configuration.setExpressionEngine(new XPathExpressionEngine());
         configuration.setAutoSave(true);
         configuration.load();
         
         selectDevice();
      }
      catch (ConfigurationException e) {
         e.printStackTrace();
      }
   }
   
   /**
    * Returns the filename of the database
    * @return the filename of the database
    */
   public String getDatabase(){
      return configuration.getString(PROPERTY_DATABASE);
   }
   
   /**
    * Returns the list of algorithm
    * @return the list of algorithm
    */
   @SuppressWarnings("unchecked")
   public List<String> getAlgorithms(){
        return configuration.getList(PROPERTY_ALGORITHM);
   }
   
   /**
    * Returns the list of tabs
    * @return the list of tabs
    */
   @SuppressWarnings("unchecked")
   public List<String> getTabs(){
      return configuration.getList(PROPERTY_TAB);
   }
   
   /**
    * Returns the input device
    * @return the input device
    * 
    */
   public InputDevice getInputDevice(){
      List list = configuration.getList(SELECTED_INPUT_DEVICE);
      if(!list.isEmpty()){
         String deviceName = (String)list.get(0);
         return InputDeviceFactory.createInputDevice(deviceName, configuration);
      }
      
      return null;
   }
   
   /**
    * Returns the input device
    * @return the input device
    * 
    */
   public InputDeviceEventListener getInputDeviceEventListener(){
      List list = configuration.getList(SELECTED_INPUT_DEVICE);
      if(!list.isEmpty()){
         String deviceName = (String)list.get(0);
         return InputDeviceFactory.createInputDeviceEventListener(deviceName, configuration);
      }
      return null;
   }
   
   
   private synchronized void selectDevice(){
      int i = 1;
      for(String deviceName:(List<String>)configuration.getList(SELECTED_INPUT_DEVICE2)){
        // configuration.setProperty("inputdevices/device["+i+"] @selected", "true");
         
       
         i++;
      }
      
   }
}
