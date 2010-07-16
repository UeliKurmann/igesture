/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ximtec.igesture.core.composite.parameter.CardinalityConstraintParameters;
import org.ximtec.igesture.core.composite.parameter.IConstraintParameter;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public abstract class CardinalityConstraint extends IntervalConstraint {

	private static final Logger LOGGER = Logger.getLogger(CardinalityConstraint.class.getName());
	private static final String ERROR_MESSAGE = "A cardinality based constraint can only contain one gesture class.\n" +
										"Please remove the existing gesture class from the constraint before adding a new one.";
	private static final String LOGGER_MESSAGE = "A cardinality based constraint can only contain one gesture class. " +
										"The gesture class was not added.";
	
	public CardinalityConstraint()
	{
		super();
		addConstraintParameters(new CardinalityConstraintParameters());
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
			IllegalUserArgumentException e = new IllegalUserArgumentException();
			LOGGER.log(Level.WARNING,e.getLoggerMessage(),e);
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
			IllegalUserArgumentException e = new IllegalUserArgumentException();
			LOGGER.log(Level.WARNING,e.getLoggerMessage(),e);
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
	 * @see org.ximtec.igesture.core.composite.DefaultConstraint#generatePatterns(java.util.Map)
	 */
	@Override
	public Set<String> generatePatterns(Map<String, String> charMapping) {
		
		Set<String> patterns = new HashSet<String>();
		
		for (Iterator<IConstraintParameter> iterator = parameters.iterator(); iterator.hasNext();) {
			IConstraintParameter param = iterator.next();
			if(param instanceof CardinalityConstraintParameters)
				patterns = param.generatePatterns(charMapping, gestures);
		}
		return patterns;
	}
}
