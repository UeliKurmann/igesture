package org.ximtec.igesture.io.wiimote;

import java.util.EnumSet;

/**
 *  
 * @author Arthur Vogels, arthur.vogels@gmail.com
 */
public interface WiiMoteHandler {

	/**
	 * Receives wiimote events
	 * 
	 * @param xAcceleration
	 * @param yAcceleration
	 * @param zAcceleration
	 * @param buttons
	 */
	public void handleWiiEvent(double xAcceleration, double yAcceleration,
			double zAcceleration);

}