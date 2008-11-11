/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   XML support for the TextDescriptor class.
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


package org.ximtec.igesture.core.jdom;

import org.jdom.Element;
import org.sigtec.jdom.element.JdomStringElement;
import org.ximtec.igesture.core.TextDescriptor;


/**
 * XML support for the TextDescriptor class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class JdomTextDescriptor extends Element {

   public static final String ROOT_TAG = "textDescriptor";

   public static final String UUID_ATTRIBUTE = "id";

   public static final String TEXT = "text";


   public JdomTextDescriptor(TextDescriptor descriptor) {
      super(ROOT_TAG);
      setAttribute(UUID_ATTRIBUTE, descriptor.getId());
      addContent(new JdomStringElement(TEXT, descriptor.getText()));
   }


   public static Object unmarshal(Element descriptor) {
      final String uuid = descriptor.getAttributeValue(UUID_ATTRIBUTE);
      final TextDescriptor textDescriptor = new TextDescriptor();
      textDescriptor.setId(uuid);
      textDescriptor.setText(descriptor.getChild(TEXT).getText());
      return textDescriptor;
   } // unmarshal

}
