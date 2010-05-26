/**
 * 
 */
package org.ximtec.igesture.util.additions3d.jdom;

import java.util.List;

import org.jdom.Element;
import org.ximtec.igesture.util.additions3d.AccelerationSample;
import org.ximtec.igesture.util.additions3d.Accelerations;
import org.ximtec.igesture.util.additions3d.jdom.JdomAccelerationSample;

/**
 * @author Björn Puypepuype@gmail.com
 *
 */
public class JdomAccelerations extends Element {
	
	public static final String ROOT_TAG = "acceleration";
	
	public JdomAccelerations(Accelerations accelerations) {
	      super(ROOT_TAG);

	      for (AccelerationSample sample : accelerations.getSamples()) {
	         addContent(new JdomAccelerationSample(sample));
	      }
	   }

	   @SuppressWarnings("unchecked")
	   public static Accelerations unmarshal(Element gesture) {
		   Accelerations newAccelerations = new Accelerations();

	      for (Element sample : (List<Element>)gesture.getChildren(JdomAccelerationSample.ROOT_TAG)) {
	         newAccelerations.addSample(JdomAccelerationSample.unmarshal(sample));
	      }

	      return newAccelerations;
	   } // unmarshal

}
