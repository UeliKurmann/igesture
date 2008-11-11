/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
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


package org.ximtec.igesture.app.showcaseapp.eventhandler;

import java.awt.Color;
import java.awt.Graphics2D;

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.GestureAction;


/**
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class DeleteEventHandler implements GestureAction {

   private Graphics2D graphic;


   public DeleteEventHandler(Graphics2D graphic) {
      this.graphic = graphic;
   }


   public void actionPerformed(ResultSet resultSet) {
      Note note = resultSet.getGesture();
      graphic.setColor(Color.WHITE);
      graphic.fillRect((int)note.getBounds2D().getMinX(), (int)note
            .getBounds2D().getMinY(), (int)note.getBounds2D().getWidth(),
            (int)note.getBounds2D().getHeight());
   } // run

}
