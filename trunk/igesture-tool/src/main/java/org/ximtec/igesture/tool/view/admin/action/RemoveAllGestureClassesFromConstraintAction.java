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
public class RemoveAllGestureClassesFromConstraintAction extends
		LocateableAction {

	private CompositeDescriptorPanel panel;
	
	public RemoveAllGestureClassesFromConstraintAction(Controller controller, CompositeDescriptorPanel panel)
	{
		super(GestureConstants.REMOVE_ALL_GESTURE_CLASSES_FROM_CONSTRAINT, controller.getLocator());
		this.panel = panel;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		panel.removeAllGestureClasses();
	}

}
