/**
 * 
 */
package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import org.ximtec.igesture.core.composite.CompositeDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.LocateableAction;
import org.ximtec.igesture.tool.view.admin.panel.CompositeDescriptorPanel;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class RemoveAllGestureClassesFromConstraintAction extends
		LocateableAction {

	private CompositeDescriptor descriptor;
	private CompositeDescriptorPanel panel;
	
	public RemoveAllGestureClassesFromConstraintAction(Controller controller, CompositeDescriptorPanel panel, CompositeDescriptor descriptor)
	{
		super(GestureConstants.REMOVE_ALL_GESTURE_CLASSES_FROM_CONSTRAINT, controller.getLocator());
		this.panel = panel;
		this.descriptor = descriptor;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		panel.removeAllGestureClasses();
		descriptor.getConstraint().removeAllGestureClasses();
	}

}
