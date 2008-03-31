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
 * 25.03.2008			ukurmann	Initial Release
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

import java.beans.PropertyChangeListener;



/**
 * Comment
 * @version 1.0 25.03.2008
 * @author Ueli Kurmann
 */
public interface PropertyChangeOwner {
   /**
    * Adds a property change listener
    * @param listener
    */
   public void addPropertyChangeListener(PropertyChangeListener listener);

   /**
    * Adds a property change listener
    * @param propertyName
    * @param listener
    */
   public void addPropertyChangeListener(String propertyName,
           PropertyChangeListener listener);

   /**
    * Removes a property change listener
    * @param listener
    */
   public void removePropertyChangeListener(PropertyChangeListener listener);

   /**
    * Removes a property change listener
    * @param propertyName
    * @param listener
    */
   public void removePropertyChangeListener(String propertyName,
           PropertyChangeListener listener);

}
