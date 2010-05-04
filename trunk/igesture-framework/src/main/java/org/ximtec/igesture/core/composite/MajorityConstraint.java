/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class represents a majority constraint. A certain gesture has to be performed between a mimimum and a maximum number 
 * of times within a certain time interval. The name is derived from the main example for this kind of constraint, 
 * defining a majority.
 * 
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class MajorityConstraint extends IntervalConstraint {

	private static final Logger LOGGER = Logger.getLogger(MajorityConstraint.class.getName());
	private static final String ERROR_MESSAGE = "A MajorityConstraint can only contain one gesture class.\n" +
										"Please remove the existing gesture class from the constraint before adding a new one.";
	private static final String LOGGER_MESSAGE = "A MajorityConstraint can only contain one gesture class. " +
										"The gesture class was not added.";
	
	
	public enum Config{
		MIN_GESTURES, MAX_GESTURES
	}
	
	private static final String MIN_GESTURES = "2";
	private static final String MAX_GESTURES = "2";
	
	private int minGestures;
	private int maxGestures;

	public MajorityConstraint() {
		super();
		DEFAULT_CONFIGURATION.put(Config.MIN_GESTURES.name(), MIN_GESTURES);
		DEFAULT_CONFIGURATION.put(Config.MAX_GESTURES.name(), MAX_GESTURES);
		setterMapping.put(Config.MIN_GESTURES.name(), "setMinimumGestures");
		setterMapping.put(Config.MAX_GESTURES.name(), "setMaximumGestures");
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String)
	 */
	@Override
	public void addGestureClass(String gestureClass) {
		if(gestures.isEmpty())
			gestures.add(new DefaultConstraintEntry(gestureClass));
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
			gestures.add(new DefaultConstraintEntry(gestureClass, user));
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
			gestures.add(new DefaultConstraintEntry(gestureClass, deviceType, devices));
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
			gestures.add(new DefaultConstraintEntry(gestureClass,user,deviceType,devices));
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

	public String toString()
	{
		return MajorityConstraint.class.getSimpleName();
	}
	
}
