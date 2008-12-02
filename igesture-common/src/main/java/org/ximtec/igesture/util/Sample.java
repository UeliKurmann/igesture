package org.ximtec.igesture.util;

/** Contains a sample of x- y- and z- acceleration
 * 
 * @author vogelsar
 *
 */
public class Sample {
	
	private float xAcceleration;
	private float yAcceleration;
	private float zAcceleration;
	private long timeStamp;
	
	/** Constructor
	 * 
	 */
	public Sample(){
	}
	
	/** Constructor with assignment of accelerations
	 * 
	 * @param xAcceleration
	 * @param yAcceleration
	 * @param zAcceleration
	 */
	public Sample(float xAcceleration, float yAcceleration, float zAcceleration){
		this.xAcceleration = xAcceleration;
		this.yAcceleration = yAcceleration;
		this.zAcceleration = zAcceleration;		
	}
	
	/** Constructor with assignment of accelerations and timestamp
	 * 
	 * @param xAcceleration
	 * @param yAcceleration
	 * @param zAcceleration
	 * @param timeStamp
	 */
	public Sample(float xAcceleration, float yAcceleration, float zAcceleration, long timeStamp){
		this.xAcceleration = xAcceleration;
		this.yAcceleration = yAcceleration;
		this.zAcceleration = zAcceleration;
		this.timeStamp = timeStamp;
	}
	
		
	/** returns the x acceleration in this sample
	 * 
	 * @return
	 */
	public float getXAcceleration() {
		return xAcceleration;
	}
	
	/** sets the x acceleration in this sample
	 * 
	 * @param acceleration
	 */
	public void setXAcceleration(float acceleration) {
		xAcceleration = acceleration;
	}
	
	/** returns the y acceleration in this sample
	 * 
	 * @return
	 */
	public float getYAcceleration() {
		return yAcceleration;
	}
	
	/** sets the y acceleration in this sample
	 * 
	 * @param acceleration
	 */
	public void setYAcceleration(float acceleration) {
		yAcceleration = acceleration;
	}
	
	/** returns the z acceleration in this sample
	 * 
	 */
	public float getZAcceleration() {
		return zAcceleration;
	}
	
	/** sets the z acceleration in this sample
	 * 
	 * @param acceleration
	 */
	public void setZAcceleration(float acceleration) {
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
