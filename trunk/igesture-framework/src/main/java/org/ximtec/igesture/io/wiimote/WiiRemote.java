package org.ximtec.igesture.io.wiimote;

import java.io.IOException;

import control.Wiigee;
import device.Wiimote;
import event.WiimoteListener;

public class WiiRemote {

	private Wiigee wiigee;

	public WiiRemote() {
		try {
			wiigee = Wiigee.getInstance();							//Get WiiGee singleton instance
			Wiimote[] wiimotes = wiigee.getWiimotes();				//Retrieve array of WiiMotes in range
			WiimoteListener listener = new WiiListener(wiimotes[0]);//Create new listener for the first WiiMote
			wiigee.addWiimoteListener(listener);					//Add the listener to WiiGee

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
