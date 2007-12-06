/*
 * @(#)JdomCommandElement.java  1.0   Dec 06, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :  XML support for the <command> element.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 06, 2007     crocimi     Initial Release
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
import org.ximtec.igesture.geco.UserAction.CommandExecutor;




/**
 * XML support for the <command> element.
 * 
 * @version 1.0 Dec 06, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class JdomCommandElement extends Element {

   private static final String COMMAND = "command";


   public JdomCommandElement(CommandExecutor action) {
      this(COMMAND, action.toString());
   } // JdomCommandElement


   public JdomCommandElement(String name, String content) {
      super(name);
      this.addContent(content);
   } // JdomCommandElement

}

