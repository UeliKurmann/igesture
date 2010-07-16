/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.logging.Logger;

import org.ximtec.igesture.core.composite.parameter.ConcurrencyConstraintParameters;

/**
 * This class represents a two-fold constraint. First the gestures have to be performed concurrently and secondly, they have 
 * to be performed in each others vicinity. The minimum and maximum distance has to be defined. This distance is the diagonal
 * of the rectangular bounding box of the gestures for 2D gestures, for 3D gestures it is the diagonal of each facet of a 
 * cubic bounding space.
 * 
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class ProximityConcurrencyConstraint extends ProximityConstraint{

	private static final Logger LOGGER = Logger.getLogger(ProximityConcurrencyConstraint.class.getName());

	public ProximityConcurrencyConstraint() {
		super();
		addConstraintParameters(new ConcurrencyConstraintParameters());
	}
	
	public String toString()
	{
		return ProximityConcurrencyConstraint.class.getSimpleName();
	}
}
