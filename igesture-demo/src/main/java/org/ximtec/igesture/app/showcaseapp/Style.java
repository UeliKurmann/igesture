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
 * Nov 15, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.app.showcaseapp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;


/**
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class Style {

   private Color color;
   private Stroke stroke;


   public Style() {
      color = Color.BLACK;
      stroke = new BasicStroke(1.0f);
   }


   public void setColor(Color color) {
      this.color = color;
   } // setColor


   public Color getColor() {
      return color;
   } // getColor


   public void setStroke(Stroke stroke) {
      this.stroke = stroke;
   } // setStroke


   public Stroke getStroke() {
      return stroke;
   } // getStroke

}
