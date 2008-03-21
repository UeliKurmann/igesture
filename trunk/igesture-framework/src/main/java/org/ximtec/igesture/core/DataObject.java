/*
 * @(#)DataObject.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Interface to be implemented by persistent-capable
 *                  objects.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Feb 27, 2007     bsigner     Cleanup
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
import java.io.Serializable;

/**
 * Interface to be implemented by persistent-capable objects.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface DataObject extends Serializable {

	/**
	 * Returns the object's universally unique identifier (UUID).
	 * 
	 * @return the object's UUID.
	 */
	public String getId();

	/**
	 * Sets the object's universally unique identifier (UUID).
	 * 
	 * @param id
	 *            the object's UUID.
	 */
	public void setId(String id);

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
	
	/**
	 * Accepts a visitor
	 * @param visitor
	 */
	public void accept(Visitor visitor);

}
