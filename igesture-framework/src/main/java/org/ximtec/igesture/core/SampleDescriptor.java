/*
 * @(#)SampleDescriptor.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Describes a gesture by a set of gesture samples
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import java.util.ArrayList;
import java.util.List;


public class SampleDescriptor extends DefaultDescriptor {

   private List<GestureSample> samples;


   /**
    * Constructor
    * 
    */
   public SampleDescriptor() {
      super();
      samples = new ArrayList<GestureSample>();
   }


   /**
    * Returns the samples
    * 
    * @return the samples
    */
   public List<GestureSample> getSamples() {
      return samples;
   }


   /**
    * Adds a sample to the descriptor
    * 
    * @param sample the sample to add
    */
   public void addSample(GestureSample sample) {
      samples.add(sample);
   }


   /**
    * Removes a sample from the gesture set
    * 
    * @param sample
    */
   public void removeSample(GestureSample sample) {
      samples.remove(sample);
   }


   @Override
   public String toString() {
      return SampleDescriptor.class.getSimpleName();
   }
}
