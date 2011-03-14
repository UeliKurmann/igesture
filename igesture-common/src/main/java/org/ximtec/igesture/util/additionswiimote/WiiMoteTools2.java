/*
 * @(#)$Id: WiiMoteTools.java
 *
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :	Provides methods for use with the Wii Remote.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         	Reason
 *
 * Dec 02, 2008     arthurvogels    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.util.additionswiimote;

import org.ximtec.igesture.util.additions3d.Point3D;
import org.ximtec.igesture.util.additions3d.Note3D;
import org.ximtec.igesture.util.additions3d.AccelerationSample;
import org.ximtec.igesture.util.additions3d.Accelerations;

public class WiiMoteTools2 {


	/** 
	 * Converts the acceleration data from the WiiMote into timestamped 3D positions in a Note3D object
	 * 
	 * @param acc the acceleration data coming from the WiiMote controller
	 * @return
	 */
	public static Note3D accelerationsToTraces(Accelerations acc){
		//Variables
		Note3D gesture = new Note3D();

		double xPosition = 0; 									//assume initial x position is zero
		double yPosition = 0; 									//assume initial y position is zero
		double zPosition = 0; 									//assume initial z position is zero
		long tOld = 0; 												//Timestamp of old sample
		long tNew = 0;												//Timestamp of new sample
		double a; 												//Acceleration
		Point3D point = new Point3D();							//Point in 3d Space
		
		float[] gravity = new float[3];
		float[] accelerometerValues = new float[3];
		float[] linear_acceleration = new float[3];
		
		//Loop through acceleration samples
		for(int i = 0; i<acc.numberOfSamples(); i++){
			AccelerationSample sample = acc.getSample(i);							//Get current sample
			accelerometerValues[0] = (float) sample.getXAcceleration() * (-1);
			accelerometerValues[1] = (float) sample.getYAcceleration() * (-1);
			accelerometerValues[2] = (float) sample.getZAcceleration();
			
	        // alpha is calculated as t / (t + dT)          
			// with t, the low-pass filter's time-constant          
			// and dT, the event delivery rate          

			final float alpha = (float) 0.2;  // to check        
			gravity[0] = alpha * gravity[0] + (1 - alpha) * accelerometerValues[0];          
			gravity[1] = alpha * gravity[1] + (1 - alpha) * accelerometerValues[1];          
			gravity[2] = alpha * gravity[2] + (1 - alpha) * accelerometerValues[2];          

			linear_acceleration[0] = accelerometerValues[0] - gravity[0];          
			linear_acceleration[1] = accelerometerValues[1] - gravity[1];          
			linear_acceleration[2] = accelerometerValues[2] - gravity[2];
			
			tNew = sample.getTimeStamp();
			if (i==0) { 
				tOld = tNew;
			} else {
				double t = (tNew - tOld) * 0.001; // convert to seconds
				tOld = tNew;												
				
				a = sample.getXAcceleration(); 
				xPosition += (a * t);
				
				a = sample.getYAcceleration();
				yPosition += (a * t);
				
				a= sample.getZAcceleration();
				zPosition += (a * t);
				
	
				point.set(xPosition, yPosition, zPosition);					//Put x, y and z positions into point
				point.setTimeStamp(sample.getTimeStamp());					//Add timestamp to point
				
				System.out.println(point);
				gesture.add(point);											//Add new point to gesture
				point = new Point3D();
				sample = new AccelerationSample();
			}
		}
		//Include source accelerations
		gesture.setAccelerations(acc);
		//Return
		return gesture;
	}
	
}