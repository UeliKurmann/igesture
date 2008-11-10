/*
 * @(#)$Id$
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :  XML support for the <key> element.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 29, 2007     crocimi     Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.xml;

import org.jdom.Element;
import org.ximtec.igesture.geco.action.KeyboardSimulation;



/**
 * XML support for the <key> element.
 * 
 * @version 0.9, Nov 29, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class JdomKeyElement extends Element {

   private static final String KEY = "key";


   public JdomKeyElement(KeyboardSimulation action) {
      this(KEY, action.toString());
   } // JdomKeyElement


   public JdomKeyElement(String name, String content) {
      super(name);
      this.addContent(content);
   } // JdomKeyElement

}
