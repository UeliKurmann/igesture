/*
 * @(#)JNote.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Visualises a sigtec note.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.tool.old.util;

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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.sigtec.graphix.Points;
import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.input.TimestampedInputEvent;
import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.InputHandler;
import org.sigtec.util.Constant;


/**
 * Visualises a sigtec note.
 * 
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class JNote extends JLabel implements InputHandler {

   private static final Logger LOGGER = Logger.getLogger(JNote.class.getName());

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
    * Constructs a new JNote.
    * 
    * @param width width of the component
    * @param height height of the component
    */
   public JNote(int width, int height) {
      this(width, height, INITIAL_WIDHT, INITIAL_HEIGHT);
   }


   /**
    * Constructs a new JNote.
    * 
    * @param width width of the component.
    * @param height height of the component.
    * @param spaceWidth the initial width of the space to be represented.
    * @param spaceHeight the initial height of the space to be represented.
    */
   public JNote(int width, int height, int spaceWidth, int spaceHeight) {
      super();
      traceNumber = 0;
      locationBuffer = new ArrayList<Point>();
      freeze = false;
      this.initialSpaceHeight = spaceHeight;
      this.initialSpaceWidth = spaceWidth;
      space = new int[] { spaceWidth, spaceHeight };
      setSize(width, height);
      bufferedImage = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_ARGB);
      graphic = bufferedImage.getGraphics();
      ((Graphics2D)graphic).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
      setIcon(new ImageIcon(bufferedImage));
      graphic.setColor(Color.BLACK);
   }


   public void handle(Object invoker, TimestampedInputEvent timestampedEvent) {
      if (timestampedEvent instanceof TimestampedLocation) {
         TimestampedLocation location = (TimestampedLocation)timestampedEvent;
         
         if (freeze) {
            clear();
            freeze = false;
         }

         drawNextPoint(location.toPoint());
      }
   } // handle


   /**
    * Sets a complete note to be drawn.
    * 
    * @param note the note to be drawn.
    */
   public void setNote(Note note) {
      clear();

      for (final Point point : note.getPoints()) {
         drawNextPoint(point);
      }

   } // setNote


   /**
    * Freeze the current drawing. The next stroke will trigger the deletion of
    * all current points and will start over.
    * 
    */
   public void freeze() {
      freeze = true;
   } // freeze


   /**
    * Exports the drawing as an image file.
    * 
    * @param file the file in which the image of the note has to be stored.
    * @param format the format of the stored image.
    */
   public void exportImage(File file, String format) {
      try {
         ImageIO.write(bufferedImage, format, file);
      }
      catch (final IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

   } // exportImage


   /**
    * Exports the drawing as a PNG file.
    * 
    * @param file the file in which the image of the note has to be stored.
    */
   public void exportPNGImage(File file) {
      exportImage(file, PNG);
   } // exportPNGImage


   /**
    * Draws the next point.
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

      repaint();
      lastPoint = currentPoint;
   } // drawNextPoint


   /**
    * Update the middle point.
    */
   private synchronized void updateMiddlePoint(Point point) {
      scale = Math.min((double)bufferedImage.getWidth() / space[0],
            (double)bufferedImage.getHeight() / space[1]);
      translation = new int[2];
      translation[0] = (int)(bufferedImage.getWidth() / 2 - point.getX() * scale);
      translation[1] = (int)(bufferedImage.getHeight() / 2 - point.getY()
            * scale);
      refresh();
   } // updateMiddlePoint


   /**
    * Redraw the entire drawing.
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
   } // refresh


   /**
    * Draws a line between two given points.
    * 
    * @param startPoint the start point.
    * @param endPoint the end point.
    */
   private void drawLine(Point startPoint, Point endPoint) {
      graphic.drawLine((int)startPoint.getX(), (int)startPoint.getY(),
            (int)endPoint.getX(), (int)endPoint.getY());
   } // drawLine


   /**
    * Draws a point marking the start of a stroke.
    * 
    * @param point the point hold location information.
    * @param radius the radius of the point.
    */
   private void drawPoint(Point point, int radius) {
      graphic.setColor(Color.RED);
      graphic.fillOval((int)point.getX() - radius / 2, (int)point.getY()
            - radius / 2, radius, radius);
      graphic.setColor(Color.BLACK);
   } // drawPoint


   /**
    * Numbers the traces.
    */
   private void numberTrace(Point point, int number) {
      graphic.setColor(Color.RED);
      graphic.drawString(String.valueOf(number), (int)point.getX() - 5,
            (int)point.getY() - 5);
      graphic.setColor(Color.BLACK);
   } // numberTrace


   /**
    * Translates the point.
    * @param point the original point.
    * @return the transformed point.
    */
   private Point translate(Point point) {
      final double x = (point.getX() * scale + translation[0]);
      final double y = (point.getY() * scale + translation[1]);
      return new Point((int)x, (int)y);
   } // translate


   /**
    * Tests if the point lies within the drawing area.
    * 
    * @param point the point to be tested.
    * @return true if the point lies within the drawing area.
    */
   private boolean isInArea(Point point) {
      if (point.getX() > bufferedImage.getWidth()
            || point.getY() > bufferedImage.getHeight() || point.getX() < 0
            || point.getY() < 0) {
         return false;
      }

      return true;
   } // isInArea


   /**
    * Computes the cetner point of a drawing.
    * 
    * @return the drawing's center point.
    */
   private Point computeCenterPoint() {
      final List<Point2D> points = new ArrayList<Point2D>();

      for (final Point ts : locationBuffer) {
         points.add(new Point2D.Double(ts.getX(), ts.getY()));
      }

      final Point2D point = Points.getCentre(points);
      return new Point((int)point.getX(), (int)point.getY());
   } // computeCenterPoint


   /**
    * Tests if a new strace was been started.
    */
   private boolean isNewTrace(Point loc1, Point loc2) {
      return loc2.getTimestamp() - loc1.getTimestamp() < MAX_TIME;
   } // isNewTrace


   /**
    * Clears the drawing area.
    * 
    */
   public void clear() {
      locationBuffer.clear();
      lastPoint = null;
      translation = null;
      space = new int[] { initialSpaceWidth, initialSpaceHeight };
      clearBufferedImage();
      traceNumber = 0;
   } // clear


   private void clearBufferedImage() {
      graphic.setColor(Color.WHITE);
      graphic
            .fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
      graphic.setColor(Color.BLACK);
   } // clearBufferedImage


   public BufferedImage getImage() {
      return bufferedImage;
   } // getImage

}
