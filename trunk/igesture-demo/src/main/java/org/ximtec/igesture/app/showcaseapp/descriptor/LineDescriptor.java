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
 * 					ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
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
 * Comment
 * 
 * @version 1.0 Nov 24, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
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
   }

}
