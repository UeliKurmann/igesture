/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   Some feature tools.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 15, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;
import org.sigtec.util.Constant;
import org.ximtec.igesture.util.DoubleVector;


/**
 * Some feature tools.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class FeatureTool {

   private static final Logger LOGGER = Logger.getLogger(FeatureTool.class
         .getName());


   /**
    * Takes a note and returns a single trace containing all points.
    * 
    * @param note the note whose points have to be extracted.
    * @return the trace containing all points of the note.
    */
   public static Trace createTrace(Note note) {
      final Trace result = new Trace();

      for (final Trace trace : note.getTraces()) {
         result.addAll(trace.getPoints());
      }

      return result;
   } // createTrace


   /**
    * Returns the distance in respect to the X axis between point i and i+1.
    * 
    * @param i the ith point.
    * @param points the array of points.
    * @return the distance in respect to the X axis between point i and i+1.
    */
   public static double getDeltaX(int i, Point[] points) {
      return points[i + 1].getX() - points[i].getX();
   } // getDeltaX


   /**
    * Returns the distance in respect to the Y axis between point i and i+1.
    * 
    * @param i the ith point.
    * @param points the array of points.
    * @return the distance in respect to the Y axis between point i and i+1.
    */
   public static double getDeltaY(int i, Point[] points) {
      return points[i + 1].getY() - points[i].getY();
   } // getDeltaY


   /**
    * Returns the difference of the timestamps in point i and i + 1.
    * 
    * @param i the ith point.
    * @param points the array of points.
    * @return the time between two points.
    */
   public static double getDeltaT(int i, Point[] points) {
      return points[i + 1].getTimestamp() - points[i].getTimestamp();
   } // getDeltaT


   /**
    * Returns the difference of the timestamps in point p1 and p2.
    * 
    * @param p1 the point p1.
    * @param p2 the point p2.
    * @return the time passes between the two given points.
    */
   public static double getDeltaT(Point p1, Point p2) {
      return p2.getTimestamp() - p1.getTimestamp();
   } // getDeltaT


   /**
    * Computes the angle in respect to the x axis in point i and i-1.
    * 
    * @param i the index of the point to be used in the computation.
    * @param points the set of points.
    * @return the angle relative to the x axis for point i and i-1.
    */
   public static double roh(int i, Point[] points) {
      final double divisor = getDeltaX(i, points) * getDeltaY(i - 1, points)
            - getDeltaX(i - 1, points) * getDeltaY(i, points);
      final double dividend = getDeltaX(i, points) * getDeltaX(i - 1, points)
            + getDeltaY(i, points) * getDeltaY(i - 1, points);
      final double result = Math.atan(divisor / dividend);

      if (Double.isNaN(result)) {
         return 1;
      }
      else {
         return result;
      }

   } // roh


   /**
    * Computes the angle of the line p1-p2 in the unit circle.
    * 
    * @param p1 start point of the line.
    * @param p2 end point of the line.
    * @return the angle.
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
    * Removes traces from the list with less than minNumOfPoints.
    * 
    * @param traces the list of traces.
    * @param minNumOfPoints the minimal number of points a trace must contain.
    * @return the filtered list of traces.
    */
   public static List<Trace> removeShortTraces(List<Trace> traces,
         int minNumOfPoints) {
      final List<Trace> result = new ArrayList<Trace>();

      for (final Trace trace : traces) {

         if (trace.size() >= minNumOfPoints) {
            result.add(trace);
         }

      }
      return result;
   } // removeShortTraces


   /**
    * Creates a list of instantiated features out of a comma delimited list of
    * full qualified feature classnames.
    * 
    * @param featureList a comma delimited list of full qualified class names.
    * @return list of feature instances.
    */
   public static List<Feature> createFeatureList(String featureList) {
      final ArrayList<Feature> result = new ArrayList<Feature>();
      final StringTokenizer tokenizer = new StringTokenizer(featureList,
            Constant.COMMA);

      while (tokenizer.hasMoreElements()) {
         result.add(createFeature(tokenizer.nextToken()));
      }

      return result;
   } // createFeatureList


   /**
    * Instantiates a feature of the given full qualified classname.
    * 
    * @param classname the full qualified class name.
    * @return the instance of the feature.
    */
   public static Feature createFeature(String classname) {
      try {
         return (Feature)Class.forName(classname.trim()).newInstance();
      }
      catch (final InstantiationException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final IllegalAccessException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final ClassNotFoundException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

      return null;
   } // createFeature


   /**
    * Computes the feature vector.
    * 
    * @param note the note the feature vector has to be computed of.
    * @param minDistance the minimal distance between two points.
    * @param featureList an array of features.
    * @return the feature vector.
    * @throws FeatureException if the feature vector cannot be computed.
    */
   public static DoubleVector computeFeatureVector(Note note, int minDistance,
         Feature[] featureList) throws FeatureException {

      // clone the note to avoid side effects
      final Note clone = (Note)note.clone();

      // filter the note using the min distance
      clone.filter(minDistance);

      // create the feature vector
      final DoubleVector featureVector = new DoubleVector(featureList.length);

      for (int i = 0; i < featureList.length; i++) {
         featureVector.set(i, featureList[i].compute(clone));
      }

      return featureVector;
   } // computeFeatureVector


   /**
    * Returns the minimal number of points a note must contain to be processed.
    * @param list the list of features.
    * @return the minimal number of points a note must contain.
    */
   public static int computeMinimalNumberOfRequiredPoints(Feature[] list) {
      int result = 0;
      for (Feature feature : list) {
         result = Math.max(result, feature.getMinimalNumberOfPoints());
      }
      return result;
   }


   public static double computeD1(Note note) {
      Trace trace = createTrace(note);
      double a = Math.pow(trace.get(trace.size() / 2).getX()
            - trace.getStartPoint().getX(), 2);
      double b = Math.pow(trace.get(trace.size() / 2).getY()
            - trace.getStartPoint().getY(), 2);
      return Math.sqrt(a + b);
   } // computeD1


   public static double computeD2(Note note) {
      Trace trace = createTrace(note);
      double a = Math.pow(trace.getEndPoint().getX()
            - trace.get(trace.size() / 2).getX(), 2);
      double b = Math.pow(trace.getEndPoint().getY()
            - trace.get(trace.size() / 2).getY(), 2);
      return Math.sqrt(a + b);
   } // computeD2


   public static double computeD3(Note note) {
      Trace trace = createTrace(note);
      double a = Math.pow(trace.getEndPoint().getX()
            - trace.getStartPoint().getX(), 2);
      double b = Math.pow(trace.getEndPoint().getY()
            - trace.getStartPoint().getY(), 2);
      return Math.sqrt(a + b);
   } // computeD3

}
