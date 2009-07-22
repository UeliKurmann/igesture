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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core.jdom;

import org.jdom.Element;
import org.sigtec.jdom.element.JdomStringElement;
import org.ximtec.igesture.core.IconDescriptor;


/**
 * XML support for the IconDescriptor class.
 * 
 * @version 1.0, 22. October 2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class JdomIconDescriptor extends Element {

   public static final String ROOT_TAG = "iconDescriptor";

   public static final String UUID_ATTRIBUTE = "id";

   public static final String PATH = "path";


   public JdomIconDescriptor(IconDescriptor descriptor) {
      super(ROOT_TAG);
      setAttribute(UUID_ATTRIBUTE, descriptor.getId());
      addContent(new JdomStringElement(PATH, descriptor.getPath()));
   }


   public static IconDescriptor unmarshal(Element descriptor) {
      String uuid = descriptor.getAttributeValue(UUID_ATTRIBUTE);
      IconDescriptor iconDescriptor = new IconDescriptor();
      iconDescriptor.setId(uuid);
      iconDescriptor.setPath(descriptor.getChild(PATH).getText());
      return iconDescriptor;
   } // unmarshal

}
