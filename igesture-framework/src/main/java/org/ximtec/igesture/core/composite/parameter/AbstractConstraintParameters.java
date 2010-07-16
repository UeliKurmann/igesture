/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ximtec.igesture.core.DefaultDataObject;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.composite.DefaultConstraintEntry;
import org.ximtec.igesture.io.IDeviceManager;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public abstract class AbstractConstraintParameters extends DefaultDataObject implements IConstraintParameter {

	protected Map<String, String> DEFAULT_CONFIGURATION = new HashMap<String, String>();
	protected Map<String, String> setterMapping = new HashMap<String, String>();
	
	public static final String PROPERTY_CONSTRAINT_PARAMETER_VALUE = "constraintParameterValue";
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.IConstraintParameter#getParameters()
	 */
	@Override
	public Map<String, String> getParameters() {
		return DEFAULT_CONFIGURATION;
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.IConstraintParameter#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String property)
	{
		return DEFAULT_CONFIGURATION.get(property);
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.IConstraintParameter#setParameter(java.lang.String, java.lang.String)
	 */
	@Override
	public void setParameter(String property, String value)
	{
		DEFAULT_CONFIGURATION.put(property, value);
		Class<?> clazz = this.getClass();
		try {
			Method m = clazz.getMethod(setterMapping.get(property), String.class);//no getDeclaredMethod because setGestureTime in DefaultConstraint
			m.invoke(this, value);
			
			propertyChangeSupport.firePropertyChange(PROPERTY_CONSTRAINT_PARAMETER_VALUE, null, value);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.IConstraintParameter#validateConditions(java.util.List, org.ximtec.igesture.io.IDeviceManager)
	 */
	public abstract boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager);
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.IConstraintParameter#generatePatterns(java.util.Map)
	 */
	public abstract Set<String> generatePatterns(Map<String, String> charMapping, List<DefaultConstraintEntry> gestures);

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.IConstraintParameter#determineTimeWindows()
	 */
	public abstract Map<String, Calendar> determineTimeWindows(List<DefaultConstraintEntry> gestures);
}
