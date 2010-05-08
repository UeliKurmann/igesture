package org.ximtec.igesture.tool.view.devicemanager.action;

import java.awt.event.ActionEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.io.IUser;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerView;

/**
 * Action to remove a user. It extends {@link org.sigtec.graphix.widget.BasicAction} and implements the {@link javax.swing.event.ListSelectionListener} interface.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class RemoveUserAction extends BasicAction implements ListSelectionListener{

	private DeviceManagerController controller;
	private DeviceManagerView view;
	
	public RemoveUserAction(DeviceManagerController controller, DeviceManagerView view)
	{
		this(controller,view,false);
	}
	
	public RemoveUserAction(DeviceManagerController controller, DeviceManagerView view, boolean enabled)
	{
		super(GestureConstants.REMOVE_USER, controller.getLocator().getService(
	            GuiBundleService.IDENTIFIER, GuiBundleService.class));
		this.controller = controller;
		this.view = view;
		setEnabled(enabled);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		IUser user = view.getSelectedUser();
		controller.removeUser(user);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		//enable the action when something was selected. Except if the user is a default user.
		IUser user = view.getSelectedUser();
		IUser defaultUser = controller.getDefaultUser();
		if( ( user != null ) && !user.equals(defaultUser) )
			setEnabled(true);
		else
			setEnabled(false);
	}

}
