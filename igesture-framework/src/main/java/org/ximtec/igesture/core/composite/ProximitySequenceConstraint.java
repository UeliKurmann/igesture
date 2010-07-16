/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.logging.Logger;

import org.ximtec.igesture.core.composite.parameter.SequenceConstraintParameters;

/**
 * This class represents a two-fold constraint. First the gestures have to be performed in sequence and secondly, they have 
 * to be performed in each others vicinity. The minimum and maximum distance has to be defined. This distance is the diagonal
 * of the rectangular bounding box of the gestures for 2D gestures, for 3D gestures it is the diagonal of each facet of a 
 * cubic bounding space.
 * For the time constraint, the minimum and maximum time between two consecutive gestures has to be defined.
 * 
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class ProximitySequenceConstraint extends ProximityConstraint {

	private static final Logger LOGGER = Logger.getLogger(ProximitySequenceConstraint.class.getName());

	public ProximitySequenceConstraint() {
		super();
		addConstraintParameters(new SequenceConstraintParameters());
	}
	
	public String toString()
	{
		return ProximitySequenceConstraint.class.getSimpleName();
	}

}
