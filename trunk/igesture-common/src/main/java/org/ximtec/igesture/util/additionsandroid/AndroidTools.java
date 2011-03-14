package org.ximtec.igesture.util.additionsandroid;

import org.ximtec.igesture.util.additions3d.AccelerationSample;
import org.ximtec.igesture.util.additions3d.Accelerations;
import org.ximtec.igesture.util.additions3d.Note3D;
import org.ximtec.igesture.util.additions3d.Point3D;

public class AndroidTools {

	public static Note3D accelerationsToTraces(Accelerations acc) {
		// Variables
		Note3D gesture = new Note3D();
		double xVelocity = 0; // assume initial x velocity is zero
		double yVelocity = 0; // assume initial y velocity is zero
		double zVelocity = 0; // assume initial z velocity is zero
		double xPosition = 0; // assume initial x position is zero
		double yPosition = 0; // assume initial y position is zero
		double zPosition = 0; // assume initial z position is zero
		long tOld = 0; // Timestamp of old sample
		long tNew = 0; // Timestamp of new sample
		double a; // Acceleration
		Point3D point = new Point3D(); // Point in 3d Space

		// Loop through acceleration samples
		for (int i = 0; i < acc.numberOfSamples(); i++) {
			AccelerationSample sample = acc.getSample(i);

			tNew = sample.getTimeStamp();
			if (i == 0) {
				tOld = tNew;
			}
			double t = (tNew - tOld) * 0.001; // convert to seconds
			tOld = tNew;

			a = sample.getXAcceleration();
			xPosition += (a * t);

			a = sample.getYAcceleration();
			yPosition += (a * t);

			a = sample.getZAcceleration();
			zPosition += (a * t);

			point.set(xPosition, yPosition, zPosition); // Put x, y and z
														// positions into point
			point.setTimeStamp(sample.getTimeStamp()); // Add timestamp to point

			System.out.println(point);
			gesture.add(point); // Add new point to gesture
			point = new Point3D();
			sample = new AccelerationSample();

		}
		// Include source accelerations
		gesture.setAccelerations(acc);
		// Return
		return gesture;
	}
}
