/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 02.12.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;

import java.util.HashSet;
import java.util.Set;

import org.ximtec.igesture.core.Gesture;



/**
 * Comment
 * @version 1.0 02.12.2008
 * @author Ueli Kurmann
 */
public abstract class AbstractGestureDevice<E, F> implements GestureDevice<E, F>{

   private Set<GestureEventListener> gestureListeners;
   
   public AbstractGestureDevice(){
      gestureListeners = new HashSet<GestureEventListener>();
   }
   
   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.io.GestureDevice#removeGestureHandle(org.ximtec.igesture.io.GestureEventListener)
    */
   @Override
   public void removeGestureHandle(GestureEventListener listener) {
      gestureListeners.remove(listener);
   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.io.GestureDevice#addGestureHandler(org.ximtec.igesture.io.GestureEventListener)
    */
   @Override
   public void addGestureHandler(GestureEventListener listener) {
      gestureListeners.add(listener);
   }
   
   /**
    * Fires a Gesture Event
    * @param gesture
    */
   protected void fireGestureEvent(Gesture<?> gesture){
      for(GestureEventListener listener:gestureListeners){
         listener.handleGesture(gesture);
      }
   }
   
   /**
    * Removes all Gesture listeners
    */
   protected void removeAllListener(){
      gestureListeners.clear();
   }

   
}
