/*
 * @(#)JdomGestureElement.java	1.0   Nov 29, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  XML jdom element for igesture
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 29, 2007		crocimi		Initial Release
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
import org.ximtec.igesture.core.GestureClass;



/**
 * XML jdom element for igesture
 * 
 * @version 1.0 Nov 29, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class JdomGestureElement extends Element {

   private static final String GESTURE = "gesture";


   public JdomGestureElement(GestureClass gestureClass) {
      this(GESTURE, gestureClass.getName());
   } // JdomStringElement


   public JdomGestureElement(String name, String content) {
      super(name);
      this.addContent(content);
   } // JdomStringElement

}