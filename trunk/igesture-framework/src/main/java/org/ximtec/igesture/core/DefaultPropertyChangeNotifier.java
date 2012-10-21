/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose      :   Default implementation of the PropertyChangeNotifier
 *                  interface.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 25.03.2008       ukurmann    Initial Release
 * 25.09.2008       bsigner     Cleanup
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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * Default implementation of the PropertyChangeNotifier interface.
 * 
 * @version 1.0 25.03.2008
 * @author Ueli Kurmann
 * @author Beat Signer, bsigner@vub.ac.be
 */
public abstract class DefaultPropertyChangeNotifier implements
      PropertyChangeNotifier {

   protected transient PropertyChangeSupport propertyChangeSupport;


   /**
    * Constructs a new default property change notifier.
    */
   public DefaultPropertyChangeNotifier() {
      this.propertyChangeSupport = new PropertyChangeSupport(this);
   }


   /**
    * This method is used to deserialise transient fields.
    * @return the deserialised object.
    */
   private Object readResolve() {
      this.propertyChangeSupport = new PropertyChangeSupport(this);
      return this;
   } // readResolve


   /**
    * {@inheritDoc}
    */
   @Override
   public void addPropertyChangeListener(PropertyChangeListener listener) {
      for (PropertyChangeListener registredListener : propertyChangeSupport
            .getPropertyChangeListeners()) {

         if (registredListener == listener) {
            return;
         }

      }

      propertyChangeSupport.addPropertyChangeListener(listener);
   } // addPropertyChangeListener


   /**
    * {@inheritDoc}
    */
   @Override
   public void addPropertyChangeListener(String propertyName,
         PropertyChangeListener listener) {
      propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
   } // addPropertyChangeListener


   /**
    * {@inheritDoc}
    */
   @Override
   public void removePropertyChangeListener(PropertyChangeListener listener) {
      propertyChangeSupport.removePropertyChangeListener(listener);
   } // removePropertyChangeListener


   /**
    * {@inheritDoc}
    */
   @Override
   public void removePropertyChangeListener(String propertyName,
         PropertyChangeListener listener) {
      propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
   } // removePropertyChangeListener

}
