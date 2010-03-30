/**
 * 
 */
package org.ximtec.igesture.tool.gesturevisualisation;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.util.GestureTool;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class RecordedGesture3DPanel implements GesturePanel {

	Gesture< ? > gesture;
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.gesturevisualisation.GesturePanel#getPanel(java.awt.Dimension)
	 */
	@Override
	public JPanel getPanel(Dimension dimension) {
		JPanel panel = null;

	      if (gesture != null) {
	         JLabel label = new JLabel(new ImageIcon(GestureTool.createRecordedGesture3DImage(
	        		 ((GestureSample3D)gesture).getGesture(), dimension.width, dimension.height)));
			
	         panel = new JPanel();
	         panel.setOpaque(true);
	         panel.add(label);

	      }
	      return panel;
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.tool.gesturevisualisation.GesturePanel#init(org.ximtec.igesture.core.Gesture)
	 */
	@Override
	public void init(Gesture<?> gesture) {
		this.gesture = gesture;
	}

}
