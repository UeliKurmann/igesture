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
 * Action to save the user-device configuration to file. It extends {@link org.sigtec.graphix.widget.BasicAction}.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class SaveDeviceConfigurationAction extends BasicAction {

	private DeviceManagerController controller;
	
	public SaveDeviceConfigurationAction(DeviceManagerController controller)
	{
		super(GestureConstants.SAVE_DEVICE_CONFIGURATION, controller.getLocator().getService(
	            GuiBundleService.IDENTIFIER, GuiBundleService.class));
		this.controller = controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setFileFilter(FileType.deviceConfiguration.getFilter());
	    if(fileChooser.showSaveDialog((JButton) e.getSource())==JFileChooser.APPROVE_OPTION)
	    {
		    File selectedFile = fileChooser.getSelectedFile();
		
		    if (selectedFile != null) {
		      controller.saveConfiguration(selectedFile);
		    }
	    }
	}

}
