package org.ximtec.igesture.tool.view.devicemanager;

import java.util.Collection;

/**
 * This interface defines the methods the view of the device manager should support.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public interface IDeviceManagerView {

	/**
	 * Add a user.
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * Add a association between a device and a user.
	 * @param association
	 */
	public void addDevice(DeviceUserAssociation association);

	/**
	 * Remove a device and the corresponding association.
	 */
	public void removeDevice();

	/**
	 * Remove a user
	 */
	public void removeUser();
	
	/**
	 * Get all device-user associations.
	 * @return A collection of DeviceUserAssociation.
	 */
	public Collection<DeviceUserAssociation> getDevices();
	
	/**
	 * Update a device-user association.
	 * @param value		The new value.
	 * @param column	The field that should be updated.
	 * @param object	The object that should be updated, when null the currently selected item is updated.
	 */
	public void updateDevice(Object value, int column, DeviceUserAssociation object);
	
	/**
	 * Get the currently selected device-user association.
	 * @return The selected association.
	 */
	public DeviceUserAssociation getSelectedDevice();
	
	/**
	 * Get the currently selected user.
	 * @return The selected user.
	 */
	public User getSelectedUser();

}
