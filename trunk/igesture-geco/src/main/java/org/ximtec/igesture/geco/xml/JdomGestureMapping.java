/*
 * @(#)JdomGestureMapping.java	1.0   Nov 28, 2007
 *
 * Author		:	Michele croci, mcroci@gmail.com
 *
 * Purpose		:   XML support for the <gestureMapping> element.
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
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.GestureAction;
import org.ximtec.igesture.geco.action.CommandExecutor;
import org.ximtec.igesture.geco.action.KeyboardSimulation;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;




/**
 * XML support for the <gestureMapping> element.
 * 
 * @version 0.9, Nov 28, 2007
 * @author Michele croci, mcroci@gmail.com
 */
public class JdomGestureMapping extends Element {



   private static final String ROOT_TAG = "gestureMapping";
   
   private static final String KEY = "key";
   
   private static final String GESTURE = "gesture";
   
   private static GestureSet gestureSet;
   
   
   public JdomGestureMapping(GestureToActionMapping map, GestureSet gestureSet) {
      super(ROOT_TAG);
      JdomGestureMapping.gestureSet = gestureSet;
      addContent(new JdomGestureElement(map.getGestureClass()));
      if (map.getAction() instanceof KeyboardSimulation){
         KeyboardSimulation keyAction = (KeyboardSimulation) map.getAction();
         addContent( new JdomKeyElement(keyAction));
      }else if(map.getAction() instanceof CommandExecutor){
         CommandExecutor action = (CommandExecutor) map.getAction();
         addContent( new JdomCommandElement(action));
      }
      
   }


   @SuppressWarnings("unchecked")
   public static Object unmarshal(Element gestureMappingElement) {
      GestureClass gc = gestureSet.getGestureClass(gestureMappingElement.getChildText(GESTURE));
      GestureAction action = null;
      if (gestureMappingElement.getChild(KEY)!=null){
         action = new KeyboardSimulation(gestureMappingElement.getChildText(KEY));
      }  

      return new GestureToActionMapping(gc, action);
   } // unmarshal

}