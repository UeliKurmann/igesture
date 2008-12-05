package org.ximtec.igesture.io.wiimote;

import java.io.IOException;

import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.util.RecordedGesture3D;
import org.ximtec.igesture.util.Sample;
import org.ximtec.igesture.util.WiiAccelerations;
import org.ximtec.igesture.util.WiiMoteTools;

import device.Wiimote;

import event.InfraredEvent;
import event.WiimoteAccelerationEvent;
import event.WiimoteButtonPressedEvent;
import event.WiimoteButtonReleasedEvent;
import event.WiimoteListener;
import event.WiimoteMotionStartEvent;
import event.WiimoteMotionStopEvent;

public class WiiListener implements WiimoteListener {

	private Wiimote wiimote; 						// The WiiMote this listener is listening to
	private RecordedGesture3D recordedGesture;
	private GestureSample3D gesture;
	private WiiAccelerations accelerations;			// List of recorded accelerations from the wiimote
	private boolean recording;						// Indicates if accelerations recording is in progress
	private int recordButton = WiimoteButtonPressedEvent.BUTTON_B; //Button that is used to start and stop recording

	/**
	 * Constructor
	 * 
	 * @param wiimote
	 */
	public WiiListener(Wiimote wiimote) {
		this.wiimote = wiimote;
		this.accelerations = new WiiAccelerations();
		this.recordedGesture = new RecordedGesture3D();
		this.gesture = new GestureSample3D("gesture from wiimote",
				recordedGesture);
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
				accelerations.clear(); // Clear accelerations list
				recording = false;
				//Vibration to confirm gesture recording
				try {
					wiimote.vibrateForTime(100);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Not used
	 */
	@Override
	public void buttonReleaseReceived(WiimoteButtonReleasedEvent event) {
		// TODO Auto-generated method stub

	}

	/**
	 * Not used
	 */
	@Override
	public void infraredReceived(InfraredEvent event) {
		// TODO Auto-generated method stub

	}

	/**
	 * Not used
	 */
	@Override
	public void motionStartReceived(WiimoteMotionStartEvent event) {
		// TODO Auto-generated method stub

	}

	/**
	 * Not used
	 */
	@Override
	public void motionStopReceived(WiimoteMotionStopEvent event) {
		// TODO Auto-generated method stub

	}

}
