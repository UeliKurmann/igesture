package org.ximtec.igesture.tool.view.devicemanager.action;

import java.awt.event.ActionEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.io.DeviceUserAssociation;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerView;

/**
 * Action to remove a device. It extends {@link org.sigtec.graphix.widget.BasicAction} and implements the {@link javax.swing.event.ListSelectionListener} interface.
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class RemoveDeviceAction extends BasicAction implements ListSelectionListener{

	private DeviceManagerController controller;
	private DeviceManagerView view;
	
	public RemoveDeviceAction(DeviceManagerController controller, DeviceManagerView view)
	{
		this(controller,view,false);
	}
	
	public RemoveDeviceAction(DeviceManagerController controller, DeviceManagerView view, boolean enabled)
	{
		super(GestureConstants.REMOVE_DEVICE, controller.getLocator().getService(
	            GuiBundleService.IDENTIFIER, GuiBundleService.class));
		this.controller = controller;	
		this.view = view;
		setEnabled(enabled);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DeviceUserAssociation device = view.getSelectedDevice();
		device.getDeviceItem().disconnect();
		controller.removeDevice(device.getDeviceItem());	
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		DeviceUserAssociation ass = view.getSelectedDevice();
		//enable the action when something was selected. Except if it is a default device
		if((ass != null) && (!ass.isDefaultDevice()))
			setEnabled(true);
		else
			setEnabled(false);
		
	}

}
