/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter.jdom;

import org.jdom.Element;
import org.sigtec.jdom.element.JdomStringElement;
import org.ximtec.igesture.core.composite.parameter.IConstraintParameter;
import org.ximtec.igesture.core.composite.parameter.ProximityConstraintParameters;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomProximityConstraintParameters extends JdomAbstractConstraintParameters {

	public static final String MAX_DISTANCE = "maxDistance";
	public static final String MIN_DISTANCE = "minDistance";
	public static final String UNIT = "unit";
	
	public JdomProximityConstraintParameters(IConstraintParameter parameter) {
		super(parameter);
		String max, min, unit;
		max = parameter.getParameter(ProximityConstraintParameters.Config.MAX_DISTANCE.name());
		min = parameter.getParameter(ProximityConstraintParameters.Config.MIN_DISTANCE.name());
		unit = parameter.getParameter(ProximityConstraintParameters.Config.DISTANCE_UNIT.name());
		addContent(new JdomStringElement(MIN_DISTANCE,min));
		addContent(new JdomStringElement(MAX_DISTANCE,max));
		addContent(new JdomStringElement(UNIT,unit));
	}
	
	@SuppressWarnings("unchecked")
	public static IConstraintParameter unmarshal(Element param)
	{
		IConstraintParameter par = new ProximityConstraintParameters();
		par.setParameter(ProximityConstraintParameters.Config.MAX_DISTANCE.name(), param.getChildText(MAX_DISTANCE));
		par.setParameter(ProximityConstraintParameters.Config.MIN_DISTANCE.name(), param.getChildText(MIN_DISTANCE));
		par.setParameter(ProximityConstraintParameters.Config.DISTANCE_UNIT.name(), param.getChildText(UNIT));
		return par;		
	}

}
