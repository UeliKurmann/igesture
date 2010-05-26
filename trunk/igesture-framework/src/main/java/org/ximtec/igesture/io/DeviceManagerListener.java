/**
 * 
 */
package org.ximtec.igesture.io;


/**
 * @author Björn Puypepuype@gmail.com
 *
 */
public interface DeviceManagerListener {

	public static final int ADD = 0;
	public static final int REMOVE = 1;
	
	public void updateDeviceManagerListener(int operation, AbstractGestureDevice<?,?> device);
}
