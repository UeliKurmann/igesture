/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.io.IDeviceManager;

/**
 * This class represents a concurrency constraint, all gestures must be performed in parallel. This means all gestures must
 * have an overlapping time interval in common.
 * 
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class ConcurrencyConstraint extends DefaultConstraint {
	
	public ConcurrencyConstraint()
	{
		super();		
	}
	
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.DefaultConstraint#determineTimeWindows()
	 */
	@Override
	public Map<String, Calendar> determineTimeWindows() {
		Calendar cal = (Calendar) gestureTime.clone();
		cal.add(Calendar.SECOND, processingTime.get(Calendar.SECOND));
		
		Map<String, Calendar> map = new HashMap<String, Calendar>();
		
		for(DefaultConstraintEntry entry : gestures)
		{
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
		
		String pattern = builder.toString();
		
		ConstraintTool.permute(0, "", new boolean[pattern.length()], pattern, patterns);
		
		return patterns;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.DefaultConstraint#validateConditions(java.util.List)
	 */
	@Override
	public boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager) {
		
		boolean conditionsValid = super.validateConditions(gestures, manager);
		
		if(conditionsValid)// if previous conditions hold
		{
		
			/* time check */
			long minTimestamp = Long.MAX_VALUE;
			Gesture<?> minGesture = null;
			
			//determine gesture that was performed first
			for(Gesture<?> gesture : gestures)
			{
				long timestamp = getTimeStamp(gesture, true);
				
				if(timestamp < minTimestamp)
				{
					minTimestamp = timestamp;
					minGesture = gesture;
				}
			}
			
			//determine if all gestures end before or at the same time as the gesture that started first (overlap)
			if(minGesture != null)
			{
				long endTime = getTimeStamp(minGesture, false);
				
				for(Gesture<?> gesture : gestures)
				{
					long timestamp = getTimeStamp(gesture, false);
					
					if(timestamp > endTime)
					{
						conditionsValid = false;
						break;
					}
				}
			}
		}
		
		return conditionsValid;
	}
	
	public String toString()
	{
		return ConcurrencyConstraint.class.getSimpleName();
	}
	
}
