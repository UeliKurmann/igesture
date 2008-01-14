/*
 * @(#)GestureSample.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Represents an example of a gesture as used by the
 *                  rubine algorithm. Gestures are represented as notes. 
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
 * Represents an example of a gesture as used by the rubine algorithm. Gestures
 * are represented as notes.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureSample extends DefaultDataObject implements Cloneable {

   private static final Logger LOGGER = Logger.getLogger(GestureSample.class
         .getName());

   private Note note;

   private String name;


   /**
    * Constructs a new gesture sample.
    * 
    * @param name the name of the sample.
    * @param note the note the sample note.
    */
   public GestureSample(String name, Note note) {
      super();
      this.note = note;
      this.name = name;
   }


   /**
    * Sets the sample name.
    * 
    * @param name the name to be set.
    */
   public void setName(String name) {
      this.name = name;
   } // setName


   /**
    * Returns the name of the sample.
    * 
    * @return the sample's name.
    */
   public String getName() {
      return name;
   } // getName


   /**
    * Returns the note describing the sample.
    * 
    * @return the note describing the sample.
    */
   public Note getNote() {
      return note;
   } // getNote


   @Override
   public Object clone() {
      GestureSample clone = null;

      try {
         clone = (GestureSample)super.clone();
         clone.name = name;
         clone.note = (Note)note.clone();
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
