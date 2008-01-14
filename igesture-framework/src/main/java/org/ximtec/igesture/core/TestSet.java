/*
 * @(#)TestSet.java	1.0   Nov 20, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Set of gesture samples used to evaluate algorithms.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 20, 2006		ukurmann	Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import java.util.ArrayList;
import java.util.List;


/**
 * Set of gesture samples used to evaluate algorithms.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TestSet extends DefaultDataObject {

   private List<GestureSample> samples;

   public static final String NOISE = "None";

   private String name;


   /**
    * Constructs a new test set.
    * 
    * @param name the name of the test set.
    */
   public TestSet(String name) {
      super();
      samples = new ArrayList<GestureSample>();
      this.name = name;
   }


   /**
    * Adds a sample to the list of samples.
    * 
    * @param sample the sample to be added.
    */
   public void add(GestureSample sample) {
      samples.add(sample);
   } // add


   /**
    * Adds a list of samples.
    * 
    * @param samples the samples to be added.
    */
   public void addAll(List<GestureSample> samples) {
      this.samples.addAll(samples);
   } // addAll


   /**
    * Removes a the sample from the list of samples.
    * 
    * @param sample the sample to be removed.
    */
   public void remove(GestureSample sample) {
      samples.remove(sample);
   } // remove


   /**
    * Returns all samples.
    * 
    * @return a list with all samples.
    */
   public List<GestureSample> getSamples() {
      return samples;
   } // getSamples


   /**
    * Returns the number of samples which should be rejected by the recogniser.
    * 
    * @return the number of "noise" entries in the test set.
    */
   public int getNoiseSize() {
      int i = 0;

      for (final GestureSample sample : getSamples()) {

         if (sample.getName().equals(NOISE)) {
            i++;
         }

      }

      return i;
   } // getNoiseSize


   /**
    * Sets the name of the test set.
    * 
    * @param name the name of the test set.
    */
   public void setName(String name) {
      this.name = name;
   } // setName


   /**
    * Returns the name of the test set.
    * 
    * @return the name of the test set.
    */
   public String getName() {
      if (name == null) {
         name = String.valueOf(System.currentTimeMillis());
      }

      return name;
   } // getName


   /**
    * Returns the size of the test set.
    * 
    * @return the size of the test set.
    */
   public int size() {
      return samples.size();
   } // size


   /**
    * Returns true if the test set is empty.
    * 
    * @return true if the test set is empty.
    */
   public boolean isEmpty() {
      return samples.isEmpty();
   } // isEmpty


   @Override
   public String toString() {
      return name;
   } // toString

}