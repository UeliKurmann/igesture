/*
 * @(#)TestSet.java	1.0   Nov 20, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Set of GestureSamples used to evaluate algorithms
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import java.util.ArrayList;
import java.util.List;


/**
 * @version 1.0 Nov 20, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class TestSet extends DefaultDataObject {

   private List<GestureSample> samples;

   public static final String NOISE = "None";

   private String name;


   /**
    * Constructor
    * 
    * @param name the name of the test set
    */
   public TestSet(String name) {
      super();
      samples = new ArrayList<GestureSample>();
      this.name = name;
   }


   /**
    * Adds a sample to the list of samples
    * 
    * @param sample
    */
   public void add(GestureSample sample) {
      samples.add(sample);
   }


   /**
    * Adds a list of samples
    * 
    * @param samples the samples to add
    */
   public void addAll(List<GestureSample> samples) {
      this.samples.addAll(samples);
   }


   /**
    * Removes a the sample from the list of samples
    * 
    * @param sample the sample to remove
    */
   public void remove(GestureSample sample) {
      samples.remove(sample);
   }


   /**
    * Returns all samples
    * 
    * @return the list of samples
    */
   public List<GestureSample> getSamples() {
      return samples;
   }


   /**
    * Returns the number of samples which should be rejected by the recogniser
    * 
    * @return the number of noise in the testset
    */
   public int getNoiseSize() {
      int i = 0;
      for (final GestureSample sample : getSamples()) {
         if (sample.getName().equals(NOISE)) {
            i++;
         }
      }
      return i;
   }


   /**
    * Returns the name of the TestSet
    * 
    * @return the name of the Test Set
    */
   public String getName() {
      if (name == null) {
         name = String.valueOf(System.currentTimeMillis());
      }
      return name;
   }


   /**
    * Returns the size of the test set
    * 
    * @return the size of the test set
    */
   public int size() {
      return samples.size();
   }


   /**
    * Informs if the testset is empty
    * 
    * @return
    */
   public boolean isEmpty() {
      return samples.isEmpty();
   }


   /**
    * Sets the name of the TestSet
    * 
    * @param name the name of the TestSet
    */
   public void setName(String name) {
      this.name = name;
   }
   
   @Override
	public String toString(){
	   return name;
   }
}
