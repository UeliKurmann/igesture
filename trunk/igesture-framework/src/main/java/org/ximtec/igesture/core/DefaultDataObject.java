/*
 * @(#)DefaultDataObject.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Default data object implementation.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
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

import org.ximtec.igesture.storage.StorageManager;

/**
 * Default data object implementation.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public abstract class DefaultDataObject implements DataObject {

  public static final String PROPERTY_ID = "id";

  private String id;

  protected PropertyChangeSupport propertyChangeSupport;

  /**
   * Constructs a new default data object.
   */
  public DefaultDataObject() {
    this.propertyChangeSupport = new PropertyChangeSupport(this);
    setId(StorageManager.generateUUID());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String id) {
    String oldValue = this.id;
    this.id = id;
    propertyChangeSupport.firePropertyChange(PROPERTY_ID, oldValue, id);
  } // setID

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {
    if (id == null) {
      setId(StorageManager.generateUUID());
    }

    return id;
  } // getID

  /**
   * {@inheritDoc}
   */
  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
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

  /**
   * {@inheritDoc}
   */
  public void accept(Visitor visitor) {
    // not implemented
  }

}
