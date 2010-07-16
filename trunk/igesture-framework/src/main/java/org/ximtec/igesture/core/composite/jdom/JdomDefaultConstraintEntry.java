/**
 * 
 */
package org.ximtec.igesture.core.composite.jdom;

import java.util.Set;

import org.jdom.Element;
import org.ximtec.igesture.core.composite.DefaultConstraintEntry;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomDefaultConstraintEntry extends Element {

	public static final String ROOT_TAG = "gesture";
	public static final String UUID_ATTRIBUTE = "id";
	public static final String IDREF_ATTRIBUTE = "idref";
	public static final String USER_ATTRIBUTE = "user";
	public static final String DEVICE_ATTRIBUTE = "device";
	
	public JdomDefaultConstraintEntry(DefaultConstraintEntry entry)
	{
		super(ROOT_TAG);
		setAttribute(UUID_ATTRIBUTE,entry.getId());
		setAttribute(IDREF_ATTRIBUTE, entry.getGesture());
		if(entry.getUser() > -1)
			setAttribute(USER_ATTRIBUTE, String.valueOf(entry.getUser()));
		if(entry.getDeviceType() != null)
			setAttribute(DEVICE_ATTRIBUTE, entry.getDeviceType());
		if(entry.getDevices() != null && !entry.getDevices().isEmpty())
		{
			addContent(new JdomDevices(entry));
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static DefaultConstraintEntry unmarshal(Element entry) {
		String uuid = entry.getAttributeValue(UUID_ATTRIBUTE);
			
		String deviceType = entry.getAttributeValue(DEVICE_ATTRIBUTE);
		int user = -1;
		try{
			user = Integer.parseInt(entry.getAttributeValue(USER_ATTRIBUTE));
		}
		catch(NumberFormatException e){}
		String gesture = entry.getAttributeValue(IDREF_ATTRIBUTE);
		
		Set<String> devs = null;
		if(entry.getChild(JdomDevices.ROOT_TAG) != null)
			devs = JdomDevices.unmarshal(entry.getChild(JdomDevices.ROOT_TAG));
		
		DefaultConstraintEntry def = new DefaultConstraintEntry(gesture,user,deviceType,devs);
		def.setId(uuid);
		
		return def;
	}

}
