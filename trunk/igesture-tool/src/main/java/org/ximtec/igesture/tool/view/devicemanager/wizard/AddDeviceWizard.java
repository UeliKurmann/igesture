package org.ximtec.igesture.tool.view.devicemanager.wizard;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.netbeans.api.wizard.WizardDisplayer;
import org.netbeans.spi.wizard.Wizard;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;
import org.ximtec.igesture.tool.view.devicemanager.User;

/**
 * A wizard to add devices.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class AddDeviceWizard {

	private Wizard wizard;
	private DeviceManagerController controller;
	
	public AddDeviceWizard(DeviceManagerController controller)
	{
		AddDeviceWizardProvider provider = new AddDeviceWizardProvider(controller);
		wizard = provider.createWizard();
		this.controller = controller;
	}
	
	/**
	 * Run the wizard
	 */
	public void execute()
	{
		Runnable s = new Runnable(){

			@Override
			public void run() {
				ArrayList<Object> association = (ArrayList<Object>)WizardDisplayer.showWizard (wizard, new Rectangle (10, 10, 600, 400));
				
				//connect to the found device
				if(association != null)//if not cancelled 				//TODO move to finish??
				{
					((AbstractGestureDevice<?,?>)association.get(0)).connect();
					//add the found device
					controller.addDevice((AbstractGestureDevice<?,?>)association.get(0), (User)association.get(1));
				}
				
			}
			
		};
		EventQueue.invokeLater(s);
	}
}
