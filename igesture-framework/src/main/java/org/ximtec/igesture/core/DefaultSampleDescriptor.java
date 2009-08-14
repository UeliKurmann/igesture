/*
 * @(#)$Id: SampleDescriptor.java 730 2009-08-05 21:17:30Z kurmannu $
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
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
 * Jan 19, 2009		vogelsar	Made more generic to be able to store GestureSample3D
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public abstract class DefaultSampleDescriptor<T> extends DefaultDescriptor {

   public static final String PROPERTY_SAMPLES = "samples";

   private List<Gesture<T>> samples;


   /**
    * Constructs a new sample descriptor.
    * 
    */
   public DefaultSampleDescriptor() {
      super();
      samples = new ArrayList<Gesture<T>>();
   }


   /**
    * Returns the samples.
    * 
    * @return the samples.
    */
   public List<Gesture<T>> getSamples() {
      return samples;
   } // getSamples


   /**
    * Returns the gesture sample for a given index position.
    * @param index the position of the sample to be returned.
    * @return the sample at the specified index position.
    */
   public Gesture<T> getSample(int index) {
      if (samples.size() >= index - 1) {
         return samples.get(index);
      }
      
      return null;
   } // getSample


   /**
    * Adds a sample to the descriptor.
    * 
    * @param sample the sample to be added.
    */
   public void addSample(Gesture<T> sample) {
      samples.add(sample);
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_SAMPLES, samples
            .indexOf(sample), null, sample);
   } // addSample


   /**
    * Removes a sample from the gesture set.
    * 
    * @param sample the sample to be removed.
    */
   public void removeSample(Gesture<T> sample) {
      int index = samples.indexOf(sample);
      samples.remove(sample);
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_SAMPLES, index,
            sample, null);
   } // removeSample


   /**
    * {@inheritDoc}
    */
   @Override
   public void accept(Visitor visitor) {
      visitor.visit(this);
      for (Gesture<T> sample : samples) {
        sample.accept(visitor);
      }
   } // accept


   public abstract String getName();


   @Override
   public String toString() {
      return getName();
   } // toString

}
