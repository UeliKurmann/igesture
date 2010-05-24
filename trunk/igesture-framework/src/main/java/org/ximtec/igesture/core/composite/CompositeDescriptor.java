/**
 * 
 */
package org.ximtec.igesture.core.composite;

import org.ximtec.igesture.core.DefaultDescriptor;

/**
 * @author Björn Puypeype, bpuype@gmail.com
 *
 */
public class CompositeDescriptor extends DefaultDescriptor {
	
	private Constraint constraint;

	public CompositeDescriptor()
	{
		
	}
	
	public CompositeDescriptor(Constraint constraint)
	{
		this.constraint = constraint;
	}
	
	public void setConstraint(Constraint constraint)
	{
		this.constraint = constraint;
	}
	
	public Constraint getConstraint()
	{
		return constraint;
	}
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName();
    }
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.DefaultDescriptor#getName()
	 */
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

}
