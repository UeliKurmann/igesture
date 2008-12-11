package org.ximtec.igesture.io.wiimote;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

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
		AbstractGestureDevice<RecordedGesture3D, Point3D> implements WiimoteListener {

	private WiiReaderPanel currentPanel;

	private Wiigee wiigee;
	private Wiimote wiimote; 						// The WiiMote this listener is listening to
	private RecordedGesture3D recordedGesture;		// The RecordedGesture3D that will contain the position data
	private GestureSample3D gesture;				// The GestureSample3D that will contain recordedGesture
	private WiiAccelerations accelerations;			// List of recorded accelerations from the wiimote
	private boolean recording;						// Indicates if accelerations recording is in progress
	private int recordButton = WiimoteButtonPressedEvent.BUTTON_B; //Button that is used to start and stop recording

	
	
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
	 * @return
	 */
	public WiiReaderPanel getPanel(Dimension dimension) {
		if(currentPanel == null){
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
		recordedGesture = new RecordedGesture3D();
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

	/**
	 * 
	 */
	@Override
	public List<Point3D> getChunks() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the recorded gesture in a GestureSample3D
	 */
	@Override
	public Gesture<RecordedGesture3D> getGesture() {
		return this.gesture;
	}

	@Override
	public void init() {
		try {
			wiigee = Wiigee.getInstance();							//Get WiiGee singleton instance
			Wiimote[] wiimotes = wiigee.getWiimotes();				//Retrieve array of WiiMotes in range
			if(wiimotes[0]!=null)									//If a wiimote was found in range
				this.wiimote = wiimotes[0];							//Take the first wiimote in the list
			wiigee.addWiimoteListener(this);						//Add this as a listener to WiiGee
			System.out.println("WiiReader added as a listener to wiigee.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("WiiRemote created");
	}

	
	/**
	 * This method is executed when an acceleration event is received from
	 * the wiimote.
	 */
	@Override
	public void accelerationReceived(WiimoteAccelerationEvent event) {
		if(recording){
			Sample sample = new Sample(event.getX(), event.getY(), event.getZ(),
					System.currentTimeMillis()); //Create acceleration sample with time stamp
			this.accelerations.addSample(sample); // Add sample to accelerations list
		}
	}

	
	/**
	 * When a button is pressed this method starts or stops recording
	 * accelerations.
	 * 
	 * 
	 */
	@Override
	public void buttonPressReceived(WiimoteButtonPressedEvent event) {
		if(event.getButton() == recordButton){	//If recording button is pressed	
			if (!recording) { //If not already recording, start recording
				recording = true;
			}		
			else{ //If already recording, stop recording
				gesture.setGesture(WiiMoteTools.accelerationsToTraces(this.accelerations)); //Convert accelerations list to a gesture
				
				//this.currentPanel.redrawGesture();
				//this.currentPanel.drawLine(new Point(10,10), new Point(33,60)); //test
				this.currentPanel.paintComponent(currentPanel.getGraphics());
				
				accelerations.clear(); // Clear accelerations list
				recording = false;
				//Vibration to confirm gesture recording
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
