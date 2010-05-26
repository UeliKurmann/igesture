/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.io.IDeviceManager;
import org.ximtec.igesture.util.Constant;
import org.ximtec.igesture.util.RecordedGesture3DTool;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

/**
 * This class represents a two-fold constraint. First the gestures have to be performed in sequence and secondly, they have 
 * to be performed in each others vicinity. The minimum and maximum distance has to be defined. This distance is the diagonal
 * of the rectangular bounding box of the gestures for 2D gestures, for 3D gestures it is the diagonal of each facet of a 
 * cubic bounding space.
 * For the time constraint, the minimum and maximum time between two consecutive gestures has to be defined.
 * 
 * @author Björn Puypepuype@gmail.com
 *
 */
public class ProximitySequenceConstraint extends SequenceConstraint {

	private static final Logger LOGGER = Logger.getLogger(ProximitySequenceConstraint.class.getName());
	private static final String ERROR_MESSAGE_NO_TYPE = "The device type must be specified for a proximity based constraint in order\n" +
			"to ensure that the coordinates of the gestures can be logically compared.\n" +
			"Please select a device type.";
	private static final String LOGGER_MESSAGE_NO_TYPE = "Proximity based constraints must specify the device type. " +
			"The gesture class was not added.";
	
	private static final String ERROR_MESSAGE_WRONG_TYPE = "The gestures created by a device of the specified type cannot be logically compared\n" +
			"with the other gestures in the constraint!\n" +
			"Please specify a device which creates gestures of type: ";
	private static final String LOGGER_MESSAGE_WRONG_TYPE = "The gesture type (e.g. 2D, 3D) of all composing gestures must match. The gesture class was not added.";
	
	public enum Config{
		MIN_DISTANCE, MAX_DISTANCE, DISTANCE_UNIT
	}
	
	private final static String MIN_DISTANCE = "0";
	private final static String MAX_DISTANCE = "10";
	private final static String DISTANCE_UNIT = Constant.CM;

	private double minDistance;
	private double maxDistance;
	private String distanceUnit;

	public ProximitySequenceConstraint() {
		super();
		DEFAULT_CONFIGURATION.put(Config.MIN_DISTANCE.name(), MIN_DISTANCE);
		DEFAULT_CONFIGURATION.put(Config.MAX_DISTANCE.name(), MAX_DISTANCE);
		DEFAULT_CONFIGURATION.put(Config.DISTANCE_UNIT.name(), DISTANCE_UNIT);
		setterMapping.put(Config.DISTANCE_UNIT.name(), "setDistanceUnit");
		setterMapping.put(Config.MIN_DISTANCE.name(), "setMinDistance");
		setterMapping.put(Config.MAX_DISTANCE.name(), "setMaxDistance");
		setMinDistance(MIN_DISTANCE);
		setMaxDistance(MAX_DISTANCE);
		setDistanceUnit(DISTANCE_UNIT);
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

	/**
	 * Get the minimum distance between two gestures.
	 */
	public double getMinDistance() {
		return minDistance;
	}
	
	/**
	 * Set the minimum distance between two gestures.
	 */
	public void setMinDistance(String minDistance) throws NumberFormatException{
		this.minDistance = Double.parseDouble(minDistance);
	}
	
	/**
	 * Get the maximum distance between two gestures.
	 */
	public double getMaxDistance() {
		return maxDistance;
	}
	
	/**
	 * Set the maximum distance between two gestures.
	 */
	public void setMaxDistance(String maxDistance) throws NumberFormatException{
		this.maxDistance = Double.parseDouble(maxDistance);
	}
	
	/**
	 * Get the distance unit.
	 * @see org.ximtec.igesture.core.composite.Constraint
	 */
	public String getDistanceUnit() {
		return distanceUnit;
	}
	
	/**
	 * Set the distance unit.
	 * @see org.ximtec.igesture.core.composite.Constraint
	 */
	public void setDistanceUnit(String distanceUnit) {
		this.distanceUnit = distanceUnit;
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.DefaultConstraint#validateConditions(java.util.List)
	 */
	@Override
	public boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager) {
		
		boolean conditionsValid = super.validateConditions(gestures, manager);
		
		/* distance check */
		if(conditionsValid) // if previous conditions hold
		{
			boolean same = true;
			Gesture<?> gest = gestures.get(0);
			if(gest instanceof GestureSample)
			{
				List<Note> notes = new ArrayList<Note>();
				for (Iterator<Gesture<?>> iterator = gestures.iterator(); iterator.hasNext();) {
					Gesture<?> gesture = iterator.next();
					if(gesture instanceof GestureSample)
					{
						notes.add(((GestureSample)gesture).getGesture());
					}
					else
					{
						same = false;
						break;
					}
					
				}
				if(same)
				{
					conditionsValid = ConstraintTool.isBoundsDiagonalValid(notes,minDistance,maxDistance);
				}
//				else
//					throw new RuntimeException("Gestures need to be of same type. 2D and 3D gestures cannot be mixed in proximity based constraints");
				
			}
			else if(gest instanceof GestureSample3D)
			{
				List<Note> notesXY = new ArrayList<Note>();
				List<Note> notesYZ = new ArrayList<Note>();
				List<Note> notesXZ = new ArrayList<Note>();
				for (Iterator<Gesture<?>> iterator = gestures.iterator(); iterator.hasNext();) {
					Gesture<?> gesture = iterator.next();
					if(gesture instanceof GestureSample3D)
					{
						RecordedGesture3D record = ((GestureSample3D)gesture).getGesture();
						List<Gesture<Note>> notes = RecordedGesture3DTool.splitToPlanes(record);
						notesXY.add(notes.get(0).getGesture());
						notesYZ.add(notes.get(1).getGesture());
						notesXZ.add(notes.get(2).getGesture());
					}
					else
					{
						same = false;
						break;
					}
					
				}
				if(same)
				{					
					conditionsValid =  ConstraintTool.isBoundsDiagonalValid(notesXZ,minDistance,maxDistance);
					if(conditionsValid)
					{
						conditionsValid = ConstraintTool.isBoundsDiagonalValid(notesYZ,minDistance,maxDistance);
						if(conditionsValid)
							conditionsValid = ConstraintTool.isBoundsDiagonalValid(notesXY,minDistance,maxDistance);
					}					
				}
//				else
//					throw new ProximityException();
				
			}
		}
		
		return conditionsValid;
	}
	
	public String toString()
	{
		return ProximitySequenceConstraint.class.getSimpleName();
	}
}
