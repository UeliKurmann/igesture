/*
 * @(#)$Id: WiiReader.java
 *
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :	Listener for WiiMote input. Can have a WiiReaderpanel attached.
 *
 * There are three ways to use the WiiReader.
 * 1) Manually
 * After performing the gesture, you can get the gesture with getGesture() and do the recognition.
 * 2) GestureEventListener interface
 * Register with the WiiReader as a GestureEventListener, it will notify you when a gesture was performed. The recognition of the performed
 * gesture can then be done.
 * 3) Configure the WiiReader with a Recogniser
 * When a gesture was performed, the recogniser will automatically try to recognise the gesture and notify GestureHandlers.
 *  
 * General steps to use:
 * 	1. create the WiiReader
 * 	2. connect to a wii remote
 *  3. In case of GestureEventListener : register as a listener
 *     In case of Recogniser : set this TuioReaders recogniser
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

package org.ximtec.igesture.algorithm.rubine3d.tools;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.bluetooth.RemoteDevice;
import javax.commerce.base.Constants;
import javax.swing.BorderFactory;

import org.sigtec.util.Constant;
import org.wiigee.control.WiimoteWiigee;
import org.wiigee.device.Wiimote;
import org.wiigee.event.AccelerationEvent;
import org.wiigee.event.AccelerationListener;
import org.wiigee.event.ButtonListener;
import org.wiigee.event.ButtonPressedEvent;
import org.wiigee.event.ButtonReleasedEvent;
import org.wiigee.event.MotionStartEvent;
import org.wiigee.event.MotionStopEvent;
//@import org.wiigee.device.Wiimote;

import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.util.additions3d.AccelerationSample;
import org.ximtec.igesture.util.additions3d.Accelerations;
import org.ximtec.igesture.util.additions3d.Point3D;
import org.ximtec.igesture.util.additions3d.Note3D;
import org.ximtec.igesture.util.additionswiimote.WiiMoteTools;

public class WiiReader extends
		AbstractGestureDevice<Note3D, Point3D> implements
		ButtonListener, AccelerationListener {

	private static final Logger LOGGER = Logger.getLogger(WiiReader.class.getName());
	
	// The panel to draw planes and graphs on
	private WiiReaderPanel currentPanel;
	// Instance of WiiGee for WiiMote device
	private WiimoteWiigee wiigee;
	// The WiiMote this listener is listening to
	private Wiimote wiimote;
	// The Note3D that will contain the position data
	private Note3D recordedGesture;
	// The GestureSample3D that will contain recordedGesture
	private GestureSample3D gesture;
	// List of recorded accelerations from the wiimote
	private Accelerations accelerations;
	// Indicator if accelerations recording is in progress
	private boolean recording;
	// Button that is used to start and stop recording
	private int recordButton = org.wiigee.device.Wiimote.BUTTON_B;

	private Recogniser recogniser;

	/**
	 * Constructor
	 */
	public WiiReader() {

		this.accelerations = new Accelerations();
		this.currentPanel = new WiiReaderPanel(this);
		this.recordedGesture = new Note3D();
		this.gesture = new GestureSample3D(this,"", recordedGesture);
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
			panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			currentPanel = panel;
		}
		return currentPanel;

	}

	/**
	 * Clears the WiiReader
	 */
	@Override
	public void clear() {
		recordedGesture = new Note3D();
		gesture = new GestureSample3D(this,"", recordedGesture);
		if (currentPanel != null) {
			currentPanel.clear();
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

	@Override
	public List<Point3D> getChunks() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns a copy of the recorded gesture in a GestureSample3D
	 */
	@Override
	public Gesture<Note3D> getGesture() {
		Note3D newGesture = new Note3D();
		newGesture.setAccelerations(this.gesture.getGesture()
				.getAccelerations());
		newGesture.setPoints(this.gesture.getGesture().getPoints());
		return new GestureSample3D(this,this.gesture.getName(), newGesture);
	}

	/**
	 * Initializes the WiiReader, starts device detection etc.
	 */
	@Override
	public void init() {
		System.out.println("Initializing WiiReader...");
		try {
			
			
			// Create WiiGee instance
			wiigee = new WiimoteWiigee();
			// Retrieve array of WiiMotes in range
			Wiimote[] wiimotes = wiigee.getDevices();
			// If a wiimote was found in range
			if (wiimotes[0] != null)
				// Take the first wiimote in the list
				this.wiimote = wiimotes[0];
			//diable WiiGee internal training and recognition
			wiimote.setRecognitionButton(0);
			wiimote.setTrainButton(0);
			wiimote.setCloseGestureButton(0);			
			// Add this as a button listener
			wiimote.addButtonListener(this);
			// Add this as a gesture listener
			wiimote.addAccelerationListener(this);
			System.out.println("WiiReader added as a listener for gesture and button events.");
			
			//MODIFY >
			setIsConnected(true);
			//MODIFY <
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Could not connect to WiiMote. Please make the device discoverable and try to reconnect.",e);//MODIFY
		}
	}

	/**
	 * Disconnects the WiiMote
	 */
	public void disconnect() {
		if (this.wiimote != null)
		{
			this.wiimote.disconnect();
		}
	}

	/**
	 * When the recording button is pressed this method starts recording
	 */
	@Override
	public void buttonPressReceived(ButtonPressedEvent event) {
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
	public void buttonReleaseReceived(ButtonReleasedEvent event) {
		// If the device is recording
		if (recording) {
			// Convert accelerations list to a gesture
			this.gesture.setGesture(WiiMoteTools.accelerationsToTraces(this.accelerations));
			gesture.setName("GestureSample3D taken from WiiMote at system time: "
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
			fireGestureEvent(getGesture());
			if(recogniser != null)
			{
				recogniser.recognise(gesture);
			}
		}
		
	}

	/**
	 * This method is executed when an acceleration event is received from the
	 * wiimote.
	 */
	@Override
	public void accelerationReceived(AccelerationEvent event) {
		if (recording) {
			// Create acceleration sample with time stamp
			AccelerationSample sample = new AccelerationSample(event.getX(),
					event.getY(), event.getZ(), System.currentTimeMillis());
			// Add sample to accelerations list
			this.accelerations.addSample(sample);
		}
	}

	@Override
	public void motionStartReceived(MotionStartEvent event) {		
	}

	@Override
	public void motionStopReceived(MotionStopEvent event) {
	}
	
	public void setRecogniser(Recogniser recogniser)
	{
		this.recogniser = recogniser;
	}
	
	@Override
	public void connect() {
		init();
	}
}
