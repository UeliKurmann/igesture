/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.composite.ConstraintTool;
import org.ximtec.igesture.core.composite.DefaultConstraintEntry;
import org.ximtec.igesture.io.IDeviceManager;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class IntervalConstraintParameters extends DefaultConstraintParameters {
	
	public enum Config{
		DURATION;
	}
	
	private static final String DURATION = "00:01:00.000";
	
	private Calendar duration;

	public IntervalConstraintParameters()
	{
		super();
		DEFAULT_CONFIGURATION.put(Config.DURATION.name(), DURATION);
		setterMapping.put(Config.DURATION.name(), "setDuration");
		try {
			setDuration(DURATION);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the duration of the interval.
	 * @param time	duration in format "hh:mm:ss.SSS"
	 * @throws ParseException thrown if the duration could not be parsed
	 */
	public void setDuration(String time) throws ParseException
	{
		Date d = ConstraintTool.getDateFormatter().parse(time);
		duration = Calendar.getInstance();
		duration.setTime(d);
	}

	/**
	 * Get the duration of the interval
	 */
	public Calendar getDuration()
	{
		return duration;
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.AbstractConstraintParameters#validateConditions(java.util.List, org.ximtec.igesture.io.IDeviceManager)
	 */
	@Override
	public boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager) {
		boolean conditionsValid = true;
		
		/* time check */
		long minTimestamp = Long.MAX_VALUE;
		
		//determine start time of the gesture that was performed first
		for(Gesture<?> gesture : gestures)
		{
			long timestamp = ConstraintTool.getTimeStamp(gesture, true);
			
			if(timestamp < minTimestamp)
			{
				minTimestamp = timestamp;
			}
		}
		
		//determine the end time of the interval
		long intervalTime = ConstraintTool.calculateTimeInMillis(duration);
		long endTimestamp = minTimestamp+intervalTime;
//			Calendar cal = Calendar.getInstance();
//			cal.setTimeInMillis(minTimestamp);
//			cal.add(Calendar.MILLISECOND, duration.get(Calendar.MILLISECOND));
//			cal.add(Calendar.SECOND, duration.get(Calendar.SECOND));
//			cal.add(Calendar.MINUTE, duration.get(Calendar.MINUTE));
//			cal.add(Calendar.HOUR, duration.get(Calendar.HOUR));
//			long endTimestamp = cal.getTimeInMillis();
		
		//determine if all gestures end before or at the same time as the end time of the interval
		for(Gesture<?> gesture : gestures)
		{
			long timestamp = ConstraintTool.getTimeStamp(gesture, false);
			
			if(timestamp > endTimestamp)
			{
				conditionsValid = false;
				break;
			}
		}
		
		return conditionsValid;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.AbstractConstraintParameters#determineTimeWindows(java.util.List)
	 */
	@Override
	public Map<String, Calendar> determineTimeWindows(List<DefaultConstraintEntry> gestures) {
		
		Calendar cal = (Calendar) duration.clone();
		cal.add(Calendar.SECOND, processingTime.get(Calendar.SECOND));
		
		Map<String, Calendar> map = new HashMap<String, Calendar>();
		
		for(DefaultConstraintEntry entry: gestures)
		{
			map.put(entry.getGesture(), cal);
		}
		
		return map;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.AbstractConstraintParameters#generatePatterns(java.util.Map, java.util.List)
	 */
	@Override
	public Set<String> generatePatterns(Map<String, String> charMapping, List<DefaultConstraintEntry> gestures) {

		Set<String> patterns = new HashSet<String>();
		
		StringBuilder builder = new StringBuilder();
		for (DefaultConstraintEntry entry : gestures) {
			builder.append(charMapping.get(entry.getGesture()));			
		}
		
		String pattern = builder.toString();
		
		ConstraintTool.permute(0, "", new boolean[pattern.length()], pattern, patterns);
		
		return patterns;
	}

}
