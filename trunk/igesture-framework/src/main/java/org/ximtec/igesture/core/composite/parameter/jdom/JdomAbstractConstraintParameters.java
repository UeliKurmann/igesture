/**
 * 
 */
package org.ximtec.igesture.core.composite.parameter.jdom;

import org.jdom.Element;
import org.jdom.Namespace;
import org.ximtec.igesture.core.composite.parameter.AbstractConstraintParameters;
import org.ximtec.igesture.core.composite.parameter.IConstraintParameter;
import org.ximtec.igesture.util.XMLTool;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomAbstractConstraintParameters extends Element {
	
	public static final String ROOT_TAG = "param";
	
	public static final String UUID_ATTRIBUTE = "id";
	public static final String TYPE_ATTRIBUTE = "type";
	
	public JdomAbstractConstraintParameters(IConstraintParameter parameter)
	{
		super(ROOT_TAG);
		setAttribute(TYPE_ATTRIBUTE, parameter.getClass().getName());
	    setAttribute(UUID_ATTRIBUTE, ((AbstractConstraintParameters)parameter).getId());
	    setAttribute(TYPE_ATTRIBUTE, "igs:"+parameter.getClass().getSimpleName()+"Type", Namespace.getNamespace("xsi",XMLTool.XSI_NAMESPACE));
	}
	


}
