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
			wiigee.addWiimoteListener(new WiiListener(wiimotes[0]));//Add new listener to WiiGee
			System.out.println("WiiListener added to wiigee.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
