/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter.jdom;

import org.jdom.Element;
import org.ximtec.igesture.core.composite.parameter.ConcurrencyConstraintParameters;
import org.ximtec.igesture.core.composite.parameter.DefaultConstraintParameters;
import org.ximtec.igesture.core.composite.parameter.IConstraintParameter;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomConcurrencyConstraintParameters extends
		JdomDefaultConstraintParameters {

	/**
	 * @param parameter
	 */
	public JdomConcurrencyConstraintParameters(IConstraintParameter parameter) {
		super(parameter);
	}
	
	@SuppressWarnings("unchecked")
	public static IConstraintParameter unmarshal(Element param)
	{
		IConstraintParameter par = new ConcurrencyConstraintParameters();
		par.setParameter(DefaultConstraintParameters.Config.GESTURE_TIME.name(), param.getChildText(GESTURE_TIME));
		return par;
	}

}
