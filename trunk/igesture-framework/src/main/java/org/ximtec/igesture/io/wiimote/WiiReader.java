/*
 * @(#)$Id: WiiReader.java 2008-12-16 arthurvogels $
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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
import org.ximtec.igesture.util.Point3D;
import org.ximtec.igesture.util.RecordedGesture3D;
import org.ximtec.igesture.util.Sample;
import org.ximtec.igesture.util.WiiAccelerations;
import org.ximtec.igesture.util.WiiMoteTools;

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

	private WiiReaderPanel currentPanel; // The panel to draw planes and graphs
											// on

	private Wiigee wiigee; // Instance of WiiGee for WiiMote device
	private Wiimote wiimote; // The WiiMote this listener is listening to
	private RecordedGesture3D recordedGesture; // The RecordedGesture3D that
												// will contain the position
												// data
	private GestureSample3D gesture; // The GestureSample3D that will contain
										// recordedGesture
	private WiiAccelerations accelerations; // List of recorded accelerations
											// from the wiimote
	private boolean recording; // Indicates if accelerations recording is in
								// progress
	private int recordButton = WiimoteButtonPressedEvent.BUTTON_B; // Button
																	// that is
																	// used to
																	// start and
																	// stop
																	// recording

	/**
	 * Constructor
	 */
	public WiiReader() {

		this.accelerations = new WiiAccelerations();
		this.currentPanel = new WiiReaderPanel(this);
		this.recordedGesture = new RecordedGesture3D();
		this.gesture = new GestureSample3D("gesture from wiimote",
				recordedGesture);

	}

	/**
	 * Returns the singleton panel
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
	 *  Returns the recorded gesture in a GestureSample3D
	 */
	@Override
	public Gesture<RecordedGesture3D> getGesture() {
		return this.gesture;
	}

	/**
	 * Initializes the WiiReader, starts device detection etc.
	 */
	@Override
	public void init() {
		System.out.println("Initializing WiiReader...");
		try {
			wiigee = Wiigee.getInstance(); // Get WiiGee singleton instance
			Wiimote[] wiimotes = wiigee.getWiimotes(); // Retrieve array of
														// WiiMotes in range
			if (wiimotes[0] != null) // If a wiimote was found in range
				this.wiimote = wiimotes[0]; // Take the first wiimote in the
											// list
			wiigee.addWiimoteListener(this); // Add this as a listener to WiiGee
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
			Sample sample = new Sample(event.getX(), event.getY(),
					event.getZ(), System.currentTimeMillis()); // Create
																// acceleration
																// sample with
																// time stamp
			this.accelerations.addSample(sample); // Add sample to accelerations
													// list
		}
	}

	/**
	 * When a button is pressed this method starts or stops recording
	 * accelerations.
	 */
	@Override
	public void buttonPressReceived(WiimoteButtonPressedEvent event) {
		if (event.getButton() == recordButton) { // If recording button is
													// pressed
			if (!recording) { // If not already recording, start recording
				accelerations.clear(); // Clear accelerations list
				recording = true;
			} else { // If already recording, stop recording
				gesture.setGesture(WiiMoteTools
						.accelerationsToTraces(this.accelerations)); // Convert
																		// accelerations
																		// list
																		// to a
																		// gesture
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
	}

	@Override
	public void buttonReleaseReceived(WiimoteButtonReleasedEvent event) {
		// TODO Auto-generated method stub

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

}
