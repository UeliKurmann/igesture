/*
 * @(#)GestureSetListener.java	1.0   Nov 20, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  Listener to "Gesture Set changed" event
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 20, 2007		crocimi    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.event;


import java.util.EventListener;
import java.util.EventObject;


/**
 * Data model gesture set listener.
 * 
 * @version 1.0 Nov 20, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public interface GestureSetLoadListener extends EventListener {
 
   
   public void gestureSetChanged(EventObject event);

}


