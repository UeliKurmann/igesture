package org.ximtec.igesture.tool.view.devicemanager;

import java.util.List;

import javax.bluetooth.RemoteDevice;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.util.Constant;

/**
 * Dummy BlueTooth Device
 * 
 * @author Bjorn Puype, bpuype@gmail.com
 * 
 */
public class BlueToothReader extends AbstractGestureDevice<Note, Point> {

	@SuppressWarnings("unused")
	private RemoteDevice device;

	public BlueToothReader(String address, String name, RemoteDevice device) {
		setName(name);
		setDeviceID(address);
		setConnectionType(Constant.CONNECTION_BLUETOOTH);
		setDeviceType(Constant.TYPE_2D);

		this.device = device;
	}

	@Override
	public void connect() {}

	@Override
	public void disconnect() { }

	@Override
	public void clear() { }

	@Override
	public void dispose() {	}

	@Override
	public List<Point> getChunks() {
		return null;
	}

	@Override
	public Gesture<Note> getGesture() {
		return null;
	}

	@Override
	public void init() { }

}
