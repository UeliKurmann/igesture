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

	private String id;

	protected PropertyChangeSupport propertyChangeSupport;

	/**
	 * Constructs a new default data object.
	 */
	public DefaultDataObject() {
		this.id = StorageManager.generateUUID();
		this.propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public void setId(String objectID) {
		this.id = objectID;
	} // setID

	public String getId() {
		if (id == null) {
			id = StorageManager.generateUUID();
		}

		return id;
	} // getID

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

}
