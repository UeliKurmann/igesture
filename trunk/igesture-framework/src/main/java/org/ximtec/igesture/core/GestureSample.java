/*
 * @(#)GestureSample.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Represents an example gesture as used by the rubine
 * 					algorithm. Gestures are represented as notes. 
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

import org.sigtec.ink.Note;


public class GestureSample extends DefaultDataObject implements Cloneable {

   private Note note;

   private String name;


   /**
    * Constructor
    * 
    * @param name the name of the sample
    * @param note the note
    */
   public GestureSample(String name, Note note) {
      super();
      this.note = note;
      this.name = name;
   }


   /**
    * Returns the name of the sample.
    * 
    * @return the name
    */
   public String getName() {
      return name;
   }


   /**
    * Sets the sample name
    * 
    * @param name
    */
   public void setName(String name) {
      this.name = name;
   }


   /**
    * Returns the note describing the sampe
    * 
    * @return the note
    */
   public Note getNote() {
      return note;
   }


   @Override
   public Object clone() {
      GestureSample clone = null;
      try {
         clone = (GestureSample) super.clone();
         clone.name = name;
         clone.note = (Note) note.clone();
      }
      catch (final CloneNotSupportedException e) {
         //
      }
      return clone;
   }


   @Override
   public String toString() {
      return name;
   }
}
