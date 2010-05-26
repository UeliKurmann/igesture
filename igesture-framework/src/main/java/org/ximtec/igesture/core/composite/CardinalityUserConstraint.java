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
import org.ximtec.igesture.io.IDeviceManager;
import org.ximtec.igesture.io.IUser;


/**
 * This class represents a cardinality constraint. A certain gesture has to be performed between a mimimum and a maximum number 
 * of times within a certain time interval. The main example for this kind of constraint is defining a majority. 
 * Each user has only one vote.
 * 
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class CardinalityUserConstraint extends CardinalityDeviceConstraint {

	private static final Logger LOGGER = Logger.getLogger(CardinalityUserConstraint.class.getName());

	public CardinalityUserConstraint() {
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
			
			Set<IUser> users = new HashSet<IUser>();
			
			//check if all gestures were created by different users
			for (Iterator iterator = gestures.iterator(); iterator.hasNext();) {
				Gesture<?> gesture = (Gesture<?>) iterator.next();
				IUser user = manager.getAssociatedUser(gesture.getSource());
				users.add(user);
			}
			
			if(users.size() != gestures.size())
				conditionsValid = false;
		}
		
		return conditionsValid;
	}

	public String toString()
	{
		return CardinalityUserConstraint.class.getSimpleName();
	}
	
}
