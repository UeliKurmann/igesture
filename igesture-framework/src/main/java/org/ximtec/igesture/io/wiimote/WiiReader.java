/*
 * @(#)$Id: WiiReader.java
 *
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :	Listener for WiiMote input. Can have a WiiReaderpanel attached.
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

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.util.additions3d.Point3D;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;
import org.ximtec.igesture.util.additionswiimote.AccelerationSample;
import org.ximtec.igesture.util.additionswiimote.WiiAccelerations;
import org.ximtec.igesture.util.additionswiimote.WiiMoteTools;

import control.Wiigee;

import device.Wiimote;

import event.InfraredEvent;
import event.WiimoteAccelerationEvent;
import event.WiimoteButtonPressedEvent;
import event.WiimoteButtonReleasedEvent;
import event.WiimoteListener;
import event.WiimoteMotionStartEvent;
import event.WiimoteMotionStopEvent;

public class WiiReader extends
		AbstractGestureDevice<RecordedGesture3D, Point3D> implements
		WiimoteListener {

	// The panel to draw planes and graphs on
	private WiiReaderPanel currentPanel;
	// Instance of WiiGee for WiiMote device
	private Wiigee wiigee;
	// The WiiMote this listener is listening to
	private Wiimote wiimote;
	// The RecordedGesture3D that will contain the position data
	private RecordedGesture3D recordedGesture;
	// The GestureSample3D that will contain recordedGesture
	private GestureSample3D gesture;
	// List of recorded accelerations from the wiimote
	private WiiAccelerations accelerations;
	// Indicator if accelerations recording is in progress
	private boolean recording;
	// Button that is used to start and stop recording
	private int recordButton = WiimoteButtonPressedEvent.BUTTON_B;

	/**
	 * Constructor
	 */
	public WiiReader() {

		this.accelerations = new WiiAccelerations();
		this.currentPanel = new WiiReaderPanel(this);
		this.recordedGesture = new RecordedGesture3D();
		this.gesture = new GestureSample3D("", recordedGesture);

	}

	/**
	 * Returns the panel with standard dimension
	 * 
	 * @return
	 */
	public WiiReaderPanel getPanel() {
		return getPanel(new Dimension(200, 200));
	}

	/**
	 * Returns the panel
	 * 
	 * @param dimension
	 *            The dimension of the panel
	 * @return The panel belonging to this WiiReader
	 */
	public WiiReaderPanel getPanel(Dimension dimension) {
		if (currentPanel == null) {
			WiiReaderPanel panel = new WiiReaderPanel(this);
			panel.setSize(dimension);
			panel.setPreferredSize(dimension);
			panel.setOpaque(true);
			panel.setBackground(Color.WHITE);
			currentPanel = panel;
		}
		return currentPanel;

	}

	/**
	 * Clears the WiiReader
	 */
	@Override
	public void clear() {
		recordedGesture = new RecordedGesture3D();
		if (currentPanel != null) {
			currentPanel.paintComponent(currentPanel.getGraphics());
		}
	}

	/**
	 * 
	 */
	@Override
	public void dispose() {
		removeAllListener();
		clear();
	}

	/**
	 * 
	 */
	@Override
	public List<Point3D> getChunks() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns a copy of the recorded gesture in a GestureSample3D
	 */
	@Override
	public Gesture<RecordedGesture3D> getGesture() {
		RecordedGesture3D newGesture = new RecordedGesture3D();
		newGesture.setAccelerations(this.gesture.getGesture()
				.getAccelerations());
		newGesture.setPoints(this.gesture.getGesture().getPoints());
		return new GestureSample3D(this.gesture.getName(), newGesture);
	}

	/**
	 * Initializes the WiiReader, starts device detection etc.
	 */
	@Override
	public void init() {
		System.out.println("Initializing WiiReader...");
		try {
			// Get WiiGee instance
			wiigee = Wiigee.getInstance();
			// Retrieve array of WiiMotes in range
			Wiimote[] wiimotes = wiigee.getWiimotes();
			// If a wiimote was found in range
			if (wiimotes[0] != null)
				// Take the first wiimote in the list
				this.wiimote = wiimotes[0];
			// Add this as a listener to WiiGee
			wiigee.addWiimoteListener(this);
			System.out.println("WiiReader added as a listener to wiigee.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is executed when an acceleration event is received from the
	 * wiimote.
	 */
	@Override
	public void accelerationReceived(WiimoteAccelerationEvent event) {
		if (recording) {
			// Create acceleration sample with time stamp
			AccelerationSample sample = new AccelerationSample(event.getX(),
					event.getY(), event.getZ(), System.currentTimeMillis());
			// Add sample to accelerations list
			this.accelerations.addSample(sample);
		}
	}

	/**
	 * When the recording button is pressed this method starts recording
	 */
	@Override
	public void buttonPressReceived(WiimoteButtonPressedEvent event) {
		// If recording button is pressed
		if (event.getButton() == recordButton) {
			// If not already recording
			if (!recording) {
				accelerations.clear(); // Clear accelerations list
				recording = true; // Start recording
			}
		}
	}

	/**
	 * When the button is released the recoding stops and the recorded gesture
	 * is displayed on the panel
	 */
	@Override
	public void buttonReleaseReceived(WiimoteButtonReleasedEvent event) {
		// If the device is recording
		if (recording) {
			// Convert accelerations list to a gesture
			this.gesture.setGesture(WiiMoteTools
					.accelerationsToTraces(this.accelerations));
			gesture
					.setName("GestureSample3D taken from WiiMote at system time: "
							+ System.currentTimeMillis());

			// Paint the gesture on the panel
			this.currentPanel.paintComponent(currentPanel.getGraphics());
			// Indicate recording stop
			recording = false;
			// Vibration to confirm gesture recording
			try {
				wiimote.vibrateForTime(200);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void infraredReceived(InfraredEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void motionStartReceived(WiimoteMotionStartEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void motionStopReceived(WiimoteMotionStopEvent event) {
		// TODO Auto-generated method stub

	}

	/**
	 * Disconnects the WiiMote
	 */
	public void disconnect() {
		if (this.wiimote != null)
			this.wiimote.disconnect();
	}

}
