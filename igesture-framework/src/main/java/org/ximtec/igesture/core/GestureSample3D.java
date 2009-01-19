/*
 * @(#)$Id: GestureSample3D.java 2008-12-02 arthurvogels $
 *
 * Author       :   Arthur Vogels, arthur.vogels@gmail.com
 *
 * Purpose      :	Container for 3D gestures.
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

package org.ximtec.igesture.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;
import org.ximtec.igesture.util.additions3d.Point3D;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

public class GestureSample3D extends DefaultDataObject implements Cloneable,
		Gesture<RecordedGesture3D> {

	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_GESTURE = "gesture";
	private RecordedGesture3D gesture;
	private String name;

	/**
	 * Constructs a new GestureSample3D.
	 * 
	 * @param name
	 *            the name of the gesture sample.
	 * @param note
	 *            the note the sample note.
	 */
	public GestureSample3D(String name, RecordedGesture3D gesture) {
		super();
		setName(name);
		setGesture(gesture);
	}

	/**
	 * Sets the sample name.
	 * 
	 * @param name
	 *            the name to be set.
	 */
	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		propertyChangeSupport.firePropertyChange(PROPERTY_NAME, oldValue, name);
	}

	/**
	 * Returns the sample name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the sample gesture.
	 * 
	 * @param gesture
	 *            the gesture to be set.
	 */
	public void setGesture(RecordedGesture3D gesture) {
		RecordedGesture3D oldValue = this.gesture;
		this.gesture = gesture;
		propertyChangeSupport.firePropertyChange(PROPERTY_GESTURE, oldValue,
				gesture);
	}

	/**
	 * Returns the gesture sample.
	 */
	public RecordedGesture3D getGesture() {
		return this.gesture;
	}

	/**
	 * Splits RecordedGesture3D gesture into three 2D planes. The XY-Plane is
	 * defined as the plane in 3D space where z=0. The YZ-Plane is defined as
	 * the plane in 3D space where x=0. The ZX-Plane is defined as the plane in
	 * 3D space where y=0.
	 * 
	 * @return The list of 2D gestures that are the planes. First XY, then YZ
	 *         and then ZX.
	 */
	public List<Gesture<Note>> splitToPlanes() {
		Iterator<Point3D> iterator = this.gesture.iterator(); // Iterator on the
		// list of
		// Point3D in
		// gesture
		Trace traceXY = new Trace(); // X plane Trace
		Trace traceYZ = new Trace(); // Y plane Trace
		Trace traceZX = new Trace(); // Z plane Trace
		Point3D point3d; // Working variable
		// Project all 3d points in gesture on planes
		while (iterator.hasNext()) {
			point3d = (Point3D) iterator.next();
			// Add points to 2d traces
			traceXY.add(new Point(point3d.getX(), point3d.getY(), point3d
					.getTimeStamp()));
			traceYZ.add(new Point(point3d.getX(), point3d.getZ(), point3d
					.getTimeStamp()));
			traceZX.add(new Point(point3d.getY(), point3d.getZ(), point3d
					.getTimeStamp()));
		}
		// Put traces into Notes
		Note noteXY = new Note(); // X plane Note
		Note noteYZ = new Note(); // Y plane Note
		Note noteZX = new Note(); // Z plane Note
		// Add planes to returnlist
		noteXY.add(traceXY);
		noteYZ.add(traceYZ);
		noteZX.add(traceZX);
		List<Gesture<Note>> returnList = new ArrayList<Gesture<Note>>(); // Return
		// variable
		returnList.add(new GestureSample("XY-plane", noteXY));
		returnList.add(new GestureSample("YZ-plane", noteYZ));
		returnList.add(new GestureSample("ZX-plane", noteZX));
		// Return list with three planes
		return returnList;
	}

}
