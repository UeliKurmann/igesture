package org.ximtec.igesture.io.android;

import java.awt.Cursor;
import java.awt.Graphics;
import java.util.List;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.io.GestureDevicePanel;
import org.ximtec.igesture.io.wiimotionplus.WiiMotionPlusReader;
import org.ximtec.igesture.util.Note3DTool;

public class AndroidReader3DPanel extends GestureDevicePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AndroidReader3D reader;
	private GestureSample3D gs;

	/**
	 * Constructor
	 * 
	 * @param reader
	 *            The WiiReader this WiiReaderPanel belongs to
	 */
	public AndroidReader3DPanel(AndroidReader3D reader) {
		this.reader = reader;
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}	
	
	/**
	 * Override of the JPanel paintComponent method. Paints the gesture from
	 * reader in 3 planes onto the Graphics object
	 */
	@Override
	public void paintComponent(Graphics g) {
		if(gs != null) 
			Note3DTool.paintGesture(gs.getGesture(), g, getWidth(), getHeight(), true);
		else 
			Note3DTool.paintStructure(g, getWidth(), getHeight(), true);
		
	}

	/**
	 * Returns the gesture drawn by this panel
	 * 
	 * @return The gesture drawn by this panel
	 */
	public GestureSample3D getGesture() {
		return gs;
	}

	/**
	 * Sets the gesture to be drawn by this panel
	 * 
	 * @param gs
	 *            The gesture to be drawn by this panel
	 */
	public void setGesture(GestureSample3D gs) {
		this.gs = gs;
		this.paintComponent(this.getGraphics());
	}
	
	@Override
	public void handleGesture(Gesture<?> gesture) {
		gs = (GestureSample3D) gesture;
		if(getGraphics() != null)
			paintComponent(getGraphics());
	}

	@Override
	public void handleChunks(List<?> chunks) { }

	public void clear() {
		paintComponent(getGraphics());
	}
}
