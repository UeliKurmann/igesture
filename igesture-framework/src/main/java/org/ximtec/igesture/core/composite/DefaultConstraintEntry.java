/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.Set;

import org.ximtec.igesture.core.DefaultDataObject;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class DefaultConstraintEntry extends DefaultDataObject{

	private String gesture;
	private int user = -1;
	private String deviceType = null;
	private Set<String> devices = null;
	
	/**
	 * Constructor, all users and all device are allowed to perform this gesture
	 * @param gesture	the gesture
	 */
	public DefaultConstraintEntry(String gesture)
	{
		this.gesture = gesture;
	}
	
	/**
	 * Constructor, all devices are allowed to perform this gesture
	 * @param gesture	the gesture
	 * @param user		the user that performs the gesture
	 */
	public DefaultConstraintEntry(String gesture, int user)
	{
		this.gesture = gesture;
		this.user = user;
	}
	
	/**
	 * Constructor, all users are allowed to perform this gesture
	 * @param gesture	the gesture
	 * @param deviceType	the device type used to perform the gestures
	 * @param devices	the specific devices that may be used to perform the gesture, specify null to allow all devices that are of type deviceType
	 */
	public DefaultConstraintEntry(String gesture, String deviceType, Set<String> devices)
	{
		this.gesture = gesture;
		if(deviceType != null && deviceType != "")
		{
			this.deviceType = deviceType;
			if(devices != null && !devices.isEmpty())
				this.devices = devices;
		}
	}
	
	/**
	 * Constructor
	 * @param gesture	the gesture
	 * @param user		the user that performs the gesture
	 * @param deviceType	the device type used to perform the gestures
	 * @param devices	the specific devices that may be used to perform the gesture, specify null to allow all devices that are of type deviceType
	 */
	public DefaultConstraintEntry(String gesture, int user, String deviceType, Set<String> devices)
	{
		this(gesture,deviceType,devices);
		this.user = user;
	}

	/**
	 * @return the gesture
	 */
	public String getGesture() {
		return gesture;
	}

	/**
	 * @return the user
	 */
	public int getUser() {
		return user;
	}

	/**
	 * @return the device type
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @return the specific devices
	 */
	public Set<String> getDevices() {
		return devices;
	}
	
	
	
}
