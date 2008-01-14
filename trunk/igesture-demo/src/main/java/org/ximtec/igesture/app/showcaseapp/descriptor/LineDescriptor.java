/*
 * @(#)RectangleDescriptor.java	1.0   Nov 24, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class LineDescriptor extends DigitalDescriptor {

   public LineDescriptor() {
      super();
   }


   @Override
   public void getDigitalObject(Graphics2D graphic, Note note) {
      graphic.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
      int x1 = (int)note.getBounds2D().getMinX();
      int x2 = (int)note.getBounds2D().getMaxX();
      int y = (int)((note.getBounds2D().getMinY() + note.getBounds2D().getMaxY()) / 2);
      graphic.drawLine(x1, y, x2, y);
   } // getDigitalObject

}
