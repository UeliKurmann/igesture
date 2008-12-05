/*
 * @(#)$Id: Sample.java 2008-12-02 arthurvogels $
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

package org.ximtec.igesture.util;

/** Contains a sample of x- y- and z- acceleration
 * 
 * @author vogelsar
 *
 */
public class Sample {
	
	private double xAcceleration;
	private double yAcceleration;
	private double zAcceleration;
	private long timeStamp;
	
	/** Constructor
	 * 
	 */
	public Sample(){
	}
	
	/** Constructor with assignment of accelerations
	 * 
	 * @param xAcc
	 * @param yAcc
	 * @param zAcc
	 */
	public Sample(double xAcc, double yAcc, double zAcc){
		this.xAcceleration = xAcc;
		this.yAcceleration = yAcc;
		this.zAcceleration = zAcc;		
	}
	
	/** Constructor with assignment of accelerations and timestamp
	 * 
	 * @param xAcceleration
	 * @param yAcceleration
	 * @param zAcceleration
	 * @param timeStamp
	 */
	public Sample(double xAcceleration, double yAcceleration, double zAcceleration, long timeStamp){
		this.xAcceleration = xAcceleration;
		this.yAcceleration = yAcceleration;
		this.zAcceleration = zAcceleration;
		this.timeStamp = timeStamp;
	}
	
		
	/** returns the x acceleration in this sample
	 * 
	 * @return
	 */
	public double getXAcceleration() {
		return xAcceleration;
	}
	
	/** sets the x acceleration in this sample
	 * 
	 * @param acceleration
	 */
	public void setXAcceleration(double acceleration) {
		xAcceleration = acceleration;
	}
	
	/** returns the y acceleration in this sample
	 * 
	 * @return
	 */
	public double getYAcceleration() {
		return yAcceleration;
	}
	
	/** sets the y acceleration in this sample
	 * 
	 * @param acceleration
	 */
	public void setYAcceleration(double acceleration) {
		yAcceleration = acceleration;
	}
	
	/** returns the z acceleration in this sample
	 * 
	 */
	public double getZAcceleration() {
		return zAcceleration;
	}
	
	/** sets the z acceleration in this sample
	 * 
	 * @param acceleration
	 */
	public void setZAcceleration(double acceleration) {
		zAcceleration = acceleration;
	}	
	
	/** returns the time stamp for this sample
	 * 
	 * @return
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/** Sets the time stamp for this sample
	 * 
	 * @param timeStamp
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
