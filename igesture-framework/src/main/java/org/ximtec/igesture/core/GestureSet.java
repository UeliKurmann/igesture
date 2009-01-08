/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Container to manage a set of gesture classes.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.core;

import java.util.ArrayList;
import java.util.List;

import org.sigtec.util.Constant;

/**
 * Container to manage a set of gesture classes.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureSet extends DefaultDataObject {

	public static final String PROPERTY_NAME = "name";

	public static final String PROPERTY_CLASSES = "gestureClasses";
	
	private List<GestureClass> gestureClasses;

	private String name;

	/**
	 * Constructs a new gesture set.
	 * 
	 */
	public GestureSet() {
		this(Constant.EMPTY_STRING);
	}

	
	/**
	 * Constructs a new gesture set with a given name.
	 * 
	 * @param name
	 *            the name of the gesture set to be constructed.
	 */
	public GestureSet(String name) {
		super();
		gestureClasses = new ArrayList<GestureClass>();
		setName(name);
	}

	
	/**
	 * Constructs a new gesture set from a list of gesture classes.
	 * 
	 * @param gestureClasses
	 *            a list of gesture classes.
	 */
	public GestureSet(List<GestureClass> gestureClasses) {
		this.gestureClasses = gestureClasses;
	}

	
	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		propertyChangeSupport.firePropertyChange(PROPERTY_NAME, oldValue, name);
	} // setName

	
	/**
	 * Returns the name of the gesture set.
	 * 
	 * @return the gesture set's name.
	 */
	public String getName() {
		return name;
	} // getName

	
	/**
	 * Adds a gesture class to the set.
	 * 
	 * @param gestureClass
	 *            the gesture class to be added.
	 */
	public void addGestureClass(GestureClass gestureClass) {
		this.gestureClasses.add(gestureClass);
		//System.err.println("propertyChangeSupport: " + propertyChangeSupport);
		//propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_CLASSES, gestureClasses.indexOf(gestureClass), null, gestureClass);
	} // addGestureClass

	
	/**
	 * Removes a gesture class from the gesture set.
	 * 
	 * @param gestureClass
	 *            the gesture class to be removed.
	 */
	public void removeGestureClass(GestureClass gestureClass) {
		int index = gestureClasses.indexOf(gestureClass);
		this.gestureClasses.remove(gestureClass);
		propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_CLASSES, index, gestureClass, null);
	} // removeGestureClass

	
	/**
	 * Returns the gesture class at position index.
	 * 
	 * @param index
	 *            the index of the gesture class to be returned.
	 * @return the gesture class at position index.
	 */
	public GestureClass getGestureClass(int index) {
		assert (index >= 0 && index < gestureClasses.size());
		return this.gestureClasses.toArray(new GestureClass[gestureClasses
				.size()])[index];
	} // getGestureClass

	
	/**
	 * Returns the number of gesture classes the gesture set contains.
	 * 
	 * @return the number of gesture classes the gesture set contains.
	 */
	public int size() {
		return gestureClasses.size();
	} // size

	
	/**
	 * Returns the list of all gesture classes the gesture set contains.
	 * 
	 * @return the list of all gesture classes the gesture set contains.
	 */
	public List<GestureClass> getGestureClasses() {
		return gestureClasses;
	} // getGestureClasses

	
	/**
	 * Returns the gesture class with the specified name or null if a gesture
	 * class with the specified name does not exist.
	 * 
	 * @param name
	 *            name of the gesture class to be returned.
	 * @return the gesture class with the given name.
	 */
	public GestureClass getGestureClass(String name) {
		for (final GestureClass gestureClass : gestureClasses) {

			if (gestureClass.getName().equals(name)) {
				return gestureClass;
			}

		}
		
		return null;
	} // getGestureClass
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(GestureClass gestureClass:gestureClasses){
			gestureClass.accept(visitor);
		}
	
	} // accept

	
	@Override
	public String toString() {
		return name;
	} // toString

}
