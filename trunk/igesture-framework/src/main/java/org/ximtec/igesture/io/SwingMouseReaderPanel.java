/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 04.05.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;


/**
 * Comment
 * @version 1.0 04.05.2008
 * @author Ueli Kurmann
 */
public class SwingMouseReaderPanel extends JPanel {

   private SwingMouseReader reader;


   public SwingMouseReaderPanel(SwingMouseReader reader) {
      this.reader = reader;
      setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
   }


   public void drawLine(Point lastPoint, Point currentPoint) {
      if (getGraphics() != null && currentPoint != null && lastPoint != null
            && lastPoint.distance(currentPoint) < 1000000) {

         getGraphics().drawLine((int)lastPoint.getX(), (int)lastPoint.getY(),
               (int)currentPoint.getX(), (int)currentPoint.getY());

      }
   }


   public void clear() {
      getGraphics().clearRect(0, 0, getWidth(), getHeight());
      repaint();
   }


   @Override
   public void paint(Graphics g) {
      super.paint(g);
      if (reader != null) {
         List<Point> buffer = reader.getBuffer();
         for (int i = 0; i < buffer.size() - 1; i++) {
            drawLine(buffer.get(i), buffer.get(i + 1));
         }
      }
   }


   @Override
   public void repaint() {
      super.repaint();
      if (reader != null) {
         List<Point> buffer = reader.getBuffer();
         for (int i = 0; i < buffer.size() - 1; i++) {
            drawLine(buffer.get(i), buffer.get(i + 1));
         }
      }
   }

}
