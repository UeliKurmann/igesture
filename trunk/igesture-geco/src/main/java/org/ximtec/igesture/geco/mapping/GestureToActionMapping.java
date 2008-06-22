/*
 * @(#)GestureToActionMapping.java	1.0   Nov 19, 2007
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.mapping;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.event.GestureAction;



/**
 * Interface describing general Action for Gesture mapping
 * 
 * @version 0.9, Nov 19, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class GestureToActionMapping {
   
   protected GestureClass gestureClass = null;
   
   protected GestureAction action = null;
   
   
   /**
    * Constructor
    * 
    * @param gesture the gesture class
    * @param action the action corresponding to the gesture
    */
   public GestureToActionMapping(GestureClass gesture, GestureAction action) {
      this.gestureClass = gesture;
      this.action = action;
   }
   

   /**
    * Returns the gesture class
    * 
    * @return the gesture class
    */
   public GestureClass getGestureClass(){
      return gestureClass;
   }
   
   
   /**
    * Returns the gesture class
    * 
    * @return the description of the Mapping
    */
   public String toString(){
      //TODO: change implementation of this method?
      return gestureClass.getName();
   }
   
   
   /**
    * Returns the action
    * 
    * @return the action
    */
   public GestureAction getAction(){
      return action;
   }
   



}
