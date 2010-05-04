/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.Map;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.io.IDeviceManager;

/**
 * Constraint for a composite gesture.
 * 
 * If a composite gesture contains two or more times the same simple gestures, you MUST call the necessary 
 * add methods for one simple after each other to get correct results.
 * 
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public interface Constraint {
	
	/**
	 * Add a gesture class to the composite gesture.
	 * Note: All users and devices are allowed to perform the gesture.
	 */
	public void addGestureClass(String gestureClass) throws IllegalArgumentException;
	/**
	 * Add a gesture class to the composite gesture and specify the user that performs it.
	 * Note: All devices are allowed to perform the gesture.
	 */
	public void addGestureClass(String gestureClass, int user) throws IllegalArgumentException;
	/**
	 * Add a gesture class to the composite gesture and specify the device type used to perform it.
	 * Note: all users are allowed to perform to gesture. If devices is null, all devices of type deviceType are
	 * allowed to perform the gesture.
	 */
	public void addGestureClass(String gestureClass, String deviceType, Set<String> devices) throws IllegalArgumentException;
	/**
	 * Add a gesture class to the composite gesture and specify the user and the device type used to perform it.
	 * Note: if devices is null, all devices of type deviceType are allowed to perform the gesture.
	 */
	public void addGestureClass(String gestureClass, int user, String deviceType, Set<String> devices) throws IllegalArgumentException;
	/**
	 * Removes the last added gesture class from the composite.
	 */
	public void removeGestureClass();
	/**
	 * Remove all gesture classes from the composite.
	 */
	public void removeAllGestureClasses();
	
//	/**
//	 * Set the user that performs the gesture <i>gestureClass</i>.
//	 */
//	public void setUser(String gestureClass, int user);
//	/**
//	 * Set the device type used to perform the gesture <i>gestureClass</i>
//	 */
//	public void setDeviceType(String gestureClass, String deviceType);
//	
//	/**
//	 * Add a specific device that can be used to perform a gesture.
//	 */
//	public void addSpecificDevice(String gestureClass, String deviceID);
//	/**
//	 * Add specific devices that can be used to perform a gesture.
//	 */
//	public void addSpecificDevices(String gestureClass, Set<String> deviceIDs);
//	
//	/** 
//	 * Get the user that performs the gesture <i>gestureClass</i>. 
//	 * Returns -1 is user was not specified 
//	 */
//	public int getUser(String gestureClass);
//	/** 
//	 * Get the device type that is used to perform the gesture <i>gestureClass</i>
//	 * Returns null if the device type was not specified. 
//	 */
//	public String getDeviceType(String gestureClass);
//	/** 
//	 * Get the specific devices that may be used to perform the gesture <i>gestureClass</i>
//	 * Returns null if the devices were not specified.
//	 */
//	public Set<String> getDevices(String gestureClass);
	
	/** Get all the gesture classes that compose this composite gesture */
	public List<String> getGestureClasses();
	/** Get the disctinct gesture classes that compose this composite gesture */
	public Set<String> getDistinctGestureClasses();
	/** Get the number of composing gestures */
	public int getNumberOfGestures();
	
	public List<DefaultConstraintEntry> getGestureEntries();
	
	public boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager);
	
	public Set<String> generatePatterns(Map<String, String> charMapping);
	
	public Map<String, Calendar> determineTimeWindows();
	
	/**
 	 * Returns an array of containing the constraint parameters.
	 * 
	 * @return the constraint parameters.
	 */
	public Map<String, String> getParameters();
	
	public String getParameter(String property);
	
	public void setParameter(String property, String value);
	
	public String toString();
}
