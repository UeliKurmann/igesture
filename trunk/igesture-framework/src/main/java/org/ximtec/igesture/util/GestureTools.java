/*
 * @(#)GestureTools.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Collection of common used static methods
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;
import org.sigtec.ink.TraceTool;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TestSet;


public class GestureTools {

   public static double scaleTraceTo(org.sigtec.ink.Trace trace,
         double maxWidth, double maxHeight) {
      final Rectangle2D bounds = trace.getBounds2D();
      final double width = bounds.getWidth();
      final double height = bounds.getHeight();
      final double scaleX = maxWidth / width;
      final double scaleY = maxHeight / height;
      return (scaleX < scaleY) ? scaleX : scaleY;
   } // scaleTo


   /**
    * Combines a list of gesture set to one gesture set
    * 
    * @param sets list of gesture sets to combine
    * @return the combined gesture set
    */
   public static GestureSet combine(List<GestureSet> sets) {
      final Set<GestureClass> gestureClasses = new HashSet<GestureClass>();
      for (final GestureSet set : sets) {
         gestureClasses.addAll(set.getGestureClasses());
      }
      return new GestureSet(new ArrayList<GestureClass>(gestureClasses));
   }


   /**
    * Detects characteristic points of a trace. a point is characteristic if it
    * is involved in a significant change of direction
    * 
    * @param trace the trace to be filtered
    * @param minAngle the minimal angle between two sequences
    * @param minDistance the minimal distance between two points
    * @return the filtered trace
    */
   public static Trace getCharacteristicTrace(Trace trace, double minAngle,
         double minDistance) {
      final Trace inputTrace = TraceTool.filterTrace(trace, minDistance);
      Trace resultTrace = new Trace();

      if (inputTrace.size() > 2) {

         Point p1 = trace.get(0);
         Point p2 = trace.get(2);
         Point p3 = null;

         double angle1 = getAngle(p1, p2);
         double angle2 = 0;

         // add the first point to the result
         resultTrace.add(p1);

         for (int i = 2; i < inputTrace.size(); i++) {
            p3 = inputTrace.get(i);
            angle2 = getAngle(p2, p3);

            if (Math.abs(angle1 - angle2) > minAngle) {
               // p2 is significant and part of the result
               resultTrace.add(p2);
               // go to the next section
               p1 = p2;
               p2 = p3;
            }
            else {
               // section p1-p3 is combined , p2 is not significant and can
               // be
               // removed
               p2 = p3;
            }
            angle1 = getAngle(p1, p2);
         }
         // add the end point of the input trace
         resultTrace.add(inputTrace.get(inputTrace.size() - 1));
      }
      else {
         resultTrace = inputTrace;
      }
      return resultTrace;
   }


   /**
    * Creates a characteristic Note
    * 
    * @see getCharacteristicTrace
    * @param note
    * @param minAngle
    * @param minDistance
    * @return the characteristic note
    */
   public static Note getCharacteristicNote(Note note, double minAngle,
         double minDistance) {
      final Note result = new Note();
      for (final Trace trace : note.getTraces()) {
         result.add(getCharacteristicTrace(trace, minAngle, minDistance));
      }
      return result;
   }


   /**
    * Computes the angle between two points
    * 
    * @param p1 the first point
    * @param p2 the second point
    * @return the angle between p1 and p2
    */
   public static double getAngle(Point p1, Point p2) {
      final double r = p1.distance(p2);
      final double x = p2.getX() - p1.getX();

      double alpha = Math.toDegrees(Math.acos(x / r));
      if (p2.getY() - p1.getY() < 0) {
         alpha = 360 - alpha;
      }
      return alpha;
   }


   /**
    * Returns all points of the note as list
    * 
    * @param note
    * @return the points of the list
    */
   public static List<Point> getPoints(Note note) {
      final List<Point> points = new ArrayList<Point>();
      for (final Trace t : note.getTraces()) {
         points.addAll(t.getPoints());
      }
      return points;
   }


   /**
    * Creates an image from the note
    * @param n the note
    * @param width the width
    * @param height the height
    * @return the buffered image
    */
   public static BufferedImage createNoteImage(Note n, int width, int height) {
      final Note note = (Note) n.clone();
      final BufferedImage bufferedImage = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_ARGB);
      final Graphics graphic = bufferedImage.getGraphics();
      graphic.setColor(Color.BLACK);

      note.scaleTo(width - 10, height - 10);
      note.moveTo(5, 5);

      note.paint(graphic);

      return bufferedImage;
   }


   /**
    * Combines samples from different gesture sets
    * @param sets a list of gesture sets
    * @return
    */
   public static GestureSet combineSampleData(List<GestureSet> sets) {
      final HashMap<String, GestureClass> samples = new HashMap<String, GestureClass>();
      final GestureSet result = new GestureSet("CombinedGestureSet");
      for (final GestureSet set : sets) {
         for (final GestureClass gestureClass : set.getGestureClasses()) {
            if (samples.containsKey(gestureClass.getName())) {
               final GestureClass target = samples.get(gestureClass.getName());
               for (final GestureSample sample : gestureClass.getDescriptor(
                     SampleDescriptor.class).getSamples()) {
                  target.getDescriptor(SampleDescriptor.class).addSample(sample);
               }
            }
            else {
               samples.put(gestureClass.getName(), gestureClass);
               result.addGestureClass(gestureClass);
            }
         }
      }
      return result;
   }


   /**
    * Transforms a GestureSet into a TestSet
    * @param set the gesture set to transform
    * @return
    */
   public static TestSet createTestSet(GestureSet set) {
      final TestSet testSet = new TestSet("Name");

      for (final GestureClass gestureClass : set.getGestureClasses()) {
         for (final GestureSample sample : gestureClass.getDescriptor(
               SampleDescriptor.class).getSamples()) {
            testSet.add(new GestureSample(gestureClass.getName(), sample
                  .getNote()));
         }
      }
      return testSet;
   }


   /**
    * Creates a noise test set from the gesture set
    * @param set the gesture set
    * @return
    */
   public static TestSet createNoise(GestureSet set) {
      final TestSet testSet = new TestSet("Name");

      for (final GestureClass gestureClass : set.getGestureClasses()) {
         for (final GestureSample sample : gestureClass.getDescriptor(
               SampleDescriptor.class).getSamples()) {
            testSet.add(new GestureSample(TestSet.NOISE, sample.getNote()));
         }
      }
      return testSet;
   }


   /**
    * Combines Testsets
    * @param args
    * @return
    */
   public static TestSet combineTestSet(TestSet[] args) {
      final TestSet result = new TestSet("testset");
      for (final TestSet set : args) {
         result.addAll(set.getSamples());
      }

      return result;
   }

}
