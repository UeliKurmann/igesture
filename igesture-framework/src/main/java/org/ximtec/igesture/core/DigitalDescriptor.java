/*
 * @(#)DigitalDescriptor.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Describes the digital representation of a gesture class
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import java.awt.Graphics2D;

import org.sigtec.ink.Note;


public abstract class DigitalDescriptor extends DefaultDescriptor {

   public DigitalDescriptor() {
      super();
   }


   /**
    * Draws the digital object
    * 
    * @param graphics the graphic object on which the drawing is done
    * @param note the note
    */
   public abstract void getDigitalObject(Graphics2D graphics, Note note);


   @Override
   public String toString() {
      return DigitalDescriptor.class.getName();
   }
}
