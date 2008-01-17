/*
 * @(#)JdomLastProjectElement.java  1.0   Jan 15, 2008
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :  XML support for the <lastproject> element.
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
 * XML support for the <lastproject> element.
 * 
 * @version 0.9, Jan 15, 2008
 * @author Michele Croci, mcroci@gmail.com
 */
public class JdomLastProjectElement extends Element {

   private static final String COMMAND = "lastproject";


   public JdomLastProjectElement(String path) {
      this(COMMAND, path);
   } // JdomConfigurationElement


   public JdomLastProjectElement(String name, String path) {
      super(name);
      this.addContent(path);
   } // JdomConfigurationElement

}