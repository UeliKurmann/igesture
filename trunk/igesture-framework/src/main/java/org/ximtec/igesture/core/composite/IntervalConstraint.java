/**
 * 
 */
package org.ximtec.igesture.core.composite;

import org.ximtec.igesture.core.composite.parameter.IntervalConstraintParameters;

/**
 * This class represents an interval constraint. All gestures must be performed within a certain time interval. It does not
 * matter in which order and how they are related in time (e.g. sequential, concurrent or a mix).
 * 
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class IntervalConstraint extends DefaultConstraint {

	public IntervalConstraint() {
		super();
		addConstraintParameters(new IntervalConstraintParameters());
	}

	public String toString()
	{
		return IntervalConstraint.class.getSimpleName();
	}
}
