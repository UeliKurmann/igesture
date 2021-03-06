/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Digital descriptor of a gesture class.
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import java.awt.Graphics2D;

import org.sigtec.ink.Note;


/**
 * Digital descriptor of a gesture class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public abstract class DigitalDescriptor extends DefaultDescriptor {

   public DigitalDescriptor() {
      super();
   }


   /**
    * Renders a digital representation of the specified note on a given graphics
    * context.
    * 
    * @param graphics the graphics context on which the digital note
    *            representation has to be rendered.
    * @param note the note to be rendered.
    */
   public abstract void getDigitalObject(Graphics2D graphics, Note note);


   @Override
   public String toString() {
      return DigitalDescriptor.class.getName();
   } // toString

}
