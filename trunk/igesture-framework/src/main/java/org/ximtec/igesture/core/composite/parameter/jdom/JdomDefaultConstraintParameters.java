/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter.jdom;

import org.jdom.Element;
import org.jdom.Namespace;
import org.sigtec.jdom.element.JdomStringElement;
import org.ximtec.igesture.core.composite.parameter.DefaultConstraintParameters;
import org.ximtec.igesture.core.composite.parameter.IConstraintParameter;
import org.ximtec.igesture.util.XMLTool;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomDefaultConstraintParameters extends JdomAbstractConstraintParameters {
	
	public static final String GESTURE_TIME = "gestureTime";
	
	public JdomDefaultConstraintParameters(IConstraintParameter parameter)
	{
		super(parameter);
		String time = parameter.getParameter(DefaultConstraintParameters.Config.GESTURE_TIME.name());
	    addContent(new JdomStringElement(GESTURE_TIME, time));
	}

}
