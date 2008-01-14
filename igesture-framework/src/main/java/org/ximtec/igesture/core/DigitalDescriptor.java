/*
 * @(#)DigitalDescriptor.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Describes the digital representation of a
 *                  gesture class.
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

import java.awt.Graphics2D;

import org.sigtec.ink.Note;


/**
 * Describes the digital representation of a gesture class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public abstract class DigitalDescriptor extends DefaultDescriptor {

   public DigitalDescriptor() {
      super();
   }


   /**
    * Draws the digital object.
    * 
    * @param graphics the graphics context on which the drawing is done.
    * @param note the note.
    */
   public abstract void getDigitalObject(Graphics2D graphics, Note note);


   @Override
   public String toString() {
      return DigitalDescriptor.class.getName();
   } // toString

}
