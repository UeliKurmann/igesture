/*
 * @(#)$Id: Sample3DDescriptor.java 607 2008-11-11 10:34:42Z bsigner $
 *
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      : 	Describes a 3D gesture by a set of 3D gesture samples.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         	Reason
 *
 * Jan 04, 2009     vogelsar		Initial Release
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

import org.ximtec.igesture.util.RecordedGesture3D;


/**
 * Describes a 3d gesture by a set of 3d gesture samples.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Sample3DDescriptor extends DefaultDescriptor {

   public static final String PROPERTY_SAMPLES = "samples";

   private List<Gesture<RecordedGesture3D>> samples;


   /**
    * Constructs a new sample descriptor.
    * 
    */
   public Sample3DDescriptor() {
      super();
      samples = new ArrayList<Gesture<RecordedGesture3D>>();
   }


   /**
    * Returns the samples.
    * 
    * @return the samples.
    */
   public List<Gesture<RecordedGesture3D>> getSamples() {
      return samples;
   } // getSamples


   /**
    * Returns the gesture sample for a given index position.
    * @param index the position of the sample to be returned.
    * @return the sample at the specified index position.
    */
   public Gesture<RecordedGesture3D> getSample(int index) {
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
   public void addSample(Gesture<RecordedGesture3D> sample) {
      samples.add(sample);
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_SAMPLES, samples
            .indexOf(sample), null, sample);
   } // addSample


   /**
    * Removes a sample from the gesture set.
    * 
    * @param sample the sample to be removed.
    */
   public void removeSample(Gesture<RecordedGesture3D> sample) {
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
      
      for (Gesture<RecordedGesture3D> sample : samples) {
         
         if (sample instanceof GestureSample3D) {
            ((GestureSample3D)sample).accept(visitor);
         }
      
      }
   
   } // accept


   public String getName() {
      return SampleDescriptor.class.getSimpleName();
   } // getName


   @Override
   public String toString() {
      return getName();
   } // toString

}
