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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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

   void init();

   void dispose();

   Gesture<E> getGesture();
   
   void clear();

   List<F> getChunks();

   void addGestureHandler(GestureEventListener listener);

   void removeGestureHandle(GestureEventListener listener);

}
