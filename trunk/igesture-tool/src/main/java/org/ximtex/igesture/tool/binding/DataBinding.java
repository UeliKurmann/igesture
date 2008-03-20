/*****************************************************************************************************************
 * (c) Copyright EAO AG, 2007
 * 
 * Project      : Tone-editor Multi Tone Sound Module 56
 * Filename     : $Id: DataBinding.java 93 2007-10-31 10:07:39Z uelikurmann $
 * Programmer   : Ueli Kurmann (UK) / bbv Software Services AG / ueli.kurmann@bbv.ch
 * Creation date: 2007-10-31
 *
 *****************************************************************************************************************
 * Description:
 * Base class for data binding functionality.
 * 
 * This class implements FocusListener and PropertyChangeListern and provides
 * functionality to get and set properties from dataobjects using reflection. 
 * 
 *****************************************************************************************************************
 * Location:
 * $HeadURL: https://svn.bbv.ch/svn/EAOToneditor/trunk/Implementation/src/ch/bbv/eao/toneditor/gui/databinding/DataBinding.java $
 *
 *****************************************************************************************************************
 * Updates:
 * $LastChangedBy: uelikurmann $
 * $LastChangedDate: 2007-10-31 11:07:39 +0100 (Mi, 31 Okt 2007) $
 * $LastChangedRevision: 93 $
 *
 *****************************************************************************************************************/

package org.ximtex.igesture.tool.binding;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;

import org.apache.commons.beanutils.BeanUtils;
import org.ximtec.igesture.core.DataObject;

public abstract class DataBinding<T extends JComponent> implements
		FocusListener, PropertyChangeListener {

	private Object dataObject;
	private String property;

	/**
	 * Constructor
	 * 
	 * @param dataObject
	 *            the data object
	 * @param property
	 *            the name of the property
	 * @param key
	 *            the key used by the localization handler (can be null)
	 */
	public DataBinding(DataObject dataObject, String property) {
		this.dataObject = dataObject;
		this.property = property;
	}

	/**
	 * This method provides functionality to update the view in case of model
	 * changes. This method is invoked on a propertyChange event.
	 */
	public abstract void updateView();

	/**
	 * This method provides functionality to update the model.
	 */
	public abstract void updateModel();

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
	public void propertyChange(PropertyChangeEvent evt) {
		updateView();
	}
}
