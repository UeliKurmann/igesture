/*
 * @(#)JdomGestureToActionMapping.java	1.0   Nov 28, 2007
 *
 * Author		:	Beat Signer, signer@inf.ethz.ch
 *
 * Purpose		: XML support for the GestureToActionMapping class.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 28, 2007		crocimi		Initial Release
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
import org.ximtec.igesture.core.GestureSet;



/**
 * Comment
 * @version 1.0 Nov 28, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class JdomGestureSetName extends Element {


      public static final String ROOT_TAG = "gestureSet";



      public JdomGestureSetName(GestureSet gestureSet) {
         super(ROOT_TAG);
         this.addContent(gestureSet.getName());
      }


      @SuppressWarnings("unchecked")
      public static Object unmarshal(Element gestureSetElement) {

         return gestureSetElement.getValue();
      } // unmarshal

   }
   


