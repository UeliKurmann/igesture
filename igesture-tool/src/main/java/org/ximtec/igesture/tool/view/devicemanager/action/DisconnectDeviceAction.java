/**
 * 
 */
package org.ximtec.igesture.tool.view.devicemanager.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerView;
import org.ximtec.igesture.tool.view.devicemanager.DeviceUserAssociation;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class DisconnectDeviceAction extends BasicAction implements
		ListSelectionListener {

	private DeviceManagerView view;
	private ArrayList<BasicAction> actions;
	
	public DisconnectDeviceAction(DeviceManagerController controller, DeviceManagerView view)
	{
		this(controller,view,false);
	}
	
	public DisconnectDeviceAction(DeviceManagerController controller, DeviceManagerView view, boolean enabled)
	{
		super(GestureConstants.DISCONNECT_DEVICE, controller.getLocator().getService(
	            GuiBundleService.IDENTIFIER, GuiBundleService.class));
		this.view = view;
		setEnabled(enabled);
		
		actions = new ArrayList<BasicAction>();
	}
	
	public void addAction(BasicAction action)
	{
		actions.add(action);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//disconnect device
		view.getSelectedDevice().disconnect();
		if(!view.getSelectedDevice().isConnected())
		{
			//update model
			view.updateDevice(false, DeviceManagerView.COL_DEVICE_CONNECTED, null);
			setEnabled(false);
			for(BasicAction action : actions)
				action.setEnabled(true);
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
			DeviceUserAssociation device = view.getSelectedDevice();
			if(device != null && device.isConnected() && !device.isDefaultDevice())
			{
				//enable this action when the device is connected
				setEnabled(true);
			}
			else
			{
				//otherwise disable it.
				setEnabled(false);
			}
	}

}
