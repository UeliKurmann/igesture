package org.ximtec.igesture.util;

public class WiiMoteTools {

	/** Converts the acceleration data from the WiiMote into 3D vectors in a RecordedGesture3D object
	 * 
	 * @param acc the acceleration data coming from the WiiMote controller
	 * @return
	 */
	public static RecordedGesture3D accelerationsToTraces(WiiAccelerations acc){
		//Variables
		RecordedGesture3D gesture = new RecordedGesture3D();
		double xVelocity = 0; 									//assume initial x velocity is zero
		double yVelocity = 0; 									//assume initial y velocity is zero
		double zVelocity = 0; 									//assume initial z velocity is zero
		double xPosition = 0; 									//assume initial x position is zero
		double yPosition = 0; 									//assume initial y position is zero
		double zPosition = 0; 									//assume initial z position is zero
		double t = 1 / acc.getSampleFrequency(); 				//Time between samples in seconds
		double a; 												//Acceleration
		Point3D point = new Point3D();							//Point in 3d Space
		
		//Loop through acceleration samples
		for(int i = 0; i<acc.numberOfSamples(); i++){
			Sample sample = acc.getSample(i);							//Get current sample
			
			a = sample.getXAcceleration(); 								//Get X acceleration for current sample
			xPosition = ((a * t * t) / 2) + xVelocity * t + xPosition; 	//Compute new X position
			xVelocity = xVelocity + (a * t); 							//Compute new X velocity
			
			a = sample.getYAcceleration(); 								//Get Y acceleration for current sample
			yPosition = ((a * t * t) / 2) + yVelocity * t + yPosition; 	//Compute new Y position
			yVelocity = yVelocity + (a * t); 							//Compute new Y velocity
			
			a = sample.getZAcceleration(); 								//Get Z acceleration for current sample
			zPosition = ((a * t * t) / 2) + zVelocity * t + zPosition; 	//Compute new Z position
			zVelocity = zVelocity + (a * t); 							//Compute new Z velocity

			point.set(xPosition, yPosition, zPosition);					//Put x, y and z positions into point
			point.setTimeStamp(sample.getTimeStamp());					//Add timestamp to point
			
			gesture.add(point);											//Add new point to gesture
			
			
		}
		return gesture;
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
