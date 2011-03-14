package org.ximtec.igesture.io.android;

import java.io.IOException;

public class AndroidStreamer extends Thread {

	private boolean recording = false;
	private AndroidReader3D device;

	public AndroidStreamer(AndroidReader3D device) {
		this.device = device;
	}
	
    @Override
    public void run() {
    	String inputLine;
		try {
			while ((inputLine = device.getBufferedReader().readLine()) != null) {
				if (inputLine.equals("Bye.")) {
					System.out.println("Bye.");
					break;
				} else if(inputLine.startsWith("S")) {
					this.device.startGesture();
					recording = true;
				} else if(inputLine.startsWith("Q")) {
					// end gesture
					this.device.stopGesture();
					recording = false;
				} else {
					if(recording) {
						String[] coordinated = inputLine.split(";");

						double x = Double.parseDouble(coordinated[0]);
						double y = Double.parseDouble(coordinated[1]);
						double z = Double.parseDouble(coordinated[2]);
						long time = Long.parseLong(coordinated[3]);
					
						this.device.addCoordinates(x,y,z,time);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
