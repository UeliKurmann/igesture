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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.core;

import java.util.List;

import org.sigtec.ink.Note;
import org.ximtec.igesture.util.RecordedGesture3DTool;
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
		return RecordedGesture3DTool.splitToPlanes(getGesture());
	}

}
