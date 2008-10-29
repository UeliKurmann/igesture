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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

/**
 * Gesture interface to be implemented by any gesture handling component.
 * 
 * @version 1.0 11.04.2008
 * @author Ueli Kurmann
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface Gesture<T> {

   /**
    * Returns the gesture
    * @return the gesture
    */
   T getGesture();


   /**
    * Returns the name of the gesture
    * @return the name of the gesture
    */
   String getName();
   
   /**
    * Sets the name of the gesture
    * @param name the name of the gesture
    */
   void setName(String name);


   /**
    * Visitor implementation
    * @param visitor
    */
   void accept(Visitor visitor);

}
