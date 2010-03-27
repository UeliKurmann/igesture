/**
 * 
 */
package org.ximtec.igesture.tool.gesturevisualisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.ximtec.igesture.io.GestureDevicePanel;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class InputComponentPanel extends JPanel {

	private GestureDevicePanel devicePanel;
	
	public GestureDevicePanel getGestureDevicePanel()
	{
		return devicePanel;
	}
	
	public void setGestureDevicePanel(GestureDevicePanel panel)
	{
		devicePanel = panel;
	}
	
}
