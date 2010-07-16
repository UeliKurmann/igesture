/*
 * @(#)$Id$
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.io.mouseclient;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.io.GestureDevicePanel;
import org.ximtec.igesture.io.GestureEventListener;

/**
 * Comment
 * 
 * @version 1.0 04.05.2008
 * @author Ueli Kurmann
 */
public class SwingMouseReaderPanel extends GestureDevicePanel implements GestureEventListener {

  private SwingMouseReader reader;

  public SwingMouseReaderPanel(SwingMouseReader reader) {
    this.reader = reader;
    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
  }

  public void drawLine(Point lastPoint, Point currentPoint) {

    if (getGraphics() != null && currentPoint != null && lastPoint != null
        && lastPoint.distance(currentPoint) < 1000000) {

      getGraphics().drawLine((int) lastPoint.getX(), (int) lastPoint.getY(), (int) currentPoint.getX(),
          (int) currentPoint.getY());

    }
  }

  public void clear() {
    if (getGraphics() != null) {
      getGraphics().clearRect(0, 0, getWidth(), getHeight());
    }

    repaint();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    redrawGesture();
  }

  @Override
  public void repaint() {
    super.repaint();
    redrawGesture();
  }

  private void redrawGesture() {
    if (reader != null) {

      Note note = reader.getGesture().getGesture();
      if (note != null) {
        List<Point> buffer = new ArrayList<Point>();
        for (Trace trace : note.getTraces()) {
          for (org.sigtec.ink.Point point : trace.getPoints()) {
            buffer.add(new Point((int) point.getX(), (int) point.getY()));
          }
        }

        for (int i = 0; i < buffer.size() - 1; i++) {
          drawLine(buffer.get(i), buffer.get(i + 1));
        }
      }
    }
  }

/* (non-Javadoc)
 * @see org.ximtec.igesture.io.GestureEventListener#handleChunks(java.util.List)
 */
@Override
public void handleChunks(List<?> chunks) {
}

/* (non-Javadoc)
 * @see org.ximtec.igesture.io.GestureEventListener#handleGesture(org.ximtec.igesture.core.Gesture)
 */
@Override
public void handleGesture(Gesture<?> gesture) {
}

}
