/**
 * 
 */
package org.ximtec.igesture.core.composite;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ximtec.igesture.core.composite.parameter.IConstraintParameter;
import org.ximtec.igesture.core.composite.parameter.SequenceConstraintParameters;

/**
 * This class represents a sequence constraint. All gestures have to be performed in the order they were defined. A minimum 
 * and a maximum time interval between two consecutive gestures has to be defined.
 * 
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class SequenceConstraint extends DefaultConstraint {
	
	public SequenceConstraint() {
		addConstraintParameters(new SequenceConstraintParameters());
	}
		
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.DefaultConstraint#determineTimeWindows()
	 */
	@Override
	public Map<String, Calendar> determineTimeWindows() {
		
		Map<String, Calendar> map = new HashMap<String, Calendar>();
		
		for (Iterator<IConstraintParameter> iterator = parameters.iterator(); iterator.hasNext();) {
			IConstraintParameter param = iterator.next();
			if(param instanceof SequenceConstraintParameters)
				map = param.determineTimeWindows(gestures);
		}
		return map;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.core.composite.DefaultConstraint#generatePatterns(java.util.Map)
	 */
	@Override
	public Set<String> generatePatterns(Map<String, String> charMapping) {
		Set<String> patterns = new HashSet<String>();
		
		for (Iterator<IConstraintParameter> iterator = parameters.iterator(); iterator.hasNext();) {
			IConstraintParameter param = iterator.next();
			if(param instanceof SequenceConstraintParameters)
				patterns = param.generatePatterns(charMapping, gestures);
		}
		return patterns;
	}
	
	public String toString()
	{
		return SequenceConstraint.class.getSimpleName();
	}

}
