/*
 * @(#)GestureTool.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Collection of commonly used static methods.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
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


/**
 * Collection of commonly used static methods.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureTool {

   public static double scaleTraceTo(org.sigtec.ink.Trace trace,
         double maxWidth, double maxHeight) {
      final Rectangle2D bounds = trace.getBounds2D();
      final double width = bounds.getWidth();
      final double height = bounds.getHeight();
      final double scaleX = maxWidth / width;
      final double scaleY = maxHeight / height;
      return (scaleX < scaleY) ? scaleX : scaleY;
   } // scaleTraceTo


   /**
    * Combines a list of gesture set to one gesture set.
    * 
    * @param sets the list of gesture sets to be combined.
    * @return the combined gesture set.
    */
   public static GestureSet combine(List<GestureSet> sets) {
      final Set<GestureClass> gestureClasses = new HashSet<GestureClass>();

      for (final GestureSet set : sets) {
         gestureClasses.addAll(set.getGestureClasses());
      }

      return new GestureSet(new ArrayList<GestureClass>(gestureClasses));
   } // combine


   /**
    * Detects characteristic points of a trace. A point is characteristic if it
    * is involved in a significant change of direction.
    * 
    * @param trace the trace to be filtered.
    * @param minAngle the minimal angle between two sequences.
    * @param minDistance the minimal distance between two points.
    * @return the filtered trace.
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
   } // getCharacteristicTrace


   /**
    * Creates a characteristic note.
    * 
    * @see getCharacteristicTrace
    */
   public static Note getCharacteristicNote(Note note, double minAngle,
         double minDistance) {
      final Note result = new Note();

      for (final Trace trace : note.getTraces()) {
         result.add(getCharacteristicTrace(trace, minAngle, minDistance));
      }

      return result;
   } // getCharacteristicNote


   /**
    * Computes the angle between two points.
    * 
    * @param p1 the first point.
    * @param p2 the second point.
    * @return the angle between p1 and p2.
    */
   public static double getAngle(Point p1, Point p2) {
      final double r = p1.distance(p2);
      final double x = p2.getX() - p1.getX();
      double alpha = Math.toDegrees(Math.acos(x / r));

      if (p2.getY() - p1.getY() < 0) {
         alpha = 360 - alpha;
      }

      return alpha;
   } // getAngle


   /**
    * Creates an image from the note.
    * @param n the note for which an image has to be created.
    * @param width the width of the image.
    * @param height the height of the image.
    * @return the buffered image.
    */
   public static BufferedImage createNoteImage(Note n, int width, int height) {
      final Note note = (Note)n.clone();
      final BufferedImage bufferedImage = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_ARGB);
      final Graphics graphic = bufferedImage.getGraphics();
      graphic.setColor(Color.BLACK);
      note.scaleTo(width - 10, height - 10);
      note.moveTo(5, 5);
      note.paint(graphic);
      return bufferedImage;
   } // createNoteImage


   /**
    * Combines samples from different gesture sets.
    * @param sets a list of gesture sets.
    * @return samples from different gesture sets.
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
   } // combineSampleData


   /**
    * Transforms a gesture set into a test set.
    * @param set the gesture set to be transformed.
    * @return the test set.
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
   } // createTestSet


   /**
    * Creates a noise test set from the gesture set.
    * @param set the gesture set.
    * @return a noise test set.
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
   } // createNoise


   /**
    * Combines different test sets.
    * @param testSets the test sets to be combined.
    * @return the combined test sets.
    */
   public static TestSet combineTestSet(TestSet[] testSets) {
      final TestSet result = new TestSet("testset");
      
      for (final TestSet set : testSets) {
         result.addAll(set.getSamples());
      }

      return result;
   } // combineTestSet

}