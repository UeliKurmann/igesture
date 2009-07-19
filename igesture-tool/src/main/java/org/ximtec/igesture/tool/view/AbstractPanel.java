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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.explorer.DefaultExplorerTreeView;
import org.ximtec.igesture.tool.util.ComponentFactory;

public abstract class AbstractPanel extends DefaultExplorerTreeView {

	private JScrollPane centerPane;
	private Controller controller;

	public AbstractPanel(Controller controller) {
		this.controller = controller;
		if(controller == null){
		  throw new RuntimeException("Controller must not be null.");
		}
		setLayout(new BorderLayout());
		centerPane = new JScrollPane();
		centerPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setBackground(Color.LIGHT_GRAY);
		setOpaque(true);
		this.add(centerPane, BorderLayout.CENTER);
	}

	public void setTitle(JComponent component) {
		this.add(component, BorderLayout.NORTH);
	}

	public void setCenter(JComponent component) {
		component.setBackground(Color.white);
		component.setOpaque(true);
		centerPane.setViewportView(component);
	}

	public void setBottom(JComponent component) {
		this.add(component, BorderLayout.SOUTH);
	}
	
	/**
	 * Returns the controller
	 * @return the controller
	 */
	protected Controller getController(){
		return this.controller;
	}
	
	/**
	 * Returns the component factory
	 * @return the component factory
	 */
	protected ComponentFactory getComponentFactory(){
		return controller.getLocator().getService(ComponentFactory.class.getName(), ComponentFactory.class);
	}

	@Override
	public void refresh() {
	  
	}

}
