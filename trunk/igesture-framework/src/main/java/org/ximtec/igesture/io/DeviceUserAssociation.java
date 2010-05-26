package org.ximtec.igesture.io;

import org.ximtec.igesture.core.DefaultDataObject;
import org.ximtec.igesture.util.Constant;

/**
 * This class defines an association between a {@link org.ximtec.igesture.io.IUser} and a {@link org.ximtec.igesture.io.AbstractGestureDevice}. 
 * Both objects are saved in the association. It extends {@link org.ximtec.igesture.core.DefaultDataObject} so the association can be serialized.
 * It also implements the {@link org.ximtec.igesture.io.Device} interface.
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class DeviceUserAssociation extends DefaultDataObject //implements Device
{

	private AbstractGestureDevice<?,?> device;
	private IUser user;
	
	/**
	 * Constructor
	 * @param device	The device.
	 * @param user		The user to associate with the device.
	 */
	public DeviceUserAssociation(AbstractGestureDevice<?,?> device, IUser user)
	{
		this.device = device;
		this.user = user;
	}

//	@Override
	public void connect() {
		device.connect();
	}

//	@Override
	public void disconnect() {
		device.disconnect();
	}

//	@Override
	public String getConnectionType() {
		return Constant.getConnectionTypeName(device.getConnectionType());
	}

//	@Override
	public String getDeviceID() {
		return device.getDeviceID();
	}

//	@Override
	public String getDeviceType() {
		return Constant.getDeviceTypeName(device.getDeviceType());
	}

//	@Override
	public String getName() {
		return device.getName();
	}

//	@Override
//	public boolean hasUniqueDeviceID() {
//		return device.hasUniqueDeviceID();
//	}

//	@Override
	public boolean isConnectable() {
		return device.isConnectable();
	}

//	@Override
	public boolean isDisconnectable() {
		return device.isDisconnectable();
	}

//	@Override
	public void setConnectionType(String connectionType) {
		device.setConnectionType(Constant.getConnectionTypeValue(connectionType));
	}

//	@Override
	public void setDeviceID(String id) {
		device.setDeviceID(id);
	}

//	@Override
//	public void setDeviceType(String deviceType) {
//		device.setDeviceType(Constant.getTypeValue(deviceType));
//	}

//	@Override
	public void setName(String name) {
		device.setName(name);
	}

	/**
	 * Set the associated user.
	 * @param user	The user to associate.
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Get a String representation of the user.
	 * @return String representation of the user.
	 */
	public String getUser()
	{
		return user.toString();
	}
	
	/**
	 * Get the device of this association. 
	 * @return The device.
	 */
	public AbstractGestureDevice<?,?> getDeviceItem()
	{
		return device;
	}
	
	/**
	 * Get the associated User.
	 * @return The associated user.
	 */
	public IUser getUserItem()
	{
		return user;
	}

//	@Override
	public boolean isDefaultDevice() {
		return device.isDefaultDevice();
	}

//	@Override
	public void setDefaultDevice(boolean isDefault) {
		device.setDefaultDevice(isDefault);
	}

//	@Override
	public boolean isConnected() {
		return device.isConnected();
	}

//	@Override
	public void setIsConnected(boolean isConnected) {
		device.setIsConnected(isConnected);
	}
	
	/**
	 * Check whether the device is connected or not.
	 * @return	True if connected, else false.
	 * @see isConnected()
	 */
	public boolean getConnected() {
		return isConnected();
		//for convenience, be able to use reflection.
	}
	
	/**
	 * Change the connection status of the device.
	 * @param isConnected The new connection status.
	 * @see setIsConnected(boolean)
	 */
	public void setConnected(boolean isConnected)
	{
		setIsConnected(isConnected);
		//for convenience, be able to use reflection.
	}
	
	public String toString()
	{
		return device.toString()+" - "+user.toString();
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.view.devicemanager.Device#getDeviceType()
	 */
//	@Override
	public String getDeviceClass() {
		return device.getDeviceClass();
	}

}
