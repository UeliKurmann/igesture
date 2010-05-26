/**
 * 
 */
package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.LocateableAction;
import org.ximtec.igesture.tool.view.admin.panel.CompositeDescriptorPanel;

/**
 * @author Björn Puypepuype@gmail.com
 *
 */
public class AddGestureClassToConstraintAction extends LocateableAction {

	private CompositeDescriptorPanel panel;
	
	public AddGestureClassToConstraintAction(Controller controller, CompositeDescriptorPanel panel)
	{
		super(GestureConstants.ADD_GESTURE_CLASS_TO_CONSTRAINT,controller.getLocator());
		this.panel = panel;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		panel.addGestureClass();
	}

}
