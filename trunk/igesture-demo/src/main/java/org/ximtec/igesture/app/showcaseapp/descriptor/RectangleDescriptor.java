/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 24, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.app.showcaseapp.descriptor;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.DigitalDescriptor;


/**
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class RectangleDescriptor extends DigitalDescriptor {

   public RectangleDescriptor() {
      super();
   }


   @Override
   public void getDigitalObject(Graphics2D graphic, Note note) {
      graphic.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
      graphic.drawRect((int)note.getBounds2D().getMinX(), (int)note
            .getBounds2D().getMinY(), (int)note.getBounds2D().getWidth(),
            (int)note.getBounds2D().getHeight());
   } // getDigitalObject
   
   @Override
   public String getName() {
     return this.getClass().getSimpleName();
   }

}
