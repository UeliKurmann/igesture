/**
 * 
 */
package org.ximtec.igesture.core.composite;

import org.ximtec.igesture.core.composite.parameter.ConcurrencyConstraintParameters;

/**
 * This class represents a concurrency constraint, all gestures must be performed in parallel. This means all gestures must
 * have an overlapping time interval in common.
 * 
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class ConcurrencyConstraint extends DefaultConstraint {
	
	public ConcurrencyConstraint()
	{
		super();
		addConstraintParameters(new ConcurrencyConstraintParameters());
	}
		
	public String toString()
	{
		return ConcurrencyConstraint.class.getSimpleName();
	}
	
}
