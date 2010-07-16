/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter.jdom;

import org.jdom.Element;
import org.sigtec.jdom.element.JdomStringElement;
import org.ximtec.igesture.core.composite.parameter.CardinalityConstraintParameters;
import org.ximtec.igesture.core.composite.parameter.IConstraintParameter;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomCardinalityConstraintParameters extends JdomAbstractConstraintParameters {

	public static final String MIN_GESTURES = "minGestures";
	public static final String MAX_GESTURES = "maxGestures";
	
	public JdomCardinalityConstraintParameters(IConstraintParameter parameter) {
		super(parameter);
		String min = parameter.getParameter(CardinalityConstraintParameters.Config.MIN_GESTURES.name());
		String max = parameter.getParameter(CardinalityConstraintParameters.Config.MAX_GESTURES.name());
		addContent(new JdomStringElement(MIN_GESTURES,min));
		addContent(new JdomStringElement(MAX_GESTURES,max));
	}
	
	@SuppressWarnings("unchecked")
	public static IConstraintParameter unmarshal(Element param)
	{
		IConstraintParameter par = new CardinalityConstraintParameters();
		par.setParameter(CardinalityConstraintParameters.Config.MAX_GESTURES.name(), param.getChildText(MAX_GESTURES));
		par.setParameter(CardinalityConstraintParameters.Config.MIN_GESTURES.name(), param.getChildText(MIN_GESTURES));
		return par;
	}
}
