/*
 * @(#)JdomInputDevicesElement.java  1.0   Jan 15, 2008
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :  XML support for the <inputdevices> element.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Jan 15, 2008     crocimi     Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.xml;

import java.util.List;

import org.jdom.Element;
import org.sigtec.input.InputDevice;
import org.ximtec.igesture.geco.UserAction1.CommandExecutor;
import org.ximtec.igesture.tool.GestureConfiguration;




/**
 * XML support for the <inputdevices> element.
 * 
 * @version 0.9, Jan 15, 2008
 * @author Michele Croci, mcroci@gmail.com
 */
public class JdomInputDevicesElement extends Element {

   private static final String COMMAND = "inputdevices";


   public JdomInputDevicesElement(List<String> devices, boolean[] arr) {
      this(COMMAND);
      for(int i=0; i<devices.size();i++){
    	  this.addContent(new JdomDeviceElement(devices.get(i), arr[i]));
      }
   } // JdomConfigurationElement


   public JdomInputDevicesElement(String name) {
      super(name);
   } // JdomConfigurationElement

}