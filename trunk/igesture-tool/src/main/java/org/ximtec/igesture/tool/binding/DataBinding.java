/*
 * @(#)$Id$
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
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.binding;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.commons.beanutils.BeanUtils;
import org.ximtec.igesture.core.DataObject;

public abstract class DataBinding<T extends JComponent> implements FocusListener, PropertyChangeListener {

  private DataObject dataObject;
  private String property;

  /**
   * Constructor
   * 
   * @param dataObject
   *          the data object
   * @param property
   *          the name of the property
   * @param key
   *          the key used by the localization handler (can be null)
   */
  public DataBinding(DataObject dataObject, String property) {
    this.dataObject = dataObject;
    this.dataObject.addPropertyChangeListener(this);
    this.property = property;
  }

  /**
   * This method provides functionality to update the view in case of model
   * changes. This method is invoked on a propertyChange event.
   */
  protected abstract void updateView();

  /**
   * This method provides functionality to update the model.
   */
  protected abstract void updateModel();

  /**
   * Returns the JComponent which is involved in the binding process.
   * 
   * @return
   */
  public abstract T getComponent();

  /**
   * Set a value in the dataobject
   * 
   * @param object
   * @throws PropertyVetoException
   */
  protected void setValue(Object object) {
    try {
      BeanUtils.setProperty(dataObject, property, object);
    } catch (SecurityException e) {
    } catch (IllegalAccessException e) {
    } catch (InvocationTargetException e) {

    }
  }

  /**
   * Read a value from the data object
   * 
   * @return
   */
  protected String getValue() {
    try {
      return BeanUtils.getProperty(dataObject, property);
    } catch (IllegalAccessException e) {
    } catch (InvocationTargetException e) {
    } catch (NoSuchMethodException e) {
    }
    return null;
  }

  /**
   * Returns the data object
   * 
   * @return
   */
  public Object getObject() {
    return dataObject;
  }

  /**
   * Returns the property name
   * 
   * @return
   */
  public String getProperty() {
    return property;
  }

  @Override
  public void focusGained(FocusEvent arg0) {
    // do nothing
  }

  @Override
  public void focusLost(FocusEvent arg0) {
    updateModel();
  }

  @Override
  public final void propertyChange(PropertyChangeEvent evt) {
    if (SwingUtilities.isEventDispatchThread()) {
      updateView();
    } else {
      SwingUtilities.invokeLater(new Runnable() {

        @Override
        public void run() {
          updateView();
        }
      });
    }
  }
}
