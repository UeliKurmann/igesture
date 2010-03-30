/**
 * 
 */
package org.ximtec.igesture.core;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class CompositeDescriptor extends DefaultDescriptor {

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
