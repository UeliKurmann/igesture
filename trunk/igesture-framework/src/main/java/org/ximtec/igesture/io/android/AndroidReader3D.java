package org.ximtec.igesture.io.android;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.swing.BorderFactory;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.util.additions3d.AccelerationSample;
import org.ximtec.igesture.util.additions3d.Accelerations;
import org.ximtec.igesture.util.additions3d.Note3D;
import org.ximtec.igesture.util.additions3d.Point3D;
import org.ximtec.igesture.util.additionsandroid.AndroidTools;

public class AndroidReader3D extends AbstractGestureDevice<Note3D, Point3D> implements AndroidListener {

	private Note3D recordedGesture;
	private GestureSample3D gesture;
	private Socket clientSocket;
	private BufferedReader in;
	private Accelerations accelerations;
	
	public BufferedReader getBufferedReader() {
		return in;
	}

	public AndroidReader3D(Socket clientSocket, BufferedReader in) {
		System.out.println("constructor");
		this.clientSocket = clientSocket;
		this.in = in;
		
		this.accelerations = new Accelerations();
		this.recordedGesture = new Note3D();
		this.gesture = new GestureSample3D(this, "", recordedGesture);
	}
	
	@Override
	public void init() {
		System.out.println("init Android Reader 3D");
		
		AndroidStreamer streamer = new AndroidStreamer(this);
		streamer.start();

		System.out.println("started ...");
	}
	
	/**
	 * Returns a copy of the recorded gesture in a GestureSample3D
	 */
	@Override
	public Gesture<Note3D> getGesture() {
		Note3D newGesture = new Note3D();
		newGesture.setAccelerations(this.gesture.getGesture().getAccelerations());
		newGesture.setPoints(this.gesture.getGesture().getPoints());
		return new GestureSample3D(this, this.gesture.getName(), newGesture);
	}

	@Override
	public void disconnect() {
		try {
			in.close();
		} catch (IOException e) {
			System.out.println("Error:"+e);
		}
		try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Error:"+e);
		}
	}

	@Override
	public void connect() {
		init();
	}

	/**
	 * Returns the panel with standard dimension
	 * 
	 * @return
	 */
	public AndroidReader3DPanel getPanel() {
		return getPanel(new Dimension(200, 200));
	}

	/**
	 * Returns the panel
	 * 
	 * @param dimension
	 *            The dimension of the panel
	 * @return The panel belonging to this WiiReader
	 */
	public AndroidReader3DPanel getPanel(Dimension dimension) {
		AndroidReader3DPanel panel = new AndroidReader3DPanel(this);
		panel.setSize(dimension);
		panel.setPreferredSize(dimension);
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		return panel;
	}

	@Override
	public void clear() {
		recordedGesture = new Note3D();
		gesture = new GestureSample3D(this, "", recordedGesture);
		fireGestureEvent(gesture);
	}

	@Override
	public void dispose() {
		removeAllListener();
		clear();
	}

	@Override
	public List<Point3D> getChunks() {
		return null;
	}

	@Override
	public void startGesture() {
		accelerations = new Accelerations();
		recordedGesture = new Note3D();
	}

	@Override
	public void stopGesture() {
		System.out.println("end of gesture -> paint pls... ");
		this.gesture.setGesture(AndroidTools.accelerationsToTraces(this.accelerations));
		gesture.setName("GestureSample3D taken from ANDROID! at system time: "+ System.currentTimeMillis());
		fireGestureEvent(getGesture());
		
	}

	@Override
	public void addCoordinates(double x, double y, double z, long time) {
		System.out.println(x+";"+y+";"+z);
		
		// Create acceleration sample with time stamp
		AccelerationSample sample = new AccelerationSample(x,y,z,time);
		sample.setXAcceleration(x);
		sample.setYAcceleration(y);
		sample.setZAcceleration(z);
		sample.setTimeStamp(time);
		
		// Add sample to accelerations list
		this.accelerations.addSample(sample);
	}
}
