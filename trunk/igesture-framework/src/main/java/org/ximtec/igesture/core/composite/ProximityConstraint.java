/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ximtec.igesture.core.composite.parameter.ProximityConstraintParameters;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public abstract class ProximityConstraint extends DefaultConstraint {
	
	private static final Logger LOGGER = Logger.getLogger(ProximityConstraint.class.getName());
	private static final String ERROR_MESSAGE_NO_TYPE = "The device type must be specified for a proximity based constraint in order\n" +
			"to ensure that the coordinates of the gestures can be logically compared.\n" +
			"Please select a device type.";
	private static final String LOGGER_MESSAGE_NO_TYPE = "Proximity based constraints must specify the device type. " +
			"The gesture class was not added.";
	
	private static final String ERROR_MESSAGE_WRONG_TYPE = "The gestures created by a device of the specified type cannot be logically compared\n" +
			"with the other gestures in the constraint!\n" +
			"Please specify a device which creates gestures of type: ";
	private static final String LOGGER_MESSAGE_WRONG_TYPE = "The gesture type (e.g. 2D, 3D) of all composing gestures must match. The gesture class was not added.";
	

	public ProximityConstraint() {
		addConstraintParameters(new ProximityConstraintParameters());
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String)
	 */
	@Override
	public void addGestureClass(String gestureClass) {
		IllegalArgumentException e = new IllegalArgumentException(ERROR_MESSAGE_NO_TYPE);
		LOGGER.log(Level.SEVERE,LOGGER_MESSAGE_NO_TYPE,e);
		throw e;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, int)
	 */
	@Override
	public void addGestureClass(String gestureClass, int user) {
		IllegalArgumentException e = new IllegalArgumentException(ERROR_MESSAGE_NO_TYPE);
		LOGGER.log(Level.SEVERE,LOGGER_MESSAGE_NO_TYPE,e);
		throw e;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, java.lang.String, java.util.Set<String>)
	 */
	@Override
	public void addGestureClass(String gestureClass, String deviceType, Set<String> devices) {
		String myGestureType = ConstraintTool.getGestureType(deviceType);
		
		if(gestures.isEmpty() || (!gestures.isEmpty() && ConstraintTool.getGestureType(gestures.get(0).getDeviceType()).equals(myGestureType)))
		{
			DefaultConstraintEntry entry = new DefaultConstraintEntry(gestureClass, deviceType, devices); 
			gestures.add(entry);
			propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_GESTURES, gestures.indexOf(entry), null, entry);
		}
		else
		{
			IllegalArgumentException e = new IllegalArgumentException(ERROR_MESSAGE_WRONG_TYPE+ConstraintTool.getGestureType(gestures.get(0).getDeviceType()));
			LOGGER.log(Level.SEVERE,LOGGER_MESSAGE_WRONG_TYPE,e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, int, java.lang.String, java.util.Set<String>)
	 */
	@Override
	public void addGestureClass(String gestureClass, int user, String deviceType, Set<String> devices) {
		String myGestureType = ConstraintTool.getGestureType(deviceType);
		
		if(gestures.isEmpty() || (!gestures.isEmpty() && ConstraintTool.getGestureType(gestures.get(0).getDeviceType()).equals(myGestureType)))
		{
			DefaultConstraintEntry entry = new DefaultConstraintEntry(gestureClass, deviceType, devices);
			gestures.add(entry);
			propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_GESTURES, gestures.indexOf(entry), null, entry);
		}
		else
		{
			IllegalArgumentException e = new IllegalArgumentException(ERROR_MESSAGE_WRONG_TYPE+ConstraintTool.getGestureType(gestures.get(0).getDeviceType()));
			LOGGER.log(Level.SEVERE,LOGGER_MESSAGE_WRONG_TYPE,e);
			throw e;
		}
	}
	
	public String toString()
	{
		return ProximityConstraint.class.getSimpleName();
	}
	
}
