/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.composite.ConstraintTool;
import org.ximtec.igesture.core.composite.DefaultConstraintEntry;
import org.ximtec.igesture.io.IDeviceManager;
import org.ximtec.igesture.util.Constant;
import org.ximtec.igesture.util.Note3DTool;
import org.ximtec.igesture.util.additions3d.Note3D;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class ProximityConstraintParameters extends AbstractConstraintParameters{
	
	public enum Config{
		MIN_DISTANCE, MAX_DISTANCE, DISTANCE_UNIT
	}
	
	private final static String MIN_DISTANCE = "0";
	private final static String MAX_DISTANCE = "10";
	private final static String DISTANCE_UNIT = Constant.CM;

	private double minDistance;
	private double maxDistance;
	private String distanceUnit;
	
	public ProximityConstraintParameters()
	{
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
	 * @see org.ximtec.igesture.core.composite.parameter.AbstractConstraintParameters#validateConditions(java.util.List, org.ximtec.igesture.io.IDeviceManager)
	 */
	@Override
	public boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager) {
		boolean conditionsValid = true;
		
		/* distance check */
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
					Note3D record = ((GestureSample3D)gesture).getGesture();
					List<Gesture<Note>> notes = Note3DTool.splitToPlanes(record);
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
		
		return conditionsValid;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.AbstractConstraintParameters#determineTimeWindows(java.util.List)
	 */
	@Override
	public Map<String, Calendar> determineTimeWindows(List<DefaultConstraintEntry> gestures) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.AbstractConstraintParameters#generatePatterns(java.util.Map, java.util.List)
	 */
	@Override
	public Set<String> generatePatterns(Map<String, String> charMapping, List<DefaultConstraintEntry> gestures) {
		return null;
	}

}
