/**
 * 
 */
package org.ximtec.igesture.core.composite.jdom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom.Element;
import org.sigtec.jdom.element.JdomStringElement;
import org.ximtec.igesture.core.composite.DefaultConstraintEntry;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomDevices extends Element {

	public static final String ROOT_TAG = "devices";
	public static final String IDREF_ATTRIBUTE = "idref";
	public static final String DEVICE = "device";
	
	public JdomDevices(DefaultConstraintEntry entry)
	{
		super(ROOT_TAG);
//		setAttribute(IDREF_ATTRIBUTE, entry.getId());
		
		for(String dev : entry.getDevices())
		{
			addContent(new JdomStringElement(DEVICE, dev));
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Set<String> unmarshal(Element devices) {
		Set<String> devs = new HashSet<String>();
		for(Element device : (List<Element>)devices.getChildren(JdomDevices.DEVICE))
		{
			devs.add(device.getText());
		}
		if(devs.isEmpty())
			devs = null;
		return devs;
	}
}
