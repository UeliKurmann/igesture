/*
 * @(#)$Id$
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

import org.jdom.Element;




/**
 * XML support for the <minimize> element.
 * 
 * @version 0.9, Jan 15, 2008
 * @author Michele Croci, mcroci@gmail.com
 */
public class JdomMinimizeElement extends Element {

   private static final String COMMAND = "minimize";


   public JdomMinimizeElement(boolean minimize) {
      this(COMMAND, minimize);
   } // JdomConfigurationElement


   public JdomMinimizeElement(String name, boolean minimize) {
      super(name);
      this.addContent(String.valueOf(minimize));
   } // JdomConfigurationElement

}