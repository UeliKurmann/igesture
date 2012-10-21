/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		:   Gesture interface to be implemented by any gesture
 *                  handling component.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 11.04.2008       ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import org.ximtec.igesture.io.GestureDevice;

/**
 * Gesture interface to be implemented by any gesture handling component.
 * 
 * @version 1.0 11.04.2008
 * @author Ueli Kurmann
 * @author Beat Signer, bsigner@vub.ac.be
 */
public interface Gesture<T> {

   /**
    * Returns the gesture.
    * @return the gesture.
    */
   T getGesture();


   /**
    * Sets the name of the gesture.
    * @param name the name of the gesture.
    */
   void setName(String name);


   /**
    * Returns the name of the gesture.
    * @return the name of the gesture.
    */
   String getName();


   /**
    * Visitor implementation.
    * @param visitor the visitor.
    */
   void accept(Visitor visitor);
   
   /**
    * Get the source device of the gesture.
    * @return		The source device.
    */
   GestureDevice<?,?> getSource();
   
   /**
    * Set the source device of the gesture.
    * @param device		The source device.
    */
   void setSource(GestureDevice<?,?> device);

}
