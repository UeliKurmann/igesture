/*
 * @(#)JdomConfigurationParameter.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, ueli@smartness.ch
 *
 * Purpose      : 	XML support for ConfigurationParameter class.
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


package org.ximtec.igesture.configuration.jdom;

import org.jdom.Element;


/**
 * XML support for ConfigurationParameter class.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, ueli@smartness.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class JdomConfigurationParameter extends Element {

   public static final String ROOT_TAG = "parameter";

   public static final String NAME_ATTRIBUTE = "name";


   public JdomConfigurationParameter(String name, String value) {
      super(ROOT_TAG);
      this.setAttribute(NAME_ATTRIBUTE, name);
      this.setText(value);
   }


   public static String[] unmarshal(Element parameter) {
      final String[] result = new String[2];
      result[0] = parameter.getAttribute(NAME_ATTRIBUTE).getValue();
      result[1] = parameter.getText();
      return result;
   } // unmarshal

}
