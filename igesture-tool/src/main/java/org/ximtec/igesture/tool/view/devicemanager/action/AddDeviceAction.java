package org.ximtec.igesture.tool.view.devicemanager.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;
import org.ximtec.igesture.tool.view.devicemanager.wizard.AddDeviceWizard;

/**
 * Action to add a device. It extends {@link org.sigtec.graphix.widget.BasicAction}.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class AddDeviceAction extends BasicAction{

	private DeviceManagerController controller;
	
	public AddDeviceAction(DeviceManagerController controller)
	{
		super(GestureConstants.ADD_DEVICE, controller.getLocator().getService(
	            GuiBundleService.IDENTIFIER, GuiBundleService.class));
		this.controller = controller;	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//create a wizard and execute it.
		AddDeviceWizard wizard = new AddDeviceWizard(controller,true);
		wizard.execute();
		
	}

}
