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
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class ArrowDescriptor extends DigitalDescriptor {

   private static final int SPACE = 20;


   public ArrowDescriptor() {
      super();
   }


   @Override
   public void getDigitalObject(Graphics2D graphic, Note note) {
      int x1 = (int)note.getBounds2D().getMinX();
      int x2 = (int)note.getBounds2D().getMaxX();
      int y = (int)((note.getBounds2D().getMinY() + note.getBounds2D().getMaxY()) / 2);
      int diff = (int)((note.getBounds2D().getMinY() - note.getBounds2D()
            .getMaxY()) / 2);
      graphic.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
      graphic.drawLine(x1, y, x2, y);
      graphic.drawLine(x2, y, x2 - SPACE, y - diff);
      graphic.drawLine(x2, y, x2 - SPACE, y + diff);
   } // getDigitalObject


  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

}
