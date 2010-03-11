package org.ximtec.igesture.tool.view.devicemanager;

import java.awt.Point;
import java.io.File;
import java.util.Map;
import java.util.Set;

import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.tool.view.devicemanager.discoveryservice.DeviceDiscoveryService;

/**
 * This interface defines the methods the controller of the device manager should support.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public interface IDeviceManager {

	/**
	 * Add a user.
	 * @param user
	 */
	void addUser(User user);
	/**
	 * Remove a user
	 */
	void removeUser(User user);
	
	/**
	 * Add a device and associate it with a user.
	 * @param device	The device to add.
	 * @param user	The user to associate with the device.
	 */
	void addDevice(AbstractGestureDevice<?,?> device, User user);
	/**
	 * Remove a device.
	 * @param device	The device to remove.
	 */
	void removeDevice(AbstractGestureDevice<?,?> device);
	
	/**
	 * Associate a user with a device.
	 * @param device 	The device.
	 * @param user		The user to associate with the device.
	 */
	void associateUser(AbstractGestureDevice<?,?> device, User user);
	
	/**
	 * Get the default user of the system (aka system user).
	 * @return	default user.
	 */
	User getDefaultUser();
	
	/**
	 * Get all devices registered with the controller.
	 * @return	A set of devices.
	 */
	Set<AbstractGestureDevice<?,?>> getDevices();
	/**
	 * Get all users registered with the controller.
	 * @return	A set of users.
	 */
	Set<User> getUsers();
	
	/**
	 * Show the view at a certain point.
	 * @param p	The desired point
	 */
	void showView(Point p);
	
	/**
	 * Get the mapping between connection types and discovery services.
	 * @return
	 */
	Map<String, DeviceDiscoveryService> getDiscoveryMapping();
	
	/**
	 * Save the current user and device configuration to file.
	 * @param file	The file to save to.
	 */
	void saveConfiguration(File file);
	
	/**
	 * Load a user and device configuration from file.
	 * @param file	The file to load from.
	 */
	void loadConfiguration(File file);
	
}
