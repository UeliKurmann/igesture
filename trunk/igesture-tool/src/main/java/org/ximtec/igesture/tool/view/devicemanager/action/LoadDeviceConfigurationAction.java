package org.ximtec.igesture.tool.view.devicemanager.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.util.FileType;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;

/**
 * Action to load a user-device configuration from file. It extends {@link org.sigtec.graphix.widget.BasicAction}.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class LoadDeviceConfigurationAction extends BasicAction {

	private DeviceManagerController controller;
	
	public LoadDeviceConfigurationAction(DeviceManagerController controller)
	{
		super(GestureConstants.LOAD_DEVICE_CONFIGURATION, controller.getLocator().getService(
	            GuiBundleService.IDENTIFIER, GuiBundleService.class));
		this.controller = controller;	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setFileFilter(FileType.deviceConfiguration.getFilter());
	    fileChooser.showOpenDialog((JButton) e.getSource());
	    File selectedFile = fileChooser.getSelectedFile();
	
	    if (selectedFile != null) {
	      controller.loadConfiguration(selectedFile);
	    }

	}

}
