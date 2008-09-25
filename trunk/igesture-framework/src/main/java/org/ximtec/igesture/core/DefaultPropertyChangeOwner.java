/*
 * @(#)$Id: MainController.java 463 2008-03-23 23:05:09Z kurmannu $
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 25.03.2008 ukurmann  Initial Release
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
import java.beans.PropertyChangeSupport;


/**
 * Transient Data Object
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public abstract class DefaultPropertyChangeOwner implements PropertyChangeNotifier {

   protected transient PropertyChangeSupport propertyChangeSupport;


   /**
    * Constructs a new transient data object.
    */
   public DefaultPropertyChangeOwner() {
      this.propertyChangeSupport = new PropertyChangeSupport(this);
   }


   /**
    * This method is used to deserialize transient fields
    * @return
    */
   private Object readResolve() {
      this.propertyChangeSupport = new PropertyChangeSupport(this);
      return this;
   }


   /**
    * {@inheritDoc}
    */
   @Override
   public void addPropertyChangeListener(PropertyChangeListener listener) {
      for(PropertyChangeListener registredListener:propertyChangeSupport.getPropertyChangeListeners()){
         if(registredListener == listener){
            return;
         }
      }
      propertyChangeSupport.addPropertyChangeListener(listener);
   }


   /**
    * {@inheritDoc}
    */
   @Override
   public void addPropertyChangeListener(String propertyName,
         PropertyChangeListener listener) {
      propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
   }


   /**
    * {@inheritDoc}
    */
   @Override
   public void removePropertyChangeListener(PropertyChangeListener listener) {
      propertyChangeSupport.removePropertyChangeListener(listener);
   }


   /**
    * {@inheritDoc}
    */
   @Override
   public void removePropertyChangeListener(String propertyName,
         PropertyChangeListener listener) {
      propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
   }

}
