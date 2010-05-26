/**
 * 
 */
package org.ximtec.igesture.tool.view.composite.action;

import java.awt.event.ActionEvent;

import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.LocateableAction;
import org.ximtec.igesture.tool.view.composite.CompositeController;

/**
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class AddRecogniserAction extends LocateableAction {

	private Controller controller;
	
	public AddRecogniserAction(Controller controller)
	{
		super(GestureConstants.COMPOSITE_ADD_RECOGNISER_ACTION,controller.getLocator());
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Command command = new Command(CompositeController.CMD_ADD_RECOGNISER);
		controller.execute(command);
	}

}
