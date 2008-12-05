/*
 * @(#)$Id: Point3D.java 2008-12-02 arthurvogels $
 *
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :	A timestamped point in 3D space. A RecordedGesture3D
 * 					contains a list of these.
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

public class Point3D {

	private long timeStamp = 0;
	private double x;
	private double y;
	private double z;

	/**
	 * Constructor
	 * 
	 */
	public Point3D() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param timeStamp
	 */
	public Point3D(double x, double y, double z, long timeStamp) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.timeStamp = timeStamp;
	}

	/**
	 * returns the time stamp for this point
	 * 
	 * @return
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Set x, y and z
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void set(double x, double y, double z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	/**
	 * sets the timestamp for this point
	 * 
	 * @param timeStamp
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * Returns true if the point has a timestamp, otherwise false
	 * 
	 * @return
	 */
	public boolean hasTimeStamp() {
		return (timeStamp != 0);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

}
