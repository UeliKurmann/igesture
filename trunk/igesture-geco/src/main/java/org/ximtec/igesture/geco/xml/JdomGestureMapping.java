/*
 * @(#)JdomGestureMapping.java	1.0   Nov 28, 2007
 *
 * Author		:	Michele croci, mcroci@gmail.com
 *
 * Purpose		: 
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
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.EventHandler;
import org.ximtec.igesture.geco.UserAction.KeyboardSimulationAction;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;




/**
 * Comment
 * @version 1.0 Nov 28, 2007
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class JdomGestureMapping extends Element {



   private static final String ROOT_TAG = "gestureMapping";
   
   private static final String KEY = "key";
   
   private static final String GESTURE = "gesture";
   
   private static GestureSet gestureSet;
   
   


   public JdomGestureMapping(GestureToActionMapping map, GestureSet gestureSet) {
      super(ROOT_TAG);
      this.gestureSet = gestureSet;
      addContent(new JdomGestureElement(map.getGestureClass()));
      if (map.getAction() instanceof KeyboardSimulationAction){
         KeyboardSimulationAction keyAction = (KeyboardSimulationAction) map.getAction();
         addContent( new JdomKeyElement(keyAction));
      }
      
   }


   @SuppressWarnings("unchecked")
   public static Object unmarshal(Element gestureMappingElement) {
      GestureClass gc = gestureSet.getGestureClass(gestureMappingElement.getChildText(GESTURE));
      EventHandler action = null;
      if (gestureMappingElement.getChild(KEY)!=null){
         action = new KeyboardSimulationAction(gestureMappingElement.getChildText(KEY));
      }  

      return new GestureToActionMapping(gc, action);
   } // unmarshal

}