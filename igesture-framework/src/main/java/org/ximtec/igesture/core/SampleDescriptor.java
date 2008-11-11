/*
 * @(#)$Id$
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

import org.sigtec.ink.Note;


/**
 * Describes a gesture by a set of gesture samples.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SampleDescriptor extends DefaultDescriptor {

   public static final String PROPERTY_SAMPLES = "samples";

   private List<Gesture<Note>> samples;


   /**
    * Constructs a new sample descriptor.
    * 
    */
   public SampleDescriptor() {
      super();
      samples = new ArrayList<Gesture<Note>>();
   }


   /**
    * Returns the samples.
    * 
    * @return the samples.
    */
   public List<Gesture<Note>> getSamples() {
      return samples;
   } // getSamples


   /**
    * Returns the gesture sample for a given index position.
    * @param index the position of the sample to be returned.
    * @return the sample at the specified index position.
    */
   public Gesture<Note> getSample(int index) {
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
   public void addSample(Gesture<Note> sample) {
      samples.add(sample);
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_SAMPLES, samples
            .indexOf(sample), null, sample);
   } // addSample


   /**
    * Removes a sample from the gesture set.
    * 
    * @param sample the sample to be removed.
    */
   public void removeSample(Gesture<Note> sample) {
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
      
      for (Gesture<Note> sample : samples) {
         
         if (sample instanceof GestureSample) {
            ((GestureSample)sample).accept(visitor);
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
