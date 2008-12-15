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

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.sigtec.ink.Note;

public class WiiAccelerations {
	private List<Sample> samples;
	private int sampleFrequency;
	

	public WiiAccelerations(int sampleFrequency){
		this();
		this.sampleFrequency = sampleFrequency;
	}

	public WiiAccelerations() {
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

	/**
	 * Clears the accelerations list
	 */
	public void clear() {
		this.samples.clear();		
	}

	/**
	 * Returns the timestamp of the first sample in the list
	 * 
	 * @return
	 */
	public long getFirstSampleTime() {
		if(samples.size() > 0)
			return samples.get(0).getTimeStamp();
		else
			return 0;
	}

	/**
	 * Returns the timestamp of the last sample in the list
	 * 
	 * @return
	 */
	public long getLastSampleTime() {
		if(samples.size() > 0)
			return samples.get(samples.size()-1).getTimeStamp();
		else
			return 0;
	}
	
	/**
	 * Returns the maximum x acceleration value
	 * 
	 * @return
	 */
	public double getMaxXValue(){
		double value = 0;
		boolean found = false;
		Iterator<Sample> i = samples.iterator();
		while(i.hasNext()){
			Sample s = i.next();
			if(!found){
				value = s.getXAcceleration();
				found = true;
			}
			else {
				if(s.getXAcceleration() > value)
				value = s.getXAcceleration();
			}
		}
		return value;
	}
	
	/**
	 * Returns the maximum y acceleration value
	 * 
	 * @return
	 */
	public double getMaxYValue(){
		double value = 0;
		boolean found = false;
		Iterator<Sample> i = samples.iterator();
		while(i.hasNext()){
			Sample s = i.next();
			if(!found){
				value = s.getYAcceleration();
				found = true;
			}
			else {
				if(s.getYAcceleration() > value)
				value = s.getYAcceleration();
			}
		}
		return value;
	}

	/**
	 * Returns the maximum z acceleration value
	 * 
	 * @return
	 */
	public double getMaxZValue(){
		double value = 0;
		boolean found = false;
		Iterator<Sample> i = samples.iterator();
		while(i.hasNext()){
			Sample s = i.next();
			if(!found){
				value = s.getZAcceleration();
				found = true;
			}
			else {
				if(s.getZAcceleration() > value)
				value = s.getZAcceleration();
			}
		}
		return value;
	}
	
	/**
	 * Returns the minimum x acceleration value
	 * 
	 * @return
	 */
	public double getMinXValue(){
		double value = 0;
		boolean found = false;
		Iterator<Sample> i = samples.iterator();
		while(i.hasNext()){
			Sample s = i.next();
			if(!found){
				value = s.getXAcceleration();
				found = true;
			}
			else {
				if(s.getXAcceleration() < value)
				value = s.getXAcceleration();
			}
		}
		return value;
	}
	
	/**
	 * Returns the minimum y acceleration value
	 * 
	 * @return
	 */
	public double getMinYValue(){
		double value = 0;
		boolean found = false;
		Iterator<Sample> i = samples.iterator();
		while(i.hasNext()){
			Sample s = i.next();
			if(!found){
				value = s.getYAcceleration();
				found = true;
			}
			else {
				if(s.getYAcceleration() < value)
				value = s.getYAcceleration();
			}
		}
		return value;
	}
	
	/**
	 * Returns the minimum z acceleration value
	 * 
	 * @return
	 */
	public double getMinZValue(){
		double value = 0;
		boolean found = false;
		Iterator<Sample> i = samples.iterator();
		while(i.hasNext()){
			Sample s = i.next();
			if(!found){
				value = s.getZAcceleration();
				found = true;
			}
			else {
				if(s.getZAcceleration() < value)
				value = s.getZAcceleration();
			}
		}
		return value;
	}
	
	/**
	 * Returns the maximum overall acceleration value
	 * 
	 * @return
	 */
	public double getMaxOverallValue(){
		double value = getMaxXValue();
		if(getMaxYValue() > value)
			value = getMaxYValue();
		if(getMaxZValue() > value)
			value = getMaxZValue();		
		return value;
	}
	
	/**
	 * Returns the minimum overall acceleration value
	 * 
	 * @return
	 */
	public Double getMinOverallValue(){
		double value = getMinXValue();
		if(getMinYValue() < value)
			value = getMinYValue();
		if(getMinZValue() < value)
			value = getMinZValue();		
		return value;
	}

	/**
	 * Returns the maximum absolute value for overall accelerations
	 * 
	 * @return
	 */
	public double getMaxAbsoluteAccelerationValue() {
		double value = getMaxOverallValue();
		//System.err.println("MAX: " + getMaxOverallValue());
		//System.err.println("MIN: " + getMinOverallValue());
		if(Math.abs(getMinOverallValue()) > value){
			value = Math.abs(getMinOverallValue());
			//System.err.println("MIN taken");
		}
		return value;
	}

	/**
	 * Returns the samples list
	 * 
	 * @return
	 */
	public List<Sample> getSamples() {
		return samples;
	}	
}
