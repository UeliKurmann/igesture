/*
 * @(#)$Id: WiiAccelerations.java 2008-12-02 arthurvogels $
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

package org.ximtec.igesture.util;

import java.util.List;
import java.util.ArrayList;

public class WiiAccelerations {
	private List<Sample> samples;
	private int sampleFrequency;
	

	public WiiAccelerations(int sampleFrequency){
		this.sampleFrequency = sampleFrequency;
		samples = new ArrayList<Sample>();
	}

	/** Returns the number of acceleration samples
	 * 
	 * @return
	 */
	public int numberOfSamples(){
		return samples.size();
	}
	
	/** Adds a sample to the acceleration samples list
	 * 
	 * @param sample
	 */
	public void addSample(Sample sample){
		samples.add(sample);
	}

	/** Returns the sample at position number
	 * 
	 * @param number
	 * @return
	 */
	public Sample getSample(int number){
		return samples.get(number);
	}
		
	/** Removes the sample at position number
	 * 
	 * @param number
	 */
	public void removeSample(int number){
		samples.remove(number);		
		return;
	}	
	
	/** Returns the sample frequency at which the list of samples was taken
	 * 
	 * @return
	 */
	public int getSampleFrequency() {
		return sampleFrequency;
	}

	/** Sets the sample frequency for the list of samples
	 * 
	 * @param sampleFrequency
	 */
	public void setSampleFrequency(int sampleFrequency) {
		this.sampleFrequency = sampleFrequency;
	}


	
}
