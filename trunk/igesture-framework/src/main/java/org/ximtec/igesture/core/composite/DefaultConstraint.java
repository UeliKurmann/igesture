/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.DefaultDataObject;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.IDeviceManager;
import org.ximtec.igesture.io.IUser;
import org.ximtec.igesture.util.MultiValueMap;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public abstract class DefaultConstraint extends DefaultDataObject implements Constraint {
	
	protected Map<String, String> DEFAULT_CONFIGURATION = new HashMap<String, String>();
	protected Map<String, String> setterMapping = new HashMap<String, String>();
	
	protected List<DefaultConstraintEntry> gestures;	
	
	protected SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");
	
	protected Calendar gestureTime;
	protected Calendar processingTime;
	
	public DefaultConstraint()
	{
		gestures = new ArrayList<DefaultConstraintEntry>();
		df.setLenient(false);
		try {
			df.parse("00:00:03.000");
			gestureTime = df.getCalendar();
			df.parse("00:00:02.000");
			processingTime = df.getCalendar();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String)
	 */
	@Override
	public void addGestureClass(String gestureClass) {
		gestures.add(new DefaultConstraintEntry(gestureClass));
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, int)
	 */
	@Override
	public void addGestureClass(String gestureClass, int user) {
		gestures.add(new DefaultConstraintEntry(gestureClass, user));
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, java.lang.String, java.util.Set<String>)
	 */
	@Override
	public void addGestureClass(String gestureClass, String deviceType, Set<String> devices) {
		gestures.add(new DefaultConstraintEntry(gestureClass, deviceType, devices));
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#addGestureClass(java.lang.String, int, java.lang.String, java.util.Set<String>)
	 */
	@Override
	public void addGestureClass(String gestureClass, int user, String deviceType, Set<String> devices) {
		gestures.add(new DefaultConstraintEntry(gestureClass,user,deviceType,devices));
	}

//	/* (non-Javadoc)
//	 * @see org.ximtec.igesture.core.composite.Constraint#addSpecificDevice(java.lang.String, java.lang.String)
//	 */
//	@Override
//	public void addSpecificDevice(String gestureClass, String deviceID) {
//		Set<String> dev = gestureDevicesMapping.getValues(gestureClass);
//		if(dev == null)
//		{
//			Set<String> devs = new HashSet<String>();
//			devs.add(deviceID);
//			gestureDevicesMapping.add(gestureClass, devs);
//		}
//		else
//			dev.add(deviceID);	
//	}
//
//	/* (non-Javadoc)
//	 * @see org.ximtec.igesture.core.composite.Constraint#addSpecificDevices(java.lang.String, java.util.Set)
//	 */
//	@Override
//	public void addSpecificDevices(String gestureClass, Set<String> deviceIDs) {
//		Set<String> dev = gestureDevicesMapping.get(gestureClass);
//		if(dev == null)
//		{
//			Set<String> devs = new HashSet<String>();
//			devs.addAll(deviceIDs);
//			gestureDevicesMapping.add(gestureClass, devs);
//		}
//		else
//			dev.addAll(deviceIDs);	
//	}
//
//	/* (non-Javadoc)
//	 * @see org.ximtec.igesture.core.composite.Constraint#setDeviceType(java.lang.String, java.lang.String)
//	 */
//	@Override
//	public void setDeviceType(String gestureClass, String deviceType) {
//		gestureDeviceTypeMapping.add(gestureClass, deviceType);
//	}
//
//	/* (non-Javadoc)
//	 * @see org.ximtec.igesture.core.composite.Constraint#setUser(java.lang.String, int)
//	 */
//	@Override
//	public void setUser(String gestureClass, int user) {
//		gestureUserMapping.add(gestureClass, user);
//	}
	
//	/* (non-Javadoc)
//	 * @see org.ximtec.igesture.core.composite.Constraint#getUser(java.lang.String)
//	 */
//	@Override
//	public int getUser(String gestureClass) {
//		Integer i = gestureUserMapping.getValues(gestureClass);
//		if(i == null)
//			return -1;
//		else
//			return i.intValue();
//	}
//
//	/* (non-Javadoc)
//	 * @see org.ximtec.igesture.core.composite.Constraint#getDeviceType(java.lang.String)
//	 */
//	@Override
//	public String getDeviceType(String gestureClass) {
//		return gestureDeviceTypeMapping.getValues(gestureClass);
//	}
//
//	/* (non-Javadoc)
//	 * @see org.ximtec.igesture.core.composite.Constraint#getDevices(java.lang.String)
//	 */
//	@Override
//	public Set<String> getDevices(String gestureClass) {
//		return gestureDevicesMapping.getValues(gestureClass);
//	}

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
	public abstract Set<String> generatePatterns(Map<String, String> charMapping);

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#validateConditions(java.util.List)
	 */
	@Override
	public boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager)
	{
		boolean conditionsValid = true;
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
				
				if(entry.getDeviceType() == null)
					continue;
				
				if(entry.getDeviceType().equals(device.getDeviceClass()))
				{
					if(entry.getDevices() != null && !entry.getDevices().isEmpty())
					{
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
			
			int matches = 0;
			
			//check if for every defined (user,gestures) combination, there is an equivalent in the recognised set
			for (Iterator<Integer> iterator = definedKeys.iterator(); iterator.hasNext();) {
				Integer integer = iterator.next();
				
				List<String> values = defined.getValues(integer);
				for(Iterator<IUser> iter = recognisedKeys.iterator(); iter.hasNext();)
				{
					IUser user = iter.next();
					List<String> list = recognised.getValues(user);
					if(list.containsAll(values))
					{
						recognised.removeKey(user);
						matches += 1;
						break;
					}
				}
				
			}
			// defining the user that has to perform a gesture is optional
			// this means that in most cases there will only be a partial mapping from the recognised gestures 
			// to the defined ones (so !recognisedKeys.isEmpty() is not usable)
			// so it is only necessary to have for each defined gesture a mapping in the recognised gestures
			if(matches != definedKeys.size())
				conditionsValid = false;
		}
		else if(manager == null)
		{
			conditionsValid = false;
		}
		
		return conditionsValid;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#determineTimeWindows()
	 */
	@Override
	public abstract Map<String, Calendar> determineTimeWindows();
	
	/**
	 * Get the start or end timestamp of a gesture.
	 * @param gesture	the gesture from which to get the timestamp
	 * @param start		true to get the start timestamp, false to get the end timestamp
	 * @return	timestamp
	 */
	protected long getTimeStamp(Gesture<?> gesture, boolean start)
	{
		long timestamp = 0;
		if(gesture instanceof GestureSample)
		{
			Note note = ((GestureSample)gesture).getGesture();
			if(start)
				timestamp = note.getStartPoint().getTimestamp();
			else
				timestamp = note.getEndPoint().getTimestamp();
		}
		else if(gesture instanceof GestureSample3D)
		{
			RecordedGesture3D record = ((GestureSample3D)gesture).getGesture();
			if(start)
				timestamp = record.getStartPoint().getTimeStamp();
			else
				timestamp = record.getEndPoint().getTimeStamp();
		}
		else
			;//TODO error
		
		return timestamp;
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
		Class<?> clazz = this.getClass();
		try {
			Method m = clazz.getDeclaredMethod(setterMapping.get(property), String.class);
			m.invoke(this, value);
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
	 * @see org.ximtec.igesture.core.composite.Constraint#removeAllGestureClasses()
	 */
	@Override
	public void removeAllGestureClasses() {
		gestures.clear();
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#removeGestureClass()
	 */
	@Override
	public void removeGestureClass() {
		gestures.remove(gestures.size()-1);
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.Constraint#getGestureEntries()
	 */
	@Override
	public List<DefaultConstraintEntry> getGestureEntries()
	{
		return gestures;
	}
	
}