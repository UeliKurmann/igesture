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
 * 30.11.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;

import java.util.List;

import org.ximtec.igesture.core.Gesture;


/**
 * Comment
 * @version 1.0 30.11.2008
 * @author Ueli Kurmann
 */
public interface GestureDevice<E, F> {

   /**
    * Initializes the device. After executing this method, gestures can be
    * captured.
    */
   void init();


   /**
    * Disposes the device. After executing this method, gestures are not captured
    * anymore and all dependent resources are released.
    */
   void dispose();


   /**
    * Returns the gestures. This method should block, if no gesture is available. 
    * @return the gesture. 
    */
   Gesture<E> getGesture();


   /**
    * Deletes the current gesture. 
    */
   void clear();


   /**
    * Returns chunks of a gesture while drawing it. 
    * @return chunks of a gesture. 
    */
   List<F> getChunks();


   /**
    * Add a gesture handler
    * @param listener
    */
   void addGestureHandler(GestureEventListener listener);


   /**
    * Remove a gesture handler
    * @param listener
    */
   void removeGestureHandler(GestureEventListener listener);

}
