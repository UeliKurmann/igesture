/**
 * 
 */
package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor3D;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class AddSampleDescriptor3DAction extends TreePathAction {

	public AddSampleDescriptor3DAction(Controller controller, TreePath treePath) {
		super(GestureConstants.SAMPLE_DESCRIPTOR_3D_ADD, controller, treePath);	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		GestureClass gestureClass = (GestureClass) getTreePath().getLastPathComponent();
		gestureClass.addDescriptor(new SampleDescriptor3D());
	}

}
