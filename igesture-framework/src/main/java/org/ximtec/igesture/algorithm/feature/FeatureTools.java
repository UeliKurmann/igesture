/*
 * @(#)FeatureTools.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 
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


package org.ximtec.igesture.algorithm.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;
import org.ximtec.igesture.core.DoubleVector;


public class FeatureTools {

   /**
    * Takes a note and returns one trace whicht contains ALL points.
    * 
    * @param note the note to extract the points
    * @return the trace with all points of the note
    */
   public static Trace createTrace(Note note) {
      final Trace result = new Trace();
      for (final Trace trace : note.getTraces()) {
         result.addAll(trace.getPoints());
      }
      return result;
   }


   /**
    * Returns the distance in respect to the X axis between point i and i+1
    * 
    * @param i the ith point
    * @param points the array of points
    * @return the distance in respect to the X axis between point i and i+1
    */
   public static double getDeltaX(int i, Point[] points) {
      return points[i + 1].getX() - points[i].getX();
   }


   /**
    * Returns the distance in respect to the Y axis between point i and i+1
    * 
    * @param i the ith point
    * @param points the array of points
    * @return the distance in respect to the Y axis between point i and i+1
    */
   public static double getDeltaY(int i, Point[] points) {
      return points[i + 1].getY() - points[i].getY();
   }


   /**
    * Returns the difference of the timestamps in point i and i + 1
    * 
    * @param i the ith point
    * @param points the array of points
    * @return the time between two points
    */
   public static double getDeltaT(int i, Point[] points) {
      return points[i + 1].getTimestamp() - points[i].getTimestamp();
   }


   /**
    * Returns the differenc of the timestamps in point p1 and p2
    * 
    * @param p1 the point p1
    * @param p2 the point p2
    * @return the time passes between the two given points
    */
   public static double getDeltaT(Point p1, Point p2) {
      return p2.getTimestamp() - p1.getTimestamp();
   }


   /**
    * Computes the angle in respect to the X axis in point i and i-1.
    * 
    * @param i
    * @param points
    * @return
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
   }


   /**
    * Computes the angle of the line p1-p2 in the unit circle
    * 
    * @param p1 start point of the line
    * @param p2 end point of the line
    * @return the angle
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
    * Removes traces from the list with less than minNumOfPoints
    * 
    * @param traces the list of traces
    * @param minNumOfPoints the minimmal number of points a trace must contain
    * @return the filtered list of traces
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
   }


   /**
    * Creates a list of instantiates features out of a comma delimited list of
    * full qualified classnames of features
    * 
    * @param featureList a comma delimited list of full qualified class names
    * @return list of feature instances
    */
   public static List<Feature> createFeatureList(String featureList) {
      final ArrayList<Feature> result = new ArrayList<Feature>();
      final StringTokenizer tokenizer = new StringTokenizer(featureList, ",");
      while (tokenizer.hasMoreElements()) {
         result.add(createFeature(tokenizer.nextToken()));
      }
      return result;
   }


   /**
    * Instantiates a festure of the given full qualified class name
    * 
    * @param classname the full qualified class name
    * @return the instance of the feature
    */
   public static Feature createFeature(String classname) {
      try {
         return (Feature) Class.forName(classname.trim()).newInstance();
      }
      catch (final InstantiationException e) {
         e.printStackTrace();
      }
      catch (final IllegalAccessException e) {
         e.printStackTrace();
      }
      catch (final ClassNotFoundException e) {
         e.printStackTrace();
      }
      return null;
   }


   /**
    * Computes the Feature Vector
    * 
    * @param note the note
    * @param minDistance the minimal distance between two points
    * @param featureList an array of features
    * @return
    */
   public static DoubleVector computeFeatureVector(Note note, int minDistance,
         Feature[] featureList) {

      // clone the note to avoid side effects
      final Note clone = (Note) note.clone();

      // filter the note using the min distance
      clone.filter(minDistance);

      // create the feature vector
      final DoubleVector featureVector = new DoubleVector(featureList.length);
      for (int i = 0; i < featureList.length; i++) {
         featureVector.set(i, featureList[i].compute(clone));
      }
      return featureVector;
   }

}
