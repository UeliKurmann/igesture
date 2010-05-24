/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.io.IDeviceManager;

/**
 * This class represents a sequence constraint. All gestures have to be performed in the order they were defined. A minimum 
 * and a maximum time interval between two consecutive gestures has to be defined.
 * 
 * @author Björn Puypeype, bpuype@gmail.com
 *
 */
public class SequenceConstraint extends DefaultConstraint {

	public enum Config{
		MIN_TIME, MAX_TIME
	}
	
	private static final String MIN_TIME = "00:00:01.000";
	private static final String MAX_TIME = "00:00:10.000";
	
	private Calendar minTime;
	private Calendar maxTime;
	
	public SequenceConstraint() {
		super();
		DEFAULT_CONFIGURATION.put(Config.MIN_TIME.name(), MIN_TIME);
		DEFAULT_CONFIGURATION.put(Config.MAX_TIME.name(), MAX_TIME);
		setterMapping.put(Config.MIN_TIME.name(), "setMinTime");
		setterMapping.put(Config.MAX_TIME.name(), "setMaxTime");
		try {
			setMinTime(MIN_TIME);
			setMaxTime(MAX_TIME);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Set the minimum time between two gestures
	 * @param time	minimum time in format "hh:mm:ss.SSS"
	 * @throws ParseException thrown if the minimum time could not be parsed
	 */
	public void setMinTime(String time) throws ParseException//int hours, int min, int seconds, int milliseconds
	{
		Date d = df.parse(time);
		minTime = Calendar.getInstance();
		minTime.setTime(d);	
	}
	
	/**
	 * Set the maximum time between two gestures
	 * @param time	maximum time in format "hh:mm:ss.SSS"
	 * @throws ParseException thrown if the maximum time could not be parsed
	 */
	public void setMaxTime(String time) throws ParseException
	{
		Date d = df.parse(time);
		maxTime = Calendar.getInstance();
		maxTime.setTime(d);
	}
	
	/**
	 * Get the minimum time between two gestures
	 */
	public Calendar getMinTime()
	{
		return minTime;
	}
	
	/**
	 * Get the maximum time between two gestures
	 */
	public Calendar getMaxTime()
	{
		return maxTime;
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.DefaultConstraint#determineTimeWindows()
	 */
	@Override
	public Map<String, Calendar> determineTimeWindows() {
		
		Map<String, Calendar> map = new HashMap<String, Calendar>();
		
		int i = 0;
		int size = gestures.size();
		for(Iterator<DefaultConstraintEntry> iterator = gestures.iterator(); iterator.hasNext();i++)
		{
			DefaultConstraintEntry entry = iterator.next();
			//processing time
			Calendar cal = (Calendar) processingTime.clone();
			//time to perform gestures
			cal.add(Calendar.SECOND, (size-i)*gestureTime.get(Calendar.SECOND));
			//intervals
			cal.add(Calendar.SECOND, (size-1-i)*maxTime.get(Calendar.SECOND));
			cal.add(Calendar.MINUTE, (size-1-i)*maxTime.get(Calendar.MINUTE));
			cal.add(Calendar.HOUR, (size-1-i)*maxTime.get(Calendar.HOUR));
			
			if(map.containsKey(entry.getGesture()))
			{
				Calendar cal2 = map.get(entry.getGesture());
				if(cal2.after(cal))
					map.put(entry.getGesture(), cal2);
			}
			else
				map.put(entry.getGesture(), cal);
		}
		
		return map;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.DefaultConstraint#generatePatterns(java.util.Map)
	 */
	@Override
	public Set<String> generatePatterns(Map<String, String> charMapping) {
		
		Set<String> patterns = new HashSet<String>();
		
		StringBuilder builder = new StringBuilder();
		for (DefaultConstraintEntry entry : gestures) {
			builder.append(charMapping.get(entry.getGesture()));			
		}
		
		patterns.add(builder.toString());
		
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
			/* time check */
			//get end time of first gesture
			Iterator<Gesture<?>> iterator = gestures.iterator();
			Gesture<?> previousGesture = iterator.next();
			long prevEndTimestamp = getTimeStamp(previousGesture, false);

			//for all consecutive gestures, check if they start after the previous one has ended
			for (; iterator.hasNext();) {
				Gesture<?> gesture = iterator.next();
				long startTimestamp = getTimeStamp(gesture, true);
				long gap = startTimestamp - prevEndTimestamp;
				if(gap >= 0 && gap > ConstraintTool.calculateTimeInMillis(minTime) && gap < ConstraintTool.calculateTimeInMillis(maxTime))//startTimestamp > prevEndTimestamp
				{
					prevEndTimestamp = getTimeStamp(gesture, false);
				}
				else
				{
					conditionsValid = false;
					break;
				}
			}
		}
		
		return conditionsValid;
	}
	
	public String toString()
	{
		return SequenceConstraint.class.getSimpleName();
	}

}
