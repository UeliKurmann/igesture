/**
 * 
 */
package org.ximtec.igesture.core.jdom;

import java.util.List;

import org.jdom.Element;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;
import org.ximtec.igesture.util.additionswiimote.AccelerationSample;
import org.ximtec.igesture.util.additionswiimote.WiiAccelerations;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomWiiAccelerations extends Element {
	
	public static final String ROOT_TAG = "acceleration";
	
	public JdomWiiAccelerations(WiiAccelerations accelerations) {
	      super(ROOT_TAG);

	      for (AccelerationSample sample : accelerations.getSamples()) {
	         addContent(new JdomAccelerationSample(sample));
	      }
	   }

	   @SuppressWarnings("unchecked")
	   public static WiiAccelerations unmarshal(Element gesture) {
		   WiiAccelerations newAccelerations = new WiiAccelerations();

	      for (Element sample : (List<Element>)gesture.getChildren(JdomAccelerationSample.ROOT_TAG)) {
	         newAccelerations.addSample(JdomAccelerationSample.unmarshal(sample));
	      }

	      return newAccelerations;
	   } // unmarshal

}
