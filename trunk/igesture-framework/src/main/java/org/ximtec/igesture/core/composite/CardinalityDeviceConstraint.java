/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.IDeviceManager;

/**
 * This class represents a cardinality constraint. A certain gesture has to be performed between a mimimum and a maximum number 
 * of times within a certain time interval. The main example for this kind of constraint is defining a majority. 
 * Each device has only one vote.
 * 
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class CardinalityDeviceConstraint extends CardinalityConstraint {

	private static final Logger LOGGER = Logger.getLogger(CardinalityDeviceConstraint.class.getName());

	public CardinalityDeviceConstraint() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.DefaultConstraint#validateConditions(java.util.List)
	 */
	@Override
	public boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager) {
		boolean conditionsValid = super.validateConditions(gestures, manager);
		
		if(conditionsValid) // if previous conditions hold
		{
			
			Set<GestureDevice<?,?>> devs = new HashSet<GestureDevice<?,?>>();
			
			//check if all gestures were created by different devices
			for (Iterator iterator = gestures.iterator(); iterator.hasNext();) {
				Gesture<?> gesture = (Gesture<?>) iterator.next();
				GestureDevice<?,?> dev = gesture.getSource();
				devs.add(dev);
			}
			
			if(devs.size() != gestures.size())
				conditionsValid = false;
		}
		
		return conditionsValid;
	}

	public String toString()
	{
		return CardinalityDeviceConstraint.class.getSimpleName();
	}
}
