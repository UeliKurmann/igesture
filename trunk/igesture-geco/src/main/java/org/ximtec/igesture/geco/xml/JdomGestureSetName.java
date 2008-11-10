/*
 * @(#)$Id$
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   XML support for the <gestureSet> element.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.xml;

import org.jdom.Element;



/**
 * XML support for the <gestureSet> element.
 * 
 * @version 0.9, Nov 28, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class JdomGestureSetName extends Element {


      public static final String ROOT_TAG = "gestureSet";



      public JdomGestureSetName(String gestureSetFileName) {
         super(ROOT_TAG);
         if(gestureSetFileName!=null)
            this.addContent(gestureSetFileName);
      }


      @SuppressWarnings("unchecked")
      public static Object unmarshal(Element gestureSetElement) {

         return gestureSetElement.getValue();
      } // unmarshal

   }
   


