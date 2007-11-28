/*
 * @(#)GestureMapping.java	1.0   Nov 19, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Interface describing general Action for Gesture mapping
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 19, 2007 	crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.mapping;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.event.EventHandler;
import org.ximtec.igesture.geco.UserDefinedAction;



/**
 * Interface describing general Action for Gesture mapping
 * 
 * @version 1.0 Nov 19, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class GestureToActionMapping {
   
   protected GestureClass gestureClass=null;
   
   protected EventHandler action = null;
   
   //public GestureToActionMapping() {}
   
   public GestureToActionMapping(GestureClass gesture, EventHandler action) {
      this.gestureClass = gesture;
      this.action = action;
   }
   

   
   public GestureClass getGestureClass(){
      return gestureClass;
   }
   
   public String toString(){
      return gestureClass.getName();
   }
   
   
   public EventHandler getAction(){
      return action;
   }
   



}
