/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose      :   The controller interface to be implemented by any
 *                  controller component. 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 23.03.2008       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.core;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.ximtec.igesture.tool.locator.Locator;

/**
 * The controller interface to be implemented by any controller component.
 * 
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface Controller extends PropertyChangeListener {

	/**
	 * Adds a controller to this controller.
	 * 
	 * @param controller
	 *            the controller to be added.
	 */
	void addController(Controller controller);

	/**
	 * Removes a controller from this controller.
	 * 
	 * @param controller
	 *            the controller to be removed.
	 */
	void removeController(Controller controller);

	/**
	 * Removes all controllers.
	 */
	void removeAllControllers();

	/**
	 * Returns a list of all controllers.
	 */
	List<Controller> getControllers();

	/**
	 * Returns the view component for this specific controller.
	 * 
	 * @return the view component for this specific controller.
	 */
	TabbedView getView();

	/**
	 * Executes a given command.
	 * 
	 * @param command
	 *            the command to be executed.
	 */
	void execute(Command command);

	/**
	 * Returns the parent controller. Each controller has a maximum of 1 parent
	 * controller. If the controller does not have a parent, it is the root
	 * controller of the application.
	 * 
	 * @return the parent controller.
	 */
	Controller getParent();

	/**
	 * Sets the parent controller. See getParent().
	 * 
	 * @param controller
	 *            the parent controller
	 */
	void setParent(Controller controller);
	
	/**
	 * Returns the action instance of the given class
	 * @param action
	 * @return
	 */
	public LocateableAction getAction(Class<?> action);
	
	/**
	 * Returns the applications locator
	 * @return the applications locator
	 */
	public Locator getLocator();

}
