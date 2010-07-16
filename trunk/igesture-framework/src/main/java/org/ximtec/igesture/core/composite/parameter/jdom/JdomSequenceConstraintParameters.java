/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter.jdom;

import org.jdom.Element;
import org.sigtec.jdom.element.JdomStringElement;
import org.ximtec.igesture.core.composite.parameter.DefaultConstraintParameters;
import org.ximtec.igesture.core.composite.parameter.IConstraintParameter;
import org.ximtec.igesture.core.composite.parameter.SequenceConstraintParameters;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomSequenceConstraintParameters extends JdomDefaultConstraintParameters {
	
	public static final String MIN_TIME = "minTime";
	public static final String MAX_TIME = "maxTime";
	
	public JdomSequenceConstraintParameters(IConstraintParameter parameter)
	{
		super(parameter);
		String min = parameter.getParameter(SequenceConstraintParameters.Config.MIN_TIME.name());
		String max = parameter.getParameter(SequenceConstraintParameters.Config.MAX_TIME.name());
		addContent(new JdomStringElement(MIN_TIME,min));
		addContent(new JdomStringElement(MAX_TIME,max));
	}
	
	@SuppressWarnings("unchecked")
	public static IConstraintParameter unmarshal(Element param)
	{
		IConstraintParameter par = new SequenceConstraintParameters();
		par.setParameter(DefaultConstraintParameters.Config.GESTURE_TIME.name(), param.getChildText(GESTURE_TIME));
		par.setParameter(SequenceConstraintParameters.Config.MIN_TIME.name(), param.getChildText(MIN_TIME));
		par.setParameter(SequenceConstraintParameters.Config.MAX_TIME.name(), param.getChildText(MAX_TIME));		
		return par;
	}
}
