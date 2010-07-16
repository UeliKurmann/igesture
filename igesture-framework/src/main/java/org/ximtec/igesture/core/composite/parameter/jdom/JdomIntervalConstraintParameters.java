/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter.jdom;

import org.jdom.Element;
import org.sigtec.jdom.element.JdomStringElement;
import org.ximtec.igesture.core.composite.parameter.DefaultConstraintParameters;
import org.ximtec.igesture.core.composite.parameter.IConstraintParameter;
import org.ximtec.igesture.core.composite.parameter.IntervalConstraintParameters;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomIntervalConstraintParameters extends
		JdomDefaultConstraintParameters {

	public static final String DURATION = "duration";
	
	public JdomIntervalConstraintParameters(IConstraintParameter parameter) {
		super(parameter);
		String duration = parameter.getParameter(IntervalConstraintParameters.Config.DURATION.name());
		addContent(new JdomStringElement(DURATION,duration));
	}
	
	@SuppressWarnings("unchecked")
	public static IConstraintParameter unmarshal(Element param)
	{
		IConstraintParameter par = new IntervalConstraintParameters();
		par.setParameter(DefaultConstraintParameters.Config.GESTURE_TIME.name(), param.getChildText(GESTURE_TIME));
		par.setParameter(IntervalConstraintParameters.Config.DURATION.name(), param.getChildText(DURATION));
		return par;
	}

}
