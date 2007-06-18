/*
 * @(#)SampleDescriptor.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Describes a gesture by a set of gesture samples.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
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
 * Describes a gesture by a set of gesture samples.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SampleDescriptor extends DefaultDescriptor {

   private List<GestureSample> samples;


   /**
    * Constructs a new sample descriptor.
    * 
    */
   public SampleDescriptor() {
      super();
      samples = new ArrayList<GestureSample>();
   }


   /**
    * Returns the samples.
    * 
    * @return the samples.
    */
   public List<GestureSample> getSamples() {
      return samples;
   } // getSamples


   /**
    * Adds a sample to the descriptor.
    * 
    * @param sample the sample to be added.
    */
   public void addSample(GestureSample sample) {
      samples.add(sample);
   } // addSample


   /**
    * Removes a sample from the gesture set.
    * 
    * @param sample the sample to be removed.
    */
   public void removeSample(GestureSample sample) {
      samples.remove(sample);
   } // removeSample


   @Override
   public String toString() {
      return SampleDescriptor.class.getSimpleName();
   } // toString

}