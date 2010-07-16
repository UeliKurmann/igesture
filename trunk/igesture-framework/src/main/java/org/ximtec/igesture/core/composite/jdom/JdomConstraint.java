/**
 * 
 */
package org.ximtec.igesture.core.composite.jdom;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.jdom.Element;
import org.jdom.Namespace;
import org.ximtec.igesture.core.composite.Constraint;
import org.ximtec.igesture.core.composite.DefaultConstraint;
import org.ximtec.igesture.core.composite.DefaultConstraintEntry;
import org.ximtec.igesture.core.composite.parameter.AbstractConstraintParameters;
import org.ximtec.igesture.core.composite.parameter.IConstraintParameter;
import org.ximtec.igesture.core.composite.parameter.jdom.JdomAbstractConstraintParameters;
import org.ximtec.igesture.util.XMLTool;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomConstraint extends Element {

	public static final String ROOT_TAG = "constraint";
	
	public static final String UUID_ATTRIBUTE = "id";
	public static final String TYPE_ATTRIBUTE = "type";
	public static final String IDREF_ATTRIBUTE = "idref";
	
	public JdomConstraint(Constraint constraint)
	{
		super(ROOT_TAG);
		setAttribute(TYPE_ATTRIBUTE, constraint.getClass().getName());
	    setAttribute(UUID_ATTRIBUTE, ((DefaultConstraint)constraint).getId());
	    setAttribute(TYPE_ATTRIBUTE, "igs:"+constraint.getClass().getSimpleName()+"Type", Namespace.getNamespace("xsi",XMLTool.XSI_NAMESPACE));
	    
	    for(DefaultConstraintEntry entry : constraint.getGestureEntries())
	    {
	    	addContent(new JdomDefaultConstraintEntry(entry));
	    }
	    for(IConstraintParameter param : constraint.getConstraintParameters())
	    {
		    String cl = param.getClass().getName().substring(0,param.getClass().getName().lastIndexOf('.')+1);
		    String jdomclazz = cl+"jdom.Jdom"+param.getClass().getSimpleName();
		    try {
				Class<?> clazz = Class.forName(jdomclazz);
				Constructor ctor = clazz.getDeclaredConstructor(IConstraintParameter.class);
				addContent((Element)ctor.newInstance(param)); 
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
	    }
	}
	
	@SuppressWarnings("unchecked")
	public static Constraint unmarshal(Element constraint) {
		String type = constraint.getAttributeValue(TYPE_ATTRIBUTE);
		String uuid = constraint.getAttributeValue(UUID_ATTRIBUTE);
		
		Constraint c = null;
		Class<?> clazz;
		try {
			clazz = Class.forName(type);
			Constructor ctor = clazz.getDeclaredConstructor();
			c = (Constraint) ctor.newInstance();
			((DefaultConstraint)c).setId(uuid);
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();
		}
		
//		Map<String,Set<String>> devmapping = new HashMap<String,Set<String>>();
		// <devices>
//		for(Element devices : (List<Element>)constraint.getChildren(JdomDevices.ROOT_TAG))
//		{
//			Set<String> devs = JdomDevices.unmarshal(devices);
//			devmapping.put(devices.getAttribute(IDREF_ATTRIBUTE).toString(),devs);
//		}
		// <gesture>	
		for (Element entry : (List<Element>)constraint.getChildren(JdomDefaultConstraintEntry.ROOT_TAG)) {
			DefaultConstraintEntry e = JdomDefaultConstraintEntry.unmarshal(entry);
			if(e.getUser() > -1)
			{
				if(e.getDeviceType() != null && e.getDeviceType().length() > 0)
				{					
					c.addGestureClass(e.getGesture(), e.getUser(), e.getDeviceType(), e.getDevices());
//							devmapping.get(entry.getAttribute(IDREF_ATTRIBUTE).toString()));
				}
				else
				{
					c.addGestureClass(e.getGesture(), e.getUser());
				}
			}
			else if(e.getDeviceType() != null && e.getDeviceType().length() > 0)
			{
				c.addGestureClass(e.getGesture(), e.getDeviceType(), e.getDevices());
//						devmapping.get(entry.getAttribute(IDREF_ATTRIBUTE).toString()));
			}
			else
			{
				c.addGestureClass(e.getGesture());
			}
	    }
		// <param>
		c.removeAllConstraintParameters();
		for(Element parameter : (List<Element>)constraint.getChildren(JdomAbstractConstraintParameters.ROOT_TAG))
		{
			type = parameter.getAttributeValue(TYPE_ATTRIBUTE);
			uuid = constraint.getAttributeValue(UUID_ATTRIBUTE);
			
			int index = type.lastIndexOf('.');
			String cl = type.substring(index+1);
			String jdomclazz = type.substring(0,index+1);
			jdomclazz = jdomclazz.concat("jdom.Jdom").concat(cl);
			
			IConstraintParameter param = null;
			try {
				clazz = Class.forName(jdomclazz);
				Method m = clazz.getDeclaredMethod("unmarshal", Element.class);
				param = (IConstraintParameter) m.invoke(null, parameter);
				((AbstractConstraintParameters)param).setId(uuid);
				c.addConstraintParameters(param);
			} catch (ClassNotFoundException e) {e.printStackTrace();
			} catch (SecurityException e) {e.printStackTrace();
			} catch (NoSuchMethodException e) {e.printStackTrace();
			} catch (IllegalArgumentException e) {e.printStackTrace();
			} catch (IllegalAccessException e) {e.printStackTrace();
			} catch (InvocationTargetException e) {e.printStackTrace();
			}			
		}
		
		return c;
	} // unmarshal
}
