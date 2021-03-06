/**
 * 
 */
package org.ximtec.igesture.io.tuio;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.io.GestureDevicePanel;
import org.ximtec.igesture.util.Note3DTool;
import org.ximtec.igesture.util.additions3d.Note3D;

/**
 * @author Bjorn Puype, bpuype@gmail.com
 *
 */
public class TuioReaderPanel extends GestureDevicePanel {
	
	private ITuioReader reader;
	private GestureSample gestureSample;
	private GestureSample3D gestureSample3D;
	
	public static final int TYPE_2D = 1;
	public static final int TYPE_3D = 2;
	
	private int type;
	
	public TuioReaderPanel()
	{
		gestureSample = new GestureSample("", new Note());
		gestureSample3D = new GestureSample3D("", new Note3D());
	}
	
	public TuioReaderPanel(ITuioReader device, int type)
	{
		reader = device;
		gestureSample = new GestureSample("", new Note());
		gestureSample3D = new GestureSample3D("", new Note3D());
		this.type = type;
	}
	
	public void clear()
	{
		gestureSample = new GestureSample("", new Note());
		gestureSample3D = new GestureSample3D("", new Note3D());
	}
	
	public GestureSample getGesture()
	{
		return gestureSample;
	}
	
	public GestureSample3D getGesture3D()
	{
		return gestureSample3D;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		if(type == TYPE_2D)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.BLACK);
			if(gestureSample != null)//gestureSample != new GestureSample("", new Note())
			{
				gestureSample.getGesture().scaleTo(getWidth()-10, getHeight()-10);
				gestureSample.getGesture().moveTo(5,5);
				gestureSample.getGesture().paint(g);
			}
				
//			g.drawString("2D", getWidth()/2, getHeight()/2);
		}
		else if(type == TYPE_3D)
		{
			if(gestureSample3D != null) //gestureSample3D != new GestureSample3D("", newNote3D())
				Note3DTool.paintGesture(gestureSample3D.getGesture(), g, getWidth(), getHeight(), true);
			else
				Note3DTool.paintStructure(g, getWidth(), getHeight(), true);
		}
		else
		{
			g.drawString("TO IMPLEMENT", 0, getHeight()/2);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureEventListener#handleChunks(java.util.List)
	 */
	@Override
	public void handleChunks(List<?> chunks) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureEventListener#handleGesture(org.ximtec.igesture.core.Gesture)
	 */
	@Override
	public void handleGesture(Gesture<?> gesture) {
		if(gesture instanceof GestureSample)
		{
			gestureSample = (GestureSample) gesture;
			type = TYPE_2D;
		}
		else if(gesture instanceof GestureSample3D)
		{
			gestureSample3D = (GestureSample3D) gesture;
			type = TYPE_3D;
		}
		if(getGraphics() != null)
			paintComponent(getGraphics());
	}

}
