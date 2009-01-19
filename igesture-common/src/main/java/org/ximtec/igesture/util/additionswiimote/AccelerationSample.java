/*
 * @(#)$Id: AccelerationSample.java
 *
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :	Contains a timestamped sample of Wii Remote accelerations.
 * 					WiiAccelerations contains a list of these.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.util.additionswiimote;

/**
 * Contains a sample of x- y- and z- acceleration
 * 
 * @author vogelsar
 * 
 */
public class AccelerationSample {

	private double xAcceleration;
	private double yAcceleration;
	private double zAcceleration;
	private long timeStamp;

	/**
	 * Constructor
	 * 
	 */
	public AccelerationSample() {
	}

	/**
	 * Constructor with assignment of accelerations
	 * 
	 * @param xAcc
	 *            The x acceleration to be set
	 * @param yAcc
	 *            The y acceleration to be set
	 * @param zAcc
	 *            The z acceleration to be set
	 */
	public AccelerationSample(double xAcc, double yAcc, double zAcc) {
		this.xAcceleration = xAcc;
		this.yAcceleration = yAcc;
		this.zAcceleration = zAcc;
	}

	/**
	 * Constructor with assignment of accelerations and timestamp
	 * 
	 * @param xAcceleration
	 *            The x acceleration to be set
	 * @param yAcceleration
	 *            The y acceleration to be set
	 * @param zAcceleration
	 *            The z acceleration to be set
	 * @param timeStamp
	 *            The system time at the moment the sample was recorded
	 */
	public AccelerationSample(double xAcceleration, double yAcceleration,
			double zAcceleration, long timeStamp) {
		this.xAcceleration = xAcceleration;
		this.yAcceleration = yAcceleration;
		this.zAcceleration = zAcceleration;
		this.timeStamp = timeStamp;
	}

	/**
	 * returns the x acceleration in this sample
	 * 
	 * @return The x acceleration in this sample
	 */
	public double getXAcceleration() {
		return xAcceleration;
	}

	/**
	 * sets the x acceleration in this sample
	 * 
	 * @param acceleration
	 *            The x acceleration to be set
	 */
	public void setXAcceleration(double acceleration) {
		xAcceleration = acceleration;
	}

	/**
	 * returns the y acceleration in this sample
	 * 
	 * @return The y acceleration in this sample
	 */
	public double getYAcceleration() {
		return yAcceleration;
	}

	/**
	 * sets the y acceleration in this sample
	 * 
	 * @param acceleration
	 *            The y acceleration to be set
	 */
	public void setYAcceleration(double acceleration) {
		yAcceleration = acceleration;
	}

	/**
	 * returns The z acceleration in this sample
	 * 
	 * @return The z acceleration in this sample
	 */
	public double getZAcceleration() {
		return zAcceleration;
	}

	/**
	 * sets the z acceleration in this sample
	 * 
	 * @param acceleration
	 *            The z acceleration to be set
	 */
	public void setZAcceleration(double acceleration) {
		zAcceleration = acceleration;
	}

	/**
	 * returns the time stamp for this sample
	 * 
	 * @return The time stamp in this sample
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the time stamp for this sample
	 * 
	 * @param timeStamp
	 *            The timestamp that should be set for this sample
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
