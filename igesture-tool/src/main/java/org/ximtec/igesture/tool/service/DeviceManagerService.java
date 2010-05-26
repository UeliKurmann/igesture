/**
 * 
 */
package org.ximtec.igesture.tool.service;

import org.sigtec.graphix.GuiBundle;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.locator.Service;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;

/**
 * @author Björn Puypepuype@gmail.com
 *
 */
public class DeviceManagerService extends DeviceManagerController implements
		Service {

	public static final String IDENTIFIER = "deviceManagerService";
	
	/**
	 * @see org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController#DeviceManagerController(Controller, String, GuiBundle)
	 */
	public DeviceManagerService(Controller parentController, String key,
			GuiBundle guiBundle) {
		super(parentController, key, guiBundle);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.locator.Service#getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

}
