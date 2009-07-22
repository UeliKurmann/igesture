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

import java.awt.BasicStroke;
import java.awt.Color;

import org.ximtec.igesture.app.showcaseapp.Style;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.GestureAction;


/**
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class StyleEventHandler implements GestureAction {

   private Style style;


   public StyleEventHandler(Style style) {
      this.style = style;
   }


   public void actionPerformed(ResultSet resultSet) {
      String command = resultSet.getResult().getGestureClassName();

      if (command.equals("Red")) {
         style.setColor(Color.RED);
      }
      else if (command.equals("Black")) {
         style.setColor(Color.BLACK);
      }
      else if (command.equals("Yellow")) {
         style.setColor(Color.YELLOW);
      }
      else if (command.equals("Thin")) {
         style.setStroke(new BasicStroke(1.0f));
      }
      else if (command.equals("Fat")) {
         style.setStroke(new BasicStroke(3.0f));
      }

   } // run

}
