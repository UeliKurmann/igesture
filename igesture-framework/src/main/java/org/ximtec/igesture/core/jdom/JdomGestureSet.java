/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   XML support for the GestureSet class.
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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;


/**
 * XML support for the GestureSet class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class JdomGestureSet extends Element {

   private static final Logger LOGGER = Logger.getLogger(JdomGestureSet.class
         .getName());

   public static final String ROOT_TAG = "set";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String UUID_ATTRIBUTE = "id";

   public static final String REFID_ATTRIBUTE = "idref";


   public JdomGestureSet(GestureSet gestureSet) {
      super(ROOT_TAG);
      setAttribute(NAME_ATTRIBUTE, gestureSet.getName());
      setAttribute(UUID_ATTRIBUTE, gestureSet.getId());

      for (final GestureClass gestureClass : gestureSet.getGestureClasses()) {
         // addContent(new JdomGestureClass(gestureClass));
         final Element classElement = new Element(JdomGestureClass.ROOT_TAG);
         classElement.setAttribute(REFID_ATTRIBUTE, gestureClass.getId());
         addContent(classElement);
      }

   }


   @SuppressWarnings("unchecked")
   public static GestureSet unmarshal(Element setElement) {
      final String name = setElement.getAttributeValue(NAME_ATTRIBUTE);
      final String uuid = setElement.getAttributeValue(UUID_ATTRIBUTE);
      final GestureSet gestureSet = new GestureSet(name);
      gestureSet.setId(uuid);

      for (final Element classElement : (List<Element>)setElement
            .getChildren(JdomGestureClass.ROOT_TAG)) {
         final String classID = classElement.getAttributeValue(REFID_ATTRIBUTE);
         GestureClass gestureClass = null;

         if (classID != null) {
            Element classInstance = null;

            try {
               classInstance = (Element)XPath.selectSingleNode(setElement
                     .getParent(), "./" + JdomGestureClass.ROOT_TAG + "[@"
                     + UUID_ATTRIBUTE + "='" + classID + "']");
               gestureClass = JdomGestureClass
                     .unmarshal(classInstance);

            }
            catch (final JDOMException e) {
               LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
            }

         }
         else {
            gestureClass = JdomGestureClass.unmarshal(classElement);
         }

         gestureSet.addGestureClass(gestureClass);
      }

      return gestureSet;
   } // unmarshal

}
