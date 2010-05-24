package org.ximtec.igesture.tool.view.devicemanager.wizard;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.netbeans.api.wizard.WizardDisplayer;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardBranchController;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.User;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;

/**
 * A wizard to add devices.
 * @author Björn Puypeype, bpuype@gmail.com
 *
 */
public class AddDeviceWizard {

	private Wizard wizard;
	private WizardBranchController brancher;
	private DeviceManagerController controller;
	
	public AddDeviceWizard(DeviceManagerController controller, boolean enableRecognisers)
	{
		if(!enableRecognisers)
		{
			AddDeviceWizardProvider provider = new AddDeviceWizardProvider(controller);
			wizard = provider.createWizard();
		}
		else
		{
			AddDeviceWizardBranchController brancher = new AddDeviceWizardBranchController(controller);
			wizard = brancher.createWizard();
		}
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
