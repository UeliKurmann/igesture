/*
 * @(#)JdomTextDescriptor.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   XML support for TextDescriptor
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


package org.ximtec.igesture.core.jdom;

import org.jdom.Element;
import org.sigtec.jdom.element.JdomStringElement;
import org.ximtec.igesture.core.TextDescriptor;


public class JdomTextDescriptor extends Element {

   public static final String ROOT_TAG = "textDescriptor";

   public static final String UUID_ATTRIBUTE = "id";

   public static final String TEXT = "text";


   public JdomTextDescriptor(TextDescriptor descriptor) {
      super(ROOT_TAG);
      setAttribute(UUID_ATTRIBUTE, descriptor.getID());
      addContent(new JdomStringElement(TEXT, descriptor.getText()));
   }


   public static Object unmarshal(Element descriptor) {
      final String uuid = descriptor.getAttributeValue(UUID_ATTRIBUTE);
      final TextDescriptor textDescriptor = new TextDescriptor("");
      textDescriptor.setID(uuid);
      textDescriptor.setText(descriptor.getChild(TEXT).getText());
      return textDescriptor;
   } // unmarshal

}
