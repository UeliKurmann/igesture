package org.ximtec.igesture.tool.core;

import javax.swing.JFrame;

import org.ximtec.igesture.tool.util.ComponentFactory;

public class DefaultView extends JFrame{

	
	
	private Controller controller;

	public DefaultView(Controller controller){
		this.controller = controller;
		if(this.controller == null){
			throw new RuntimeException("Controller must not be null.");
		}
	}
	
	/**
	 * Returns the controller instance of the view
 	 * @return the controller of the  view
	 */
	public Controller getController() {
		return controller;
	}
	
	/**
	 * Returns the component factory
	 * @return the component factory
	 */
	public ComponentFactory getComponentFactory(){
		return controller.getLocator().getService(ComponentFactory.class.getName(), ComponentFactory.class);
	}
}
