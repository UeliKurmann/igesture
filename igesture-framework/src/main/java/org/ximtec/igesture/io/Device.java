package org.ximtec.igesture.io;

/**
 * This interface defines all methods for devices, that can be managed by the device manager, must implement.
 * @author Björn Puypepuype@gmail.com
 *
 */
public interface Device {
	
		// device identifier
	   /**
	    * Get the unique identifier of the device. (e.g. MAC address)
	    */
		String getDeviceID();
		/**
		 * Set the unique identifier of the device. (e.g. MAC address)
		 * @param id
		 */
		void setDeviceID(String id);
		/**
		 * If the device can be uniquely identified by for example a MAC address, this function returns true else false.
		 * @return
		 */
		boolean hasUniqueDeviceID();
		
		// display name
		/**
		 * Get the display name for the device.
		 */
		String getName();
		/**
		 * Set the display name for the device.
		 * @param name
		 */
		void setName(String name);
		
		//allow management of the connection with the device
		/**
		 * Disconnect the device.
		 */
		void disconnect();
		/**
		 * Connect the device.
		 */
		void connect();
		/**
		 * Check whether the device can be connected by the user or is automatically connected.
		 * @return
		 */
		boolean isConnectable();//TODO remove
		/**
		 * Check whether the device can be disconnected by the user.
		 * @return
		 */
		boolean isDisconnectable();//TODO remove
		/**
		 * Check whether the device is connected or not.
		 * @return	True if connected, else false.
		 */
		boolean isConnected();
		/**
		 * Change the connection status of the device.
		 * @param isConnected	The new connection status.
		 */
		void setIsConnected(boolean isConnected);
		
		//type of the device, e.g. 2D, 3D
		/**
		 * Get the type of the device. (E.g. 2D or 3D, voice)
		 */
		String getDeviceType();
		/**
		 * Set the type of the device. (E.g. 2D or 3D, voice)
		 * @param deviceType
		 */
		void setDeviceType(String deviceType);
		
		//type of the connection, e.g. USB, BlueTooth
		/**
		 * Get the connection type of the device. (E.g. USB, BlueTooth)
		 */
		String getConnectionType();
		/**
		 * Set the connection type of the device. (E.g. USB, BlueTooth)
		 * @param connectionType
		 */
		void setConnectionType(String connectionType);
		
		/**
		 * Check whether the device is the default device.
		 * @return
		 */
		boolean isDefaultDevice();
		/**
		 * Set the default device status of the device.
		 * @param isDefault
		 */
		void setDefaultDevice(boolean isDefault);	
		
		
		String toString();

		/**
		 * Get the device type (E.g. SwingMouseReader, WiiReader, ...)
		 * @return the device type.
		 */
		String getDeviceClass();
}
