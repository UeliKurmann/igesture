/*
 * @(#)GestureSample.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Represents a gesture sample as for example used by
 *                  the Rubine algorithm. Single gestures are represented
 *                  as notes. 
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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.sigtec.util.Constant;


/**
 * Represents a gesture sample as for example used by the Rubine algorithm.
 * Single gestures are represented as notes.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureSample extends DefaultDataObject implements Cloneable,
      Gesture<Note> {

   private static final Logger LOGGER = Logger.getLogger(GestureSample.class
         .getName());

   public static final String PROPERTY_NAME = "name";

   public static final String PROPERTY_GESTURE = "gesture";

   private Note gesture;

   private String name;


   /**
    * Constructs a new gesture sample.
    * 
    * @param name the name of the gesture sample.
    * @param note the note the sample note.
    */
   public GestureSample(String name, Note gesture) {
      super();
      setName(name);
      setGesture(gesture);
   }


   /**
    * Sets the sample name.
    * 
    * @param name the name to be set.
    */
   public void setName(String name) {
      String oldValue = this.name;
      this.name = name;
      propertyChangeSupport.firePropertyChange(PROPERTY_NAME, oldValue, name);
   } // setName


   /**
    * Returns the name of the gesture sample.
    * 
    * @return the gesture sample's name.
    */
   public String getName() {
      return name;
   } // getName


   /**
    * Sets the sample gesture.
    * 
    * @param gesture the gesture to be set.
    */
   public void setGesture(Note gesture) {
      Note oldValue = this.gesture;
      this.gesture = gesture;
      propertyChangeSupport.firePropertyChange(PROPERTY_GESTURE, oldValue,
            gesture);
   } // setGesture


   /**
    * Returns the gesture sample.
    * 
    * @return the gesture sample.
    */
   public Note getGesture() {
      return gesture;
   } // getGesture


   /**
    * {@inheritDoc}
    */
   @Override
   public void accept(Visitor visitor) {
      visitor.visit(this);
   }


   @Override
   public Object clone() {
      GestureSample clone = null;

      try {
         clone = (GestureSample)super.clone();
         clone.name = name;
         clone.gesture = (Note)gesture.clone();
      }
      catch (final CloneNotSupportedException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

      return clone;
   } // clone


   @Override
   public String toString() {
      return name;
   } // toString

}
