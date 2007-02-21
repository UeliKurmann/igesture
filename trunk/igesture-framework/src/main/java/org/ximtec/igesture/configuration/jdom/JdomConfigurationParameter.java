/*
 * @(#)JdomConfigurationParameter.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	XML support for ConfigurationParameter
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.configuration.jdom;

import org.jdom.Element;


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