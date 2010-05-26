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
 * @author Björn Puypepuype@gmail.com
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
	 * Removes the specified gesture class from the composite.
	 */
	public void removeGestureClass(DefaultConstraintEntry entry);
	/**
	 * Remove all gesture classes from the composite.
	 */
	public void removeAllGestureClasses();
	
	/** Get all the names of the gesture classes that compose this composite gesture */
	public List<String> getGestureClasses();
	/** Get the disctinct gesture classes that compose this composite gesture */
	public Set<String> getDistinctGestureClasses();
	/** Get the number of composing gestures */
	public int getNumberOfGestures();
	/**
	 * Get all gesture entries.
	 */
	public List<DefaultConstraintEntry> getGestureEntries();
	/**
	 * Validate the constraint conditions.
	 * @param gestures	Gestures that possibly compose the composite gesture.
	 * @param manager	Device manager to check the associated users.
	 * @return
	 */
	public boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager);
	
	/**
	 * Generate all possible string patterns that represent the composite gesture.
	 * @param charMapping Mapping between the gesture class name and the character representation
	 */
	public Set<String> generatePatterns(Map<String, String> charMapping);
	/**
	 * Determine the time window for each composing gesture.
	 * @return Mapping between the gesture name and the corresponding time window.
	 */
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
