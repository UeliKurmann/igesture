/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ximtec.igesture.core.DefaultDataObject;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.composite.parameter.IConstraintParameter;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.IDeviceManager;
import org.ximtec.igesture.io.IUser;
import org.ximtec.igesture.util.MultiValueMap;

/**
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public abstract class DefaultConstraint extends DefaultDataObject implements Constraint {
	
	protected Map<String, String> DEFAULT_CONFIGURATION = new HashMap<String, String>();
	protected Map<String, IConstraintParameter> parameterMapping = new HashMap<String, IConstraintParameter>();
	
	protected List<DefaultConstraintEntry> gestures;
	public static final String PROPERTY_GESTURES = "gestures";
	public static final String PROPERTY_CONSTRAINT_PARAMETER_VALUE = "constraintParameterValue";
	
	protected Set<IConstraintParameter> parameters = new HashSet<IConstraintParameter>(); 
	
	
	public DefaultConstraint()
	{
		gestures = new ArrayList<DefaultConstraintEntry>();	
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addConstraintParameters(org.ximtec.igesture.core.composite.parameter.IConstraintParameter)
	 */
	public void addConstraintParameters(IConstraintParameter param)
	{
		parameters.add(param);
		DEFAULT_CONFIGURATION.putAll(param.getParameters());
		for(String key : param.getParameters().keySet())
		{
			parameterMapping.put(key, param);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String)
	 */
	@Override
	public void addGestureClass(String gestureClass) {
		DefaultConstraintEntry entry = new DefaultConstraintEntry(gestureClass); 
		addGesture(entry);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, int)
	 */
	@Override
	public void addGestureClass(String gestureClass, int user) {
		DefaultConstraintEntry entry = new DefaultConstraintEntry(gestureClass, user);
		addGesture(entry);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, java.lang.String, java.util.Set<String>)
	 */
	@Override
	public void addGestureClass(String gestureClass, String deviceType, Set<String> devices) {
		DefaultConstraintEntry entry = new DefaultConstraintEntry(gestureClass, deviceType, devices); 
		addGesture(entry);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, int, java.lang.String, java.util.Set<String>)
	 */
	@Override
	public void addGestureClass(String gestureClass, int user, String deviceType, Set<String> devices) {
		DefaultConstraintEntry entry = new DefaultConstraintEntry(gestureClass, deviceType, devices);
		addGesture(entry);
	}
	
	private void addGesture(DefaultConstraintEntry entry)
	{
		gestures.add(entry);
		propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_GESTURES, gestures.indexOf(entry), null, entry);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#getGestureClasses()
	 */
	@Override
	public List<String> getGestureClasses() {
		List<String> list = new ArrayList<String>();
		for(DefaultConstraintEntry entry : gestures)
			list.add(entry.getGesture());
		return list;
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#getDistinctGestureClasses()
	 */
	@Override
	public Set<String> getDistinctGestureClasses() {
		Set<String> set = new HashSet<String>();
		set.addAll(getGestureClasses());
		return set;
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#getNumberOfGestures()
	 */
	@Override
	public int getNumberOfGestures() {
		return gestures.size();
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#generatePatterns(java.util.Map)
	 */
	@Override
	public Set<String> generatePatterns(Map<String, String> charMapping)
	{
		Set<String> patterns = new HashSet<String>();
		
		for (Iterator<IConstraintParameter> iterator = parameters.iterator(); iterator.hasNext();) {
			IConstraintParameter param = iterator.next();
			Set<String> subSet = param.generatePatterns(charMapping, gestures);
			if(subSet != null && !subSet.isEmpty())
				patterns.addAll(subSet);
		}
		return patterns;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#determineTimeWindows()
	 */
	@Override
	public Map<String, Calendar> determineTimeWindows()
	{
		Map<String, Calendar> map = new HashMap<String, Calendar>();
		
		for (Iterator<IConstraintParameter> iterator = parameters.iterator(); iterator.hasNext();) {
			IConstraintParameter param = iterator.next();
			Map<String, Calendar> subMap = param.determineTimeWindows(gestures);
			if(subMap != null && !subMap.isEmpty())
			{
				map.putAll(subMap);
			}
		}
		return map;
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#validateConditions(java.util.List)
	 */
	@Override
	public boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager)
	{
		boolean conditionsValid = true;
		Iterator<IConstraintParameter> paramiterator = parameters.iterator();
		while(conditionsValid && paramiterator.hasNext())
		{
			IConstraintParameter param = paramiterator.next();
			conditionsValid = param.validateConditions(gestures, manager);
		}		
		
		//TODO move
		
		Map<Gesture<?>,GestureDevice<?,?>> map = new HashMap<Gesture<?>,GestureDevice<?,?>>();
		
		/* device checks */
		boolean found = true;
		for(Gesture<?> gesture : gestures)
		{
			//get the gesture class of the gesture
			String gestureClass = gesture.getName();
			//get all entries that are of the same gesture class
			List<DefaultConstraintEntry> entries = new ArrayList<DefaultConstraintEntry>();
			for(DefaultConstraintEntry entry : this.gestures)
			{
				if(entry.getGesture().equals(gestureClass))
					entries.add(entry);
			}
			//check if the gesture is performed by an allowed device
			for (Iterator<DefaultConstraintEntry> iterator = entries.iterator(); iterator.hasNext();) {
				
				DefaultConstraintEntry entry = iterator.next();
				GestureDevice<?,?> device = gesture.getSource();
				
				// if no device type specified, continue with next entry
				if(entry.getDeviceType() == null)
					continue;
				// check if device type matches
				if(entry.getDeviceType().equals(device.getDeviceClass()))
				{
					// if specific devices were specified...
					if(entry.getDevices() != null && !entry.getDevices().isEmpty())
					{
						// ... check if the gesture was created by a specified device (based on ID)
						if(entry.getDevices().contains(device.getDeviceID()))
							found = true;
						else
							found = false;
					}
					else
						found = true;						
				}
				
				if(found == true)
				{
					map.put(gesture, device);
					break;
				}
			}
			
			if(found == false)
			{
				conditionsValid = false;
				break;
			}
		}
		
		/* user check */
		if(conditionsValid && manager!= null) // if previous conditions hold
		{
			MultiValueMap<Integer, String> defined = new MultiValueMap<Integer, String>();
			MultiValueMap<IUser, String> recognised = new MultiValueMap<IUser, String>();
			
			//preprocess defined gestures
			for (Iterator<DefaultConstraintEntry> iterator = this.gestures.iterator(); iterator.hasNext();) {
				DefaultConstraintEntry entry = iterator.next();
				if(entry.getUser() != -1)
					defined.add(entry.getUser(), entry.getGesture());				
			}
			
			//preprocess recognised gestures
			for (Iterator<Gesture<?>> iterator = gestures.iterator(); iterator.hasNext();) {
				Gesture<?> gesture = iterator.next();
				recognised.add(manager.getAssociatedUser(gesture.getSource()), gesture.getName());
			}
			
			//compare gestures
			Set<Integer> definedKeys = defined.keySet();
			Set<IUser> recognisedKeys = recognised.keySet();
			
//			int matches = 0;
			
			//check if for every defined (user,gestures) combination, there is an equivalent in the recognised set
			for (Iterator<Integer> iterator = definedKeys.iterator(); iterator.hasNext();) {
				Integer integer = iterator.next();
				
				// put all users that perform at least the required gestures (those that were defined)
				Map<IUser, Integer> fittingKeys = new HashMap<IUser, Integer>();
				
				List<String> values = defined.getValues(integer);
				for(Iterator<IUser> iter = recognisedKeys.iterator(); iter.hasNext();)
				{
					IUser user = iter.next();
					List<String> list = recognised.getValues(user);
					if(list.containsAll(values))
					{
						fittingKeys.put(user, list.size());
					}
				}
				
				// if not match found...
				if(fittingKeys.isEmpty())
				{
					conditionsValid = false; //... condition invalid
					break;
				}
				else
				{
					// ...use the first found shortest fit
					IUser shortestFittingKey = null;
					int min = Integer.MAX_VALUE;
					for(Iterator<IUser> iter = fittingKeys.keySet().iterator(); iter.hasNext();)
					{
						IUser user = iter.next();
						if(fittingKeys.get(user).intValue() < min)
						{
							min = fittingKeys.get(user).intValue();
							shortestFittingKey = user;
						}
					}
//					matches += 1;
					recognised.removeKey(shortestFittingKey);
				}
				
			}
//			// defining the user that has to perform a gesture is optional
//			// this means that in most cases there will only be a partial mapping from the recognised gestures 
//			// to the defined ones (so !recognisedKeys.isEmpty() is not usable)
//			// so it is only necessary to have for each defined gesture a mapping in the recognised gestures
//			if(matches != definedKeys.size())
//				conditionsValid = false;
		}
		else if(conditionsValid && manager == null)
		{
			// if no manager was passed, return true if no users have been specified otherwise return false
			boolean userSpecified = true;
			for (Iterator<DefaultConstraintEntry> iter = this.gestures.iterator(); iter.hasNext();) {
				DefaultConstraintEntry entry = iter.next();
				if(entry.getUser() > -1)
				{
					userSpecified = false;
					break;
				}
			}	
			
			conditionsValid = (conditionsValid && userSpecified);
		}
		else
		{
			conditionsValid =  false;
		}
		
		return conditionsValid;
	}
	
	public String toString()
	{
		return DefaultConstraint.class.getSimpleName();
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#getParameters()
	 */
	@Override
	public Map<String, String> getParameters() {
		return DEFAULT_CONFIGURATION;
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#getParameter(Java.lang.String property)
	 */
	@Override
	public String getParameter(String property)
	{
		return DEFAULT_CONFIGURATION.get(property);
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#setParameter(Java.lang.String property, Java.lang.String)
	 */
	@Override
	public void setParameter(String property, String value)
	{
		DEFAULT_CONFIGURATION.put(property, value);
		parameterMapping.get(property).setParameter(property, value);
		propertyChangeSupport.firePropertyChange(PROPERTY_CONSTRAINT_PARAMETER_VALUE, null, value);
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#removeAllGestureClasses()
	 */
	@Override
	public void removeAllGestureClasses() {
		DefaultConstraintEntry entry = gestures.get(0);
		gestures.clear();
		propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_GESTURES, 0, entry, null);
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#removeGestureClass(org.ximtec.igesture.core.composite.DefaultConstraintEntry entry)
	 */
	@Override
	public void removeGestureClass(DefaultConstraintEntry entry)
	{
		gestures.remove(entry);
		propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_GESTURES, gestures.indexOf(entry), entry, null);
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#getGestureEntries()
	 */
	@Override
	public List<DefaultConstraintEntry> getGestureEntries()
	{
		return gestures;
	}
	
	public Set<IConstraintParameter> getConstraintParameters()
	{
		Set<IConstraintParameter> params = new HashSet<IConstraintParameter>();
		params.addAll(parameterMapping.values());
		return params;
	}
	
	public void removeAllConstraintParameters()
	{
		parameterMapping.clear();
		parameters.clear();
		DEFAULT_CONFIGURATION.clear();
	}
}
