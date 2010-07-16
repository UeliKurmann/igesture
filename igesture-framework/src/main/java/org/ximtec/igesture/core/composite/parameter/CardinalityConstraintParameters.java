/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.composite.DefaultConstraintEntry;
import org.ximtec.igesture.io.IDeviceManager;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class CardinalityConstraintParameters extends AbstractConstraintParameters {

	public enum Config{
		MIN_GESTURES, MAX_GESTURES
	}
	
	private static final String MIN_GESTURES = "2";
	private static final String MAX_GESTURES = "2";
	
	private int minGestures;
	private int maxGestures;
	
	public CardinalityConstraintParameters()
	{
		super();
		DEFAULT_CONFIGURATION.put(Config.MIN_GESTURES.name(), MIN_GESTURES);
		DEFAULT_CONFIGURATION.put(Config.MAX_GESTURES.name(), MAX_GESTURES);
		setterMapping.put(Config.MIN_GESTURES.name(), "setMinimumGestures");
		setterMapping.put(Config.MAX_GESTURES.name(), "setMaximumGestures");
		setMinimumGestures(MIN_GESTURES);
		setMaximumGestures(MAX_GESTURES);
	}
	
	/**
	 * Set the minimum number of gestures to perform
	 */
	public void setMinimumGestures(String min) throws NumberFormatException
	{
		minGestures = Integer.parseInt(min);
	}
	
	/**
	 * Get the minimum number of gestures that has to be performed
	 */
	public int getMinimumGestures()
	{
		return minGestures;
	}
	
	/**
	 * Set the maximum number of gestures to perform
	 */
	public void setMaximumGestures(String max) throws NumberFormatException
	{
		maxGestures = Integer.parseInt(max);
	}
	
	/**
	 * Get the maximum number of gestures that has to be performed
	 */
	public int getMaximumGestures()
	{
		return maxGestures;
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.parameter.AbstractConstraintParameters#validateConditions(java.util.List, org.ximtec.igesture.io.IDeviceManager)
	 */
	@Override
	public boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager) {
		return true;
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
	public Set<String> generatePatterns(Map<String, String> charMapping,List<DefaultConstraintEntry> gestures) {

		Set<String> patterns = new HashSet<String>();
		
		String key = gestures.get(0).getGesture();
		
		for (int i = minGestures; i <= maxGestures; i++) {
			StringBuilder builder = new StringBuilder();
			for(int j = 0; j < i; j++)
			{
				builder.append(charMapping.get(key));
			}
			patterns.add(builder.toString());
		}
		
		return patterns;
	}

}
