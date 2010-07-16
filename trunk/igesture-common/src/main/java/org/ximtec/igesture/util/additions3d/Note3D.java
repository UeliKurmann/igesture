/*
 * @(#)$Id: Note3D.java
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.util.additions3d;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.ink.input.TimestampedInputEvent;
import org.sigtec.input.InputHandler;

public class Note3D implements Cloneable, InputHandler {
	
	private static final Logger LOGGER = Logger.getLogger(Note3D.class.getName());
	
	private List<Point3D> points; // List of position data
	private Accelerations accelerations; // Accelerations list that comes

	// from the input device

	/**
	 * Constructor
	 */
	public Note3D() {
		points = new Vector<Point3D>();
	}

	/**
	 * Constructor
	 * 
	 * @param points
	 *            A list of Point3D to be set as the position data of this
	 *            Note3D
	 */
	public Note3D(List<Point3D> points) {
		this();
		if (points != null) {
			this.points = points;
		}
	}

	/**
	 * Adds a point to the gesture
	 * 
	 * @param point
	 *            The Point3D to be added to the gesture
	 */
	public void add(Point3D point) {
		points.add(point);
	}

	/**
	 * Adds a list of points to the gesture
	 * 
	 * @param points
	 *            The list of points to be added to the gesture
	 */
	public void addAll(List<Point3D> points) {
		this.points.addAll(points);
	}

	/**
	 * Removes a point from the gesture
	 * 
	 * @param point
	 *            The point to be removed from the gesture
	 */
	public void remove(Point3D point) {
		points.remove(point);
	}

	/**
	 * Returns an Iterator on points
	 * 
	 * @return The Iterator on the points list
	 */
	public Iterator<Point3D> iterator() {
		return points.iterator();
	}

	/**
	 * Returns point by index
	 * 
	 * @param index
	 *            The index of the point to be returned
	 * @return The Point3D with the given index
	 */
	public Point3D get(int index) {
		return points.get(index);
	}

	/**
	 * Returns the list of points
	 * 
	 * @return The list of Point3D from this gesture
	 */
	public List<Point3D> getPoints() {
		return points;
	}

	/**
	 * Returns the size of the points list
	 * 
	 * @return The size of the points list
	 */
	public int size() {
		return points.size();
	}

	/**
	 * Returns the start point of the gesture
	 * 
	 * @return The start Point3D of the gesture
	 */
	public Point3D getStartPoint() {
		return (!points.isEmpty()) ? (Point3D) points.get(0) : null;
	}

	/**
	 * Returns the end point of the gesture
	 * 
	 * @return The end Point3D of the gesture
	 */
	public Point3D getEndPoint() {
		return (!points.isEmpty()) ? (Point3D) points.get(points.size() - 1)
				: null;
	}

	/**
	 * Returns the duration of the gesture
	 * 
	 * @return The duration of the gesture in milliseconds
	 */
	public long getDuration() {
		Point3D startPoint = getStartPoint();
		Point3D endPoint = getEndPoint();

		if ((startPoint != null) && (endPoint != null)) {
			return endPoint.getTimeStamp() - startPoint.getTimeStamp();
		} else {
			return 0;
		}
	}

	/**
	 * Returns true if the optional timestamp is available for all the points
	 * the trace contains. It is assumed that either all or none of the points
	 * have a timestamp.
	 * 
	 * @return true if a timestamp is available.
	 */
	public boolean hasTimestamp() {
		return getStartPoint().hasTimeStamp();
	}

	@Override
	public synchronized void handle(Object invoker,
			TimestampedInputEvent timestampedEvent) {
		// TODO Auto-generated method stub

	}

	/**
	 * Returns the list of acceleration data from this gesture
	 * 
	 * @return The WiiAccelerations from this gesture
	 */
	public Accelerations getAccelerations() {
		return accelerations;
	}

	/**
	 * Sets an accelerations list for this gesture
	 * 
	 * @param accelerations
	 *            The accelerations list WiiAccelerations object
	 */
	public void setAccelerations(Accelerations accelerations) {
		this.accelerations = accelerations;
	}

	/**
	 * Sets a points list for this gesture
	 * 
	 * @param pointsList
	 *            The points list
	 */
	public void setPoints(List<Point3D> pointsList) {
		this.points = pointsList;		
	}
	
   /**
    * Clones a note3D.
    * @return the cloned note3D.
    */
   @Override
   @SuppressWarnings("unchecked")
   public Object clone() {
      Note3D clone = null;

      try {
         clone = (Note3D)super.clone();
         List<Point3D> clonedPoints = new Vector<Point3D>();

         for (Point3D point : points) {
        	 clonedPoints.add((Point3D)point.clone());
         }

         clone.points = clonedPoints;
         clone.accelerations = (Accelerations) accelerations.clone();
      }
      catch (CloneNotSupportedException e) {
         LOGGER.log(Level.SEVERE, e.toString());
      }

      return clone;
   } // clone
   
}
