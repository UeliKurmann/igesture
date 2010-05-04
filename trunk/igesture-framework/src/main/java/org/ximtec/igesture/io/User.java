package org.ximtec.igesture.io;

import org.ximtec.igesture.core.DefaultDataObject;

/**
 * This class represents a User for the device manager.
 * It implements the {@link org.ximtec.igesture.io.IUser} interface and extends {@link org.ximtec.igesture.core.DefaultDataObject}. The latter allows serialization of User objects.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class User extends DefaultDataObject implements IUser {

	private static final long serialVersionUID = 1L;
	private String name;
	private String initials;
	private boolean isDefault;
	
	public User()
	{
		
	}
	
	public User(String name, String initials)
	{
		this.name = name;
		this.initials = initials.toUpperCase();
		this.isDefault = false;
	}

	@Override
	public String getInitials() {
		return initials;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setInitials(String initials) {
		this.initials = initials.toUpperCase();
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return getName()+" ("+getInitials()+")";
	}

	@Override
	public boolean isDefaultUser() {
		return isDefault;
	}

	@Override
	public void setDefaultUser(boolean isDefault) {
		this.isDefault = isDefault;		
	}

}
