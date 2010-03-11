package org.ximtec.igesture.tool.view.devicemanager;

import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.tool.view.devicemanager.temp.ConnectionType;

/**
 * Dummy BlueTooth Device
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class BlueToothReader extends AbstractGestureDevice<Note,Point>{
	
	public BlueToothReader(String address, String name)
	{
		setName(name);
		setDeviceID(address);
		setConnectionType(ConnectionType.CONNECTION_BLUETOOTH);
		setDeviceType("3D");
	}
	
	@Override
	public void connect() {	
	}

	@Override
	public void disconnect() {	
	}

	@Override
	public void clear() {
	}

	@Override
	public void dispose() {	
	}

	@Override
	public List<Point> getChunks() {
		return null;
	}

	@Override
	public Gesture<Note> getGesture() {
		return null;
	}

	@Override
	public void init() {		
	}

}
