/*
 * @(#)PropertyChangeNotifier.java   1.0   Mar 25, 2008
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		:   Interface to be implemented by classes that offer a
 *                  property change notification.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 25.03.2008		ukurmann	Initial Release
 * 25.09.2008       bsigner     Cleanup
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
 * Interface to be implemented by classes that offer a property change
 * notification.
 * 
 * @version 1.0 25.03.2008
 * @author Ueli Kurmann
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface PropertyChangeNotifier {

   /**
    * Adds a property change listener.
    * @param listener the property change listener to be added.
    */
   public void addPropertyChangeListener(PropertyChangeListener listener);


   /**
    * Adds a property change listener
    * @param propertyName the name of the property to be tracked by the property
    *            change listener.
    * @param listener the property change listener to be added.
    */
   public void addPropertyChangeListener(String propertyName,
         PropertyChangeListener listener);


   /**
    * Removes a property change listener.
    * @param listener the property change listener to be removed.
    */
   public void removePropertyChangeListener(PropertyChangeListener listener);


   /**
    * Removes a property change listener.
    * @param propertyName the name of the property for which the listener has to
    *            be removed.
    * @param listener
    */
   public void removePropertyChangeListener(String propertyName,
         PropertyChangeListener listener);

}
