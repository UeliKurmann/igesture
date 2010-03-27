package org.ximtec.igesture.tool.view.devicemanager;

/**
 * This interface defines the methods users must implement.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public interface IUser {
	
	/**
	 * Get the name of the user.
	 * @return Name of the user.
	 */
	String getName();
	/**
	 * Set the name of the user.
	 * @param name	Name of the user.
	 */
	void setName(String name);
	
	/**
	 * Get the initials of the user.
	 * @return	Initials of the user.
	 */
	String getInitials();
	/**
	 * Set the initials of the user.
	 * @return	Initials of the user.
	 */
	void setInitials(String initials);
	
	/**
	 * True if this user is a default user of the system. Typically this is the active system user.
	 * Default user cannot be removed from the device manager.
	 * @return
	 */
	boolean isDefaultUser();
	/**
	 * Specify whether this user is the default user or not.
	 * @param isDefault
	 */
	void setDefaultUser(boolean isDefault);
	
	/**
	 * Get a String representation of the user.
	 * @return	String representation of the user.
	 */
	String toString();
}
