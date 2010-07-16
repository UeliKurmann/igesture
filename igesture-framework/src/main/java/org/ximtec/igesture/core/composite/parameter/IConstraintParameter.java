/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter;

import java.util.Calendar;
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
public interface IConstraintParameter {

	public Map<String, String> getParameters();

	public String getParameter(String property);

	public void setParameter(String property, String value);

	public boolean validateConditions(List<Gesture<?>> gestures, IDeviceManager manager);

	public Set<String> generatePatterns(Map<String, String> charMapping, List<DefaultConstraintEntry> gestures);

	public Map<String, Calendar> determineTimeWindows(List<DefaultConstraintEntry> gestures);

}