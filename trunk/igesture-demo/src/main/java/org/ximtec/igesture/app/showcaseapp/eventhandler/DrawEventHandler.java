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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.app.showcaseapp.eventhandler;

import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.ximtec.igesture.app.showcaseapp.Style;
import org.ximtec.igesture.core.DigitalDescriptor;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.GestureAction;


/**
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class DrawEventHandler implements GestureAction {

   private static final Logger LOGGER = Logger.getLogger(DrawEventHandler.class
         .getName());

   private static final String GESTURE_CLASS_NAME = "Gesture class name = ";

   private Graphics2D graphic;
   private Style style;


   public DrawEventHandler(Graphics2D graphic, Style style) {
      this.graphic = graphic;
      this.style = style;
   }


   public void actionPerformed(ResultSet resultSet) {
      Result result = resultSet.getResult();
      Note note = (Note) resultSet.getGesture().getGesture();
      LOGGER.log(Level.INFO, GESTURE_CLASS_NAME + result.getGestureClassName());
      graphic.setStroke(style.getStroke());
      graphic.setColor(style.getColor());
      DigitalDescriptor descriptor = result.getGestureClass().getDescriptor(
            DigitalDescriptor.class);
      descriptor.getDigitalObject(graphic, note);
   } // run

}
