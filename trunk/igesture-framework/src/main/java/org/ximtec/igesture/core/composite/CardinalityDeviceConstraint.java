/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.IDeviceManager;

/**
 * This class represents a cardinality constraint. A certain gesture has to be performed between a mimimum and a maximum number 
 * of times within a certain time interval. The main example for this kind of constraint is defining a majority. 
 * Each device has only one vote.
 * 
 * @author Björn Puypepuype@gmail.com
 *
 */
public class CardinalityDeviceConstraint extends IntervalConstraint {

	private static final Logger LOGGER = Logger.getLogger(CardinalityUserConstraint.class.getName());
	private static final String ERROR_MESSAGE = "A cardinality based constraint can only contain one gesture class.\n" +
										"Please remove the existing gesture class from the constraint before adding a new one.";
	private static final String LOGGER_MESSAGE = "A cardinality based constraint can only contain one gesture class. " +
										"The gesture class was not added.";
	private static final String ERROR_MESSAGE_USER = "The user cannot be specified for a cardinality based constraint.\n" +
			"The user field was not saved";
	private static final String LOGGER_MESSAGE_USER = "The user cannot be specified for a cardinality based constraint.\n" +
			"The gesture class was added without the user field.";
	
	public enum Config{
		MIN_GESTURES, MAX_GESTURES
	}
	
	private static final String MIN_GESTURES = "2";
	private static final String MAX_GESTURES = "2";
	
	private int minGestures;
	private int maxGestures;

	public CardinalityDeviceConstraint() {
		super();
		DEFAULT_CONFIGURATION.put(Config.MIN_GESTURES.name(), MIN_GESTURES);
		DEFAULT_CONFIGURATION.put(Config.MAX_GESTURES.name(), MAX_GESTURES);
		setterMapping.put(Config.MIN_GESTURES.name(), "setMinimumGestures");
		setterMapping.put(Config.MAX_GESTURES.name(), "setMaximumGestures");
		setMinimumGestures(MIN_GESTURES);
		setMaximumGestures(MAX_GESTURES);
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String)
	 */
	@Override
	public void addGestureClass(String gestureClass) {
		if(gestures.isEmpty())
		{
			DefaultConstraintEntry entry = new DefaultConstraintEntry(gestureClass); 
			gestures.add(entry);
			propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_GESTURES, gestures.indexOf(entry), null, entry);
		}
		else
		{
			IllegalArgumentException e = new IllegalArgumentException(ERROR_MESSAGE);
			LOGGER.log(Level.SEVERE,LOGGER_MESSAGE,e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, int)
	 */
	@Override
	public void addGestureClass(String gestureClass, int user) {
		if(gestures.isEmpty())
		{
			DefaultConstraintEntry entry = new DefaultConstraintEntry(gestureClass);
			gestures.add(entry);
			propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_GESTURES, gestures.indexOf(entry), null, entry);
			IllegalArgumentException e = new IllegalArgumentException(ERROR_MESSAGE_USER);
			LOGGER.log(Level.WARNING,LOGGER_MESSAGE_USER,e);
			throw e;
		}
		else
		{
			IllegalArgumentException e = new IllegalArgumentException(ERROR_MESSAGE);
			LOGGER.log(Level.SEVERE,LOGGER_MESSAGE,e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, java.lang.String, java.util.Set<String>)
	 */
	@Override
	public void addGestureClass(String gestureClass, String deviceType, Set<String> devices) {
		if(gestures.isEmpty())
		{
			DefaultConstraintEntry entry = new DefaultConstraintEntry(gestureClass, deviceType, devices); 
			gestures.add(entry);
			propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_GESTURES, gestures.indexOf(entry), null, entry);
			IllegalArgumentException e = new IllegalArgumentException(ERROR_MESSAGE_USER);
			LOGGER.log(Level.WARNING,LOGGER_MESSAGE_USER,e);
			throw e;
		}
		else
		{
			IllegalArgumentException e = new IllegalArgumentException(ERROR_MESSAGE);
			LOGGER.log(Level.SEVERE,LOGGER_MESSAGE,e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, int, java.lang.String, java.util.Set<String>)
	 */
	@Override
	public void addGestureClass(String gestureClass, int user, String deviceType, Set<String> devices) {
		if(gestures.isEmpty())
		{
			DefaultConstraintEntry entry = new DefaultConstraintEntry(gestureClass, deviceType, devices);
			gestures.add(entry);
			propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_GESTURES, gestures.indexOf(entry), null, entry);
		}
		else
		{
			IllegalArgumentException e = new IllegalArgumentException(ERROR_MESSAGE);
			LOGGER.log(Level.SEVERE,LOGGER_MESSAGE,e);
			throw e;
		}
	}

	/**
	 * Set the minimum number of gestures to perform
	 */
	public void setMinimumGestures(String min) throws NumberFormatException
	{
		minGestures = Integer.parseInt(min);
	}
	
	/**
	 * Get the minimum number of gestures that has to be performed
	 */
	public int getMinimumGestures()
	{
		return minGestures;
	}
	
	/**
	 * Set the maximum number of gestures to perform
	 */
	public void setMaximumGestures(String max) throws NumberFormatException
	{
		maxGestures = Integer.parseInt(max);
	}
	
	/**
	 * Get the maximum number of gestures that has to be performed
	 */
	public int getMaximumGestures()
	{
		return maxGestures;
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.DefaultConstraint#generatePatterns(java.util.Map)
	 */
	@Override
	public Set<String> generatePatterns(Map<String, String> charMapping) {
		Set<String> patterns = new HashSet<String>();
		
		String key = gestures.get(0).getGesture();
		
		for (int i = minGestures; i <= maxGestures; i++) {
			StringBuilder builder = new StringBuilder();
			for(int j = 0; j < i; j++)
			{
				builder.append(charMapping.get(key));
			}
			patterns.add(builder.toString());
		}
		
		return patterns;
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
