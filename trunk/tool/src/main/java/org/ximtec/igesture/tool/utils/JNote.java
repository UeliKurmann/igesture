/*
 * @(#)JNote.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Visualises a sigtec note
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.sigtec.graphix.Points;
import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.ximtec.ipaper.input.InputHandler;
import org.ximtec.ipaper.util.TimestampedLocation;


@SuppressWarnings("serial")
public class JNote extends JLabel implements InputHandler {

   private BufferedImage bufferedImage;

   private Graphics graphic;

   private Point lastPoint;

   private double scale;

   private int[] space;

   private int[] translation;

   private int traceNumber;

   private int initialSpaceWidth;

   private int initialSpaceHeight;

   private List<Point> locationBuffer;

   private static final int MAX_TIME = 180;

   private static final int MARKER_RADIUS = 6;

   private static final int INITIAL_WIDHT = 50;

   private static final int INITIAL_HEIGHT = 50;

   private static final int STRETCH_FACTOR = 2;

   private boolean freeze;

   public static String PNG = "png";

   public static String JPEG = "jpeg";


   /**
    * Constructor
    * 
    * @param width width of the component
    * @param height height of the component
    */
   public JNote(int width, int height) {
      this(width, height, INITIAL_WIDHT, INITIAL_HEIGHT);
   }


   /**
    * Constructor
    * 
    * @param width width of the component
    * @param height height of the component
    * @param spaceWidth the initial width of the space to be represented
    * @param spaceHeight the initial height of the space to be represented
    */
   public JNote(int width, int height, int spaceWidth, int spaceHeight) {
      super();
      traceNumber = 0;
      locationBuffer = new ArrayList<Point>();
      freeze = false;
      this.initialSpaceHeight = spaceHeight;
      this.initialSpaceWidth = spaceWidth;

      space = new int[] { spaceWidth, spaceHeight };

      this.setSize(width, height);
      bufferedImage = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_ARGB);
      graphic = bufferedImage.getGraphics();
      ((Graphics2D) graphic).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
      this.setIcon(new ImageIcon(bufferedImage));
      graphic.setColor(Color.BLACK);
   }


   public void handle(Object invoker, TimestampedLocation location) {
	   if (freeze) {
         clear();
         freeze = false;
      }
      drawNextPoint(convert(location));
   }


   /**
    * Sets a complete note to be drawn
    * 
    * @param note
    */
   public void setNote(Note note) {
      clear();
      for (final Point point : note.getPoints()) {
         drawNextPoint(point);
      }
   }


   /**
    * Freeze the current drawing. The next stroke will trigger the deletion of
    * all current points and will start over.
    * 
    */
   public void freeze() {
      freeze = true;
   }


   /**
    * Exports the Drawing as Image File
    * 
    * @param file
    * @param format
    */
   public void exportImage(File file, String format) {
      try {
         ImageIO.write(bufferedImage, format, file);
      }
      catch (final IOException e) {
         e.printStackTrace();
      }
   }


   /**
    * Exports the Drawing PNG file
    * 
    * @param file
    */
   public void exportPNGImage(File file) {
      exportImage(file, PNG);
   }


   /**
    * Draws the next point
    * 
    * @param currentPoint
    */
   public synchronized void drawNextPoint(Point currentPoint) {
      if (!locationBuffer.contains(currentPoint)) {
         locationBuffer.add(currentPoint);
      }

      if (translation == null) {
         updateMiddlePoint(currentPoint);
      }
      else if (!isInArea(translate(currentPoint))) {
         space[0] *= STRETCH_FACTOR;
         space[1] *= STRETCH_FACTOR;
         updateMiddlePoint(computeCenterPoint());
      }

      if (lastPoint != null && isNewTrace(lastPoint, currentPoint)) {
         drawLine(translate(lastPoint), translate(currentPoint));
      }
      else {
         drawPoint(translate(currentPoint), MARKER_RADIUS);
         numberTrace(translate(currentPoint), traceNumber++);
      }
      this.repaint();
      lastPoint = currentPoint;
   }


   /**
    * Update the middle point
    * 
    * @param point
    */
   private synchronized void updateMiddlePoint(Point point) {
      scale = Math.min((double) bufferedImage.getWidth() / space[0],
            (double) bufferedImage.getHeight() / space[1]);

      translation = new int[2];
      translation[0] = (int) (bufferedImage.getWidth() / 2 - point.getX()
            * scale);
      translation[1] = (int) (bufferedImage.getHeight() / 2 - point.getY()
            * scale);

      this.refresh();
   }


   /**
    * Redraw the full drawing
    * 
    */
   private synchronized void refresh() {
      clearBufferedImage();

      traceNumber = 0;
      final Point tmp = lastPoint;
      lastPoint = null;
      for (final Point ts : locationBuffer) {
         drawNextPoint(ts);
      }
      lastPoint = tmp;
   }


   /**
    * Draws a line between two given points
    * 
    * @param startPoint the start point
    * @param endPoint the end point
    */
   private void drawLine(Point startPoint, Point endPoint) {
      graphic.drawLine((int) startPoint.getX(), (int) startPoint.getY(),
            (int) endPoint.getX(), (int) endPoint.getY());
   }


   /**
    * Draws a point marking the beginning of a stroke
    * 
    * @param point the point hold location information
    * @param radius the radius of the point
    */
   private void drawPoint(Point point, int radius) {
      graphic.setColor(Color.RED);
      graphic.fillOval((int) point.getX() - radius / 2, (int) point.getY()
            - radius / 2, radius, radius);

      graphic.setColor(Color.BLACK);
   }


   /**
    * Numbers the traces
    * 
    * @param point
    * @param number
    */
   private void numberTrace(Point point, int number) {
      graphic.setColor(Color.RED);

      graphic.drawString(String.valueOf(number), (int) point.getX() - 5,
            (int) point.getY() - 5);

      graphic.setColor(Color.BLACK);
   }


   /**
    * Translate the point
    * 
    * @param point the original point
    * @return the transformed point
    */
   private Point translate(Point point) {
      final double x = (point.getX() * scale + translation[0]);
      final double y = (point.getY() * scale + translation[1]);
      return new Point((int) x, (int) y);
   }


   /**
    * Test if the point is located in the drawing area
    * 
    * @param point
    * @return
    */
   private boolean isInArea(Point point) {
      if (point.getX() > bufferedImage.getWidth()
            || point.getY() > bufferedImage.getHeight() || point.getX() < 0
            || point.getY() < 0) {
         return false;
      }
      return true;
   }


   /**
    * Computes the cetner point of a drawing
    * 
    * @return the center point
    */
   private Point computeCenterPoint() {
      final List<Point2D> points = new ArrayList<Point2D>();
      for (final Point ts : locationBuffer) {
         points.add(new Point2D.Double(ts.getX(), ts.getY()));
      }
      final Point2D point = Points.getCentre(points);
      return new Point((int) point.getX(), (int) point.getY());
   }


   /**
    * Tests if a new strace was started
    * 
    * @param loc1 point 1
    * @param loc2 point 2
    * @return
    */
   private boolean isNewTrace(Point loc1, Point loc2) {
      return loc2.getTimestamp() - loc1.getTimestamp() < MAX_TIME;
   }


   /**
    * Converts a timestamped location into a point
    * 
    * @param location
    * @return
    */
   private Point convert(TimestampedLocation location) {
      return new Point(location.getPosition().getX(), location.getPosition()
            .getY(), location.getTimestamp());
   }


   /**
    * Clears the drawing area
    * 
    */
   public void clear() {
      locationBuffer.clear();
      lastPoint = null;
      translation = null;
      space = new int[] { initialSpaceWidth, initialSpaceHeight };
      clearBufferedImage();
      traceNumber = 0;
   }


   private void clearBufferedImage() {
      graphic.setColor(Color.WHITE);
      graphic
            .fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
      graphic.setColor(Color.BLACK);
   }


   public BufferedImage getImage() {
      return bufferedImage;
   }

}
