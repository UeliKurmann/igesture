/**
 * 
 */
package org.ximtec.igesture.tool.view.devicemanager;

import org.ximtec.igesture.io.AbstractGestureDevice;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public interface DeviceManagerListener {

	public static final int ADD = 0;
	public static final int REMOVE = 1;
	
	public void updateDeviceManagerListener(int operation, AbstractGestureDevice<?,?> device);
}
