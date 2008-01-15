/*
 * @(#)JdomDeviceElement.java  1.0   Jan 15, 2008
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :  XML support for the <device> element.
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

import org.jdom.Element;




/**
 * XML support for the <device> element.
 * 
 * @version 0.9, Jan 15, 2008
 * @author Michele Croci, mcroci@gmail.com
 */
public class JdomDeviceElement extends Element {

   private static final String COMMAND = "device";
   
   private static final String NAME = "name";
   
   private static final String SELECTED = "selected";
   


   public JdomDeviceElement(String device, boolean selected) {
      this(COMMAND, device);
   } // JdomConfigurationElement


   public JdomDeviceElement(String name, String device) {
      super(name);
      //add attribute
      //add 2 attribute
   } // JdomConfigurationElement

}