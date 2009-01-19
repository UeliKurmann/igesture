/*
 * @(#)$Id: WiiAccelerations.java
 *
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :	Temporarily stores acceleration data from the Wii Remote. 
 * 					Used to convert to position data in WiiMoteTools.
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

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class WiiAccelerations {
	private List<AccelerationSample> samples;

	public WiiAccelerations() {
		samples = new ArrayList<AccelerationSample>();
	}

	/**
	 * Returns the number of acceleration samples
	 * 
	 * @return The number of samples in this WiiAccelerations object
	 */
	public int numberOfSamples() {
		return samples.size();
	}

	/**
	 * Adds a sample to the acceleration samples list
	 * 
	 * @param sample
	 *            The Sample to be added
	 */
	public void addSample(AccelerationSample sample) {
		samples.add(sample);
	}

	/**
	 * Returns the sample at position number
	 * 
	 * @param number
	 *            The number of the sample to be returned
	 * @return The Sample with the given number
	 */
	public AccelerationSample getSample(int number) {
		return samples.get(number);
	}

	/**
	 * Removes the sample at position number
	 * 
	 * @param number
	 *            The number of the Sample to be removed
	 */
	public void removeSample(int number) {
		samples.remove(number);
	}

	/**
	 * Clears the accelerations list
	 */
	public void clear() {
		this.samples.clear();
	}

	/**
	 * Returns the timestamp of the first sample in the list. If no samples are
	 * available 0 is returned.
	 * 
	 * @return The timestamp of the first Sample in this WiiAccelerations object
	 */
	public long getFirstSampleTime() {
		if (samples.size() > 0)
			return samples.get(0).getTimeStamp();
		else
			return 0;
	}

	/**
	 * Returns the timestamp of the last sample in the list. If no samples are
	 * available 0 is returned.
	 * 
	 * @return The timestamp of the last Sample in this WiiAccelerations object
	 */
	public long getLastSampleTime() {
		if (samples.size() > 0)
			return samples.get(samples.size() - 1).getTimeStamp();
		else
			return 0;
	}

	/**
	 * Returns the maximum x acceleration value
	 * 
	 * @return The maximum x acceleration value
	 */
	public double getMaxXValue() {
		double value = 0;
		boolean found = false;
		Iterator<AccelerationSample> i = samples.iterator();
		while (i.hasNext()) {
			AccelerationSample s = i.next();
			if (!found) {
				value = s.getXAcceleration();
				found = true;
			} else {
				if (s.getXAcceleration() > value)
					value = s.getXAcceleration();
			}
		}
		return value;
	}

	/**
	 * Returns the maximum y acceleration value
	 * 
	 * @return The maximum y acceleration value
	 */
	public double getMaxYValue() {
		double value = 0;
		boolean found = false;
		Iterator<AccelerationSample> i = samples.iterator();
		while (i.hasNext()) {
			AccelerationSample s = i.next();
			if (!found) {
				value = s.getYAcceleration();
				found = true;
			} else {
				if (s.getYAcceleration() > value)
					value = s.getYAcceleration();
			}
		}
		return value;
	}

	/**
	 * Returns the maximum z acceleration value
	 * 
	 * @return The maximum z acceleration value
	 */
	public double getMaxZValue() {
		double value = 0;
		boolean found = false;
		Iterator<AccelerationSample> i = samples.iterator();
		while (i.hasNext()) {
			AccelerationSample s = i.next();
			if (!found) {
				value = s.getZAcceleration();
				found = true;
			} else {
				if (s.getZAcceleration() > value)
					value = s.getZAcceleration();
			}
		}
		return value;
	}

	/**
	 * Returns the minimum x acceleration value
	 * 
	 * @return The minimum x acceleration value
	 */
	public double getMinXValue() {
		double value = 0;
		boolean found = false;
		Iterator<AccelerationSample> i = samples.iterator();
		while (i.hasNext()) {
			AccelerationSample s = i.next();
			if (!found) {
				value = s.getXAcceleration();
				found = true;
			} else {
				if (s.getXAcceleration() < value)
					value = s.getXAcceleration();
			}
		}
		return value;
	}

	/**
	 * Returns the minimum y acceleration value
	 * 
	 * @return The minimum y acceleration value
	 */
	public double getMinYValue() {
		double value = 0;
		boolean found = false;
		Iterator<AccelerationSample> i = samples.iterator();
		while (i.hasNext()) {
			AccelerationSample s = i.next();
			if (!found) {
				value = s.getYAcceleration();
				found = true;
			} else {
				if (s.getYAcceleration() < value)
					value = s.getYAcceleration();
			}
		}
		return value;
	}

	/**
	 * Returns the minimum z acceleration value
	 * 
	 * @return The minimum z acceleration value
	 */
	public double getMinZValue() {
		double value = 0;
		boolean found = false;
		Iterator<AccelerationSample> i = samples.iterator();
		while (i.hasNext()) {
			AccelerationSample s = i.next();
			if (!found) {
				value = s.getZAcceleration();
				found = true;
			} else {
				if (s.getZAcceleration() < value)
					value = s.getZAcceleration();
			}
		}
		return value;
	}

	/**
	 * Returns the maximum overall acceleration value
	 * 
	 * @return The maximum overall acceleration value
	 */
	public double getMaxOverallValue() {
		double value = getMaxXValue();
		if (getMaxYValue() > value)
			value = getMaxYValue();
		if (getMaxZValue() > value)
			value = getMaxZValue();
		return value;
	}

	/**
	 * Returns the minimum overall acceleration value
	 * 
	 * @return The minimum overall acceleration value
	 */
	public Double getMinOverallValue() {
		double value = getMinXValue();
		if (getMinYValue() < value)
			value = getMinYValue();
		if (getMinZValue() < value)
			value = getMinZValue();
		return value;
	}

	/**
	 * Returns the maximum absolute value for overall accelerations
	 * 
	 * @return The maximum absolute value for overall accelerations
	 */
	public double getMaxAbsoluteAccelerationValue() {
		double value = getMaxOverallValue();
		// System.err.println("MAX: " + getMaxOverallValue());
		// System.err.println("MIN: " + getMinOverallValue());
		if (Math.abs(getMinOverallValue()) > value) {
			value = Math.abs(getMinOverallValue());
			// System.err.println("MIN taken");
		}
		return value;
	}

	/**
	 * Returns the samples list
	 * 
	 * @return The list of samples in this WiiAccelerations object
	 */
	public List<AccelerationSample> getSamples() {
		return samples;
	}
}
