/*
 * @(#)$Id: RecordedGesture3D.java 2008-12-02 arthurvogels $
 *
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :	Temporarily stores a gesture that has been recorded,
 * 					serving as input for the recognizer.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         	Reason
 *
 * Dec 02, 2008     arthurvogels    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.util;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RecordedGesture3D implements Cloneable {
	   private static final Logger LOGGER = Logger.getLogger(RecordedGesture3D.class.getName());

	   private List<Point3D> points;

	   /** Constructor
	    * 
	    */
	   public RecordedGesture3D() {
	      points = new Vector<Point3D>();
	   } // Trace


	   /** Constructor
	    * 
	    * @param points
	    */
	   public RecordedGesture3D(List<Point3D> points) {
	      this();

	      if (points != null) {
	         this.points = points;
	      }

	   }

	   /** Adds a point to the gesture
	    * 
	    * @param point
	    * @return
	    */
	   public boolean add(Point3D point) {
	      return points.add(point);
	   }


	   /** Adds a list of points to the gesture
	    * 
	    * @param points
	    * @return
	    */
	   public boolean addAll(List<Point3D> points) {
	      return this.points.addAll(points);
	   }

	   /** Removes a point from the gesture
	    * 
	    * @param point
	    * @return
	    */
	   public boolean remove(Point3D point) {
	      return points.remove(point);
	   }

	   /** returns an iterator on points
	    * 
	    * @return
	    */
	   public Iterator<Point3D> iterator() {
	      return points.iterator();
	   }

	   /** Returns point by index
	    * 
	    * @param index
	    * @return
	    */
	   public Point3D get(int index) {
	      return points.get(index);
	   }

	   /** Returns a list of points
	    * 
	    * @return
	    */
	   public List<Point3D> getPoints() {
	      return points;
	   }

	   /** Returns the size of the points list
	    * 
	    * @return
	    */
	   public int size() {
	      return points.size();
	   }

	   /** Returns the start point of the gesture
	    * 
	    * @return
	    */
	   public Point3D getStartPoint() {
	      return (!points.isEmpty()) ? (Point3D)points.get(0) : null;
	   }

	   /** Returns the end point of the gesture
	    * 
	    * @return
	    */
	   public Point3D getEndPoint() {
	      return (!points.isEmpty()) ? (Point3D)points.get(points.size() - 1) : null;
	   }

	   /** Returns the duration of the gesture
	    * 
	    * @return
	    */
	   public long getDuration() {
	      Point3D startPoint = getStartPoint();
	      Point3D endPoint = getEndPoint();

	      if ((startPoint != null) && (endPoint != null)) {
	         return endPoint.getTimeStamp() - startPoint.getTimeStamp();
	      }
	      else {
	         return 0;
	      }
	   }


	   /**
	    * Returns true if the optional timestamp is available for all the points the
	    * trace contains. It is assumed that either all or none of the points have a
	    * timestamp.
	    * @return true if a timestamp is available.
	    */
	   public boolean hasTimestamp() {
	      return getStartPoint().hasTimeStamp();
	   }




	   /**
	    * Renders the gesture.
	    * @param g the graphics context
	    */
	 /*  public void paint(Graphics g) {
	      if (points.size() == 1) {
	         Point point = points.get(0);
	         g.drawLine((int)point.x, (int)point.y, (int)point.x, (int)point.y);
	      }
	      else if (points.size() >= 1) {
	         Iterator<Point> spline = points.iterator();
	         Point last = spline.next();

	         while (spline.hasNext()) {
	            Point current = spline.next();
	            g.drawLine((int)last.x, (int)last.y, (int)current.x, (int)current.y);
	            last = current;
	         }

	      }

	   }*/


	   /**
	    * Clones a recordedgesture3d.
	    * @return the cloned trace.
	    */
	   public Object clone() {
	      RecordedGesture3D clone = null;

	      try {
	         clone = (RecordedGesture3D)super.clone();
	         List<Point3D> clonedPoints = new Vector<Point3D>();

	         for (Point3D point : points) {
	            clonedPoints.add((Point3D)point.clone());
	         }

	         clone.points = clonedPoints;
	      }
	      catch (CloneNotSupportedException e) {
	         LOGGER.log(Level.SEVERE, e.toString());
	      }

	      return clone;
	   } 

}
