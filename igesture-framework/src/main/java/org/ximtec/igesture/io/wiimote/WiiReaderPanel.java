/*
 * @(#)$Id: WiiReaderPanel.java
 *
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :	Draws the data recorded by the WiiReader into
 * 					planes and graphs on a JPanel.
 *
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         	Reason
 *
 * Dec 16, 2008     arthurvogels    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.io.wiimote;

import java.awt.Cursor;
import java.awt.Graphics;
import java.util.List;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.io.GestureDevicePanel;
import org.ximtec.igesture.util.Note3DTool;

public class WiiReaderPanel extends GestureDevicePanel {

	private WiiReader reader;
	private GestureSample3D gs;

	/**
	 * Constructor
	 * 
	 * @param reader
	 *            The WiiReader this WiiReaderPanel belongs to
	 */
	public WiiReaderPanel(WiiReader reader) {
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
		{
			Note3DTool.paintGesture(gs.getGesture(), g, getWidth(), getHeight(), true);
		}
		else
		{
			Note3DTool.paintStructure(g, getWidth(), getHeight(), true);
		}
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
		//System.err.println("SAMPLE : " + gs.getName());
		this.gs = gs;
//		if(gs != null){
//			System.err.println("WiiReaderPanel.setGesture() Displaying: " + gs.getName());
//		}
//		else {
//			System.err.println("WiiReaderPanel.setGesture() Displaying: null gesture");
//		}
		this.paintComponent(this.getGraphics());
	}
	
	public void clear()
	{
//		System.out.println("clearing");
		paintComponent(getGraphics());
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureEventListener#handleChunks(java.util.List)
	 */
	@Override
	public void handleChunks(List<?> chunks) {		
	}

	/* (non-Javadoc)
	 * @see org.ximtec.igesture.io.GestureEventListener#handleGesture(org.ximtec.igesture.core.Gesture)
	 */
	@Override
	public void handleGesture(Gesture<?> gesture) {
		gs = (GestureSample3D) gesture;
		if(getGraphics() != null)
			paintComponent(getGraphics());
	}

	
	
}
