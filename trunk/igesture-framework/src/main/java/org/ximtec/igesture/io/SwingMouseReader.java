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
 * 03.05.2008			ukurmann	Initial Release
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.sigtec.ink.input.Location;
import org.sigtec.ink.input.PenUpEvent;
import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.AbstractInputDevice;
import org.sigtec.util.Constant;


/**
 * Comment
 * @version 1.0 03.05.2008
 * @author Ueli Kurmann
 */
public class SwingMouseReader extends AbstractInputDevice {

   private Boolean mouseClicked;
   private Point translation;
   private Point lastPoint;
   private SwingMouseReaderPanel currentPanel;
   private List<Point> buffer;
   private String identifier;


   public SwingMouseReader() {
      buffer = new ArrayList<Point>();

      mouseClicked = false;
      identifier = SwingMouseReader.class.getName() + Constant.UNDERSCORE
            + System.currentTimeMillis();

      new Thread(worker, identifier).start();
   }


   public List<Point> getBuffer() {
      return buffer;
   }


   public JPanel getPanel(Dimension dimension) {
      final SwingMouseReaderPanel panel = new SwingMouseReaderPanel(this);
      panel.setSize(dimension);
      panel.setPreferredSize(dimension);
      panel.setOpaque(true);
      panel.setBackground(Color.WHITE);
      panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

      panel.addMouseListener(new MouseAdapter() {

         SwingMouseReaderPanel owner = panel;


         @Override
         public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            currentPanel = owner;
            Point p1 = e.getPoint();
            Point p2 = MouseInfo.getPointerInfo().getLocation();
            translation = new Point((int)(p1.getX() - p2.getX()), (int)(p1
                  .getY() - p2.getY()));
            mouseClicked = true;

         }


         @Override
         public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            mouseClicked = false;
            fireInputDeviceEvent(new MouseReaderEvent(new PenUpEvent(System
                  .currentTimeMillis(), identifier)));
            lastPoint = null;
         }
      });
      return panel;
   }


   public void clear() {
      buffer.clear();
      if (currentPanel != null) {
         currentPanel.clear();
      }
   }

   private Runnable worker = new Runnable() {

      @Override
      public void run() {
         while (true) {
            try {
               Thread.sleep(20);
               if (mouseClicked) {
                  PointerInfo info = MouseInfo.getPointerInfo();
                  Point currentPoint = info.getLocation();
                  currentPoint.move((int)(currentPoint.getX() + translation
                        .getX()),
                        (int)(currentPoint.getY() + translation.getY()));

                  Location location = new Location(Constant.EMPTY_STRING, 0,
                        currentPoint, identifier);
                  fireInputDeviceEvent(new MouseReaderEvent(
                        new TimestampedLocation(location, System
                              .currentTimeMillis())));

                  buffer.add(currentPoint);

                  if (currentPanel != null) {
                     currentPanel.drawLine(lastPoint, currentPoint);
                  }

                  lastPoint = currentPoint;
               }
            }
            catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }
   };
}
