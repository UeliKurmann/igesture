package org.ximtec.igesture.tool.view.devicemanager.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.io.DeviceUserAssociation;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerView;

/**
 * Action to reconnect a device. It extends {@link org.sigtec.graphix.widget.BasicAction} and implements the {@link javax.swing.event.ListSelectionListener} interface.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class ReconnectDeviceAction extends BasicAction implements ListSelectionListener{

	private static final long serialVersionUID = 1L;
	private DeviceManagerView view;
	private ArrayList<BasicAction> actions;
	
	public ReconnectDeviceAction(DeviceManagerController controller, DeviceManagerView view)
	{
		this(controller,view,false);
	}
	
	public ReconnectDeviceAction(DeviceManagerController controller, DeviceManagerView view, boolean enabled)
	{
		super(GestureConstants.RECONNECT_DEVICE, controller.getLocator().getService(
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
		//connect device
		view.getSelectedDevice().connect();
		if(view.getSelectedDevice().isConnected())
		{
			//update model
			view.updateDevice(true, DeviceManagerView.COL_DEVICE_CONNECTED, null);
			setEnabled(false);
			for(BasicAction action : actions)
				action.setEnabled(true);
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
			DeviceUserAssociation device = view.getSelectedDevice();
			if(device != null && !device.isConnected())
			{
				//enable this action when the device is not connected
				setEnabled(true);
			}
			else
			{
				//otherwise disable it.
				setEnabled(false);
			}
	}

}
