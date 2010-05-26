package org.ximtec.igesture.io;

/**
 * This interface defines the methods users must implement.
 * @author Bjorn Puype, bpuype@gmail.com
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
	 * Get a String representation of the user.
	 * @return	String representation of the user.
	 */
	String toString();
}
