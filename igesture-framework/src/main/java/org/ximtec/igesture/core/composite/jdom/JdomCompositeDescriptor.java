/**
 * 
 */
package org.ximtec.igesture.core.composite.jdom;

import java.util.List;

import org.jdom.Element;
import org.ximtec.igesture.core.composite.CompositeDescriptor;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomCompositeDescriptor extends Element {
	
	public static final String ROOT_TAG = "compositeDescriptor";
	
	public static final String TYPE_ATTRIBUTE = "type";

	public static final String UUID_ATTRIBUTE = "id";
	
	public JdomCompositeDescriptor(CompositeDescriptor descriptor)
	{
		super(ROOT_TAG);
	    setAttribute(TYPE_ATTRIBUTE, descriptor.getClass().getName());
	    setAttribute(UUID_ATTRIBUTE, descriptor.getId());
	    addContent(new JdomConstraint(descriptor.getConstraint()));
	}
	
	@SuppressWarnings("unchecked")
	public static Object unmarshal(Element descriptor) {
		String uuid = descriptor.getAttributeValue(UUID_ATTRIBUTE);
		CompositeDescriptor gestureDescriptor = new CompositeDescriptor();
		gestureDescriptor.setId(uuid);
		
		for(Element constraint : (List<Element>)descriptor.getChildren(JdomConstraint.ROOT_TAG))
		{
			gestureDescriptor.setConstraint(JdomConstraint.unmarshal(constraint));
		}
		
		return gestureDescriptor;
	} // unmarshal

}
