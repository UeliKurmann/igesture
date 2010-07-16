/**
 * 
 */
package org.ximtec.igesture.core.jdom;

import java.util.List;

import org.jdom.Element;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.SampleDescriptor3D;
import org.ximtec.igesture.util.additions3d.Note3D;

/**
 * XML support for the SampleDescriptor3D class.
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class JdomSampleDescriptor3D extends Element {

	public static final String ROOT_TAG = "descriptor3D";

	public static final String TYPE_ATTRIBUTE = "type";

	public static final String UUID_ATTRIBUTE = "id";
	
	public JdomSampleDescriptor3D(SampleDescriptor3D descriptor)
	{
		super(ROOT_TAG);
		setAttribute(TYPE_ATTRIBUTE, descriptor.getType().getName());
	    setAttribute(UUID_ATTRIBUTE, descriptor.getId());
	    
	    for (final Gesture<Note3D> sample : descriptor.getSamples()) {
	         addContent(new JdomGestureSample3D(sample));
	    }
	}
	
	@SuppressWarnings("unchecked")
	   public static Object unmarshal(Element descriptor) {
	      String uuid = descriptor.getAttributeValue(UUID_ATTRIBUTE);
	      SampleDescriptor3D gestureDescriptor = new SampleDescriptor3D();
	      gestureDescriptor.setId(uuid);

	      for (Element sample : (List<Element>)descriptor.getChildren(JdomGestureSample3D.ROOT_TAG)) {
	         gestureDescriptor.addSample(JdomGestureSample3D.unmarshal(sample));
	      }

	      return gestureDescriptor;
	   } // unmarshal
	
	
}
