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

import java.util.List;

import org.ximtec.igesture.core.Gesture;



/**
 * Comment
 * @version 1.0 02.12.2008
 * @author Ueli Kurmann
 */
public interface GestureEventListener {

   void handleGesture(Gesture<?> gesture);
   
   void handleChunks(List<?> chunks);
   
}
