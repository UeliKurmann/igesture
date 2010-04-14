/**
 * 
 */
package org.ximtec.igesture.util.additions3d.jdom;

import org.jdom.Element;
import org.sigtec.jdom.element.JdomDoubleElement;
import org.sigtec.jdom.element.JdomLongElement;
import org.ximtec.igesture.util.additions3d.AccelerationSample;

/**
 * Constructs an XML representation for a AccelerationSample object. The 'timestamp' element is optional.
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class JdomAccelerationSample extends Element {
	
	   public static final String ROOT_TAG = "sampleAcc";
	   public static final String ACC_ELEMENT_X = "x";
	   public static final String ACC_ELEMENT_Y = "y";
	   public static final String ACC_ELEMENT_Z = "z";
	   public static final String ACC_ELEMENT_TIMESTAMP = "timestamp";

	   public JdomAccelerationSample(AccelerationSample sample) {
	      super(ROOT_TAG);
	      addContent(new JdomDoubleElement(ACC_ELEMENT_X, sample.getXAcceleration()));
	      addContent(new JdomDoubleElement(ACC_ELEMENT_Y, sample.getYAcceleration()));
	      addContent(new JdomDoubleElement(ACC_ELEMENT_Z, sample.getZAcceleration()));

	      if (sample.hasTimeStamp()) {
	         addContent(new JdomLongElement(ACC_ELEMENT_TIMESTAMP, sample.getTimeStamp()));
	      }

	   } // JdomAccelerationSample


	   public static AccelerationSample unmarshal(Element sample) {
		   AccelerationSample newSample = new AccelerationSample(Double.parseDouble(sample
	            .getChildText(ACC_ELEMENT_X)), Double.parseDouble(sample
	            .getChildText(ACC_ELEMENT_Y)), Double.parseDouble(sample
	                    .getChildText(ACC_ELEMENT_Z)), 0);
	      setTimestamp(newSample, sample);
	      return newSample;
	   } // unmarshal


	   private static void setTimestamp(AccelerationSample sample, Element source) {
	      Element timestamp = source.getChild(ACC_ELEMENT_TIMESTAMP);

	      if (timestamp != null) {
	         sample.setTimeStamp(Long.parseLong(timestamp.getText()));
	      }

	   } // setTimestamp

}
