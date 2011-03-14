package org.ximtec.igesture.io.android;



/**
 * 
 * @author Johan Bas
 */
public interface AndroidListener {
	
	public void startGesture();
	
	public void stopGesture();
	
	public void addCoordinates(double x, double y, double z, long time);
}
