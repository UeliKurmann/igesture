/*
 * @(#)$Id: Gesture3DTool.java
 *
 * Author		:	Arthur Vogels, arthur.vogels@gmail.com
 *                  
 *
 * Purpose		:   Tool for creating and manipulating 3D Gesture 
 * 					sets with the WiiMote.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 15.01.2009		vogelsar	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.algorithm.rubine3d.tools;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.rubine3d.Rubine3DAlgorithm;
import org.ximtec.igesture.algorithm.siger.SigerAlgorithm;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.io.wiimote.WiiReader;
import org.ximtec.igesture.io.wiimote.WiiReaderPanel;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

public class Gesture3DTool {

	private StorageManager storage; // The storage manager for database or xml
	private WiiReader reader; // The WiiReader to read from the WiiMote
	private Recogniser recogniser; // The recogniser
	private Gesture3DToolUI ui;

	public Gesture3DTool(String gestureSetName) {
		storage = new StorageManager(StorageManager
				.createStorageEngine(new File(gestureSetName)));
		reader = new WiiReader();
	}

	/**
	 * Initialises the WiiReader
	 * 
	 */
	public void initWiiMote() {
		reader.init();
	}

	/**
	 * Disconnects the wiimote
	 */
	public void disconnectWiiMote() {
		reader.disconnect();
	}

	public void setUI(Gesture3DToolUI ui) {
		this.ui = ui;
	}

	/**
	 * Returns the panel of the WiiReader in this controller
	 * 
	 * @return The panel of the WiiReader in this controller
	 */
	public WiiReaderPanel getWiiReaderPanel() {
		return reader.getPanel();
	}

	/**
	 * Adds a new GestureSet with name setName to the database
	 * 
	 * @param setName
	 *            The name of the new GestureSet
	 */
	public void addGestureSet(String setName) {
		System.err.println("Adding new gesture set with name \"" + setName
				+ "\"");
		GestureSet set = new GestureSet(setName);
		storage.store(set);
		storage.commit();
	}

	/**
	 * Returns a list of all the GestureSets in the database
	 * 
	 * @return
	 */
	public List<GestureSet> getGestureSets() {
		return storage.load(GestureSet.class);
	}

	/**
	 * Returns GestureSet with name setName from the database
	 * 
	 * @param setName
	 *            The name of the GestureSet that is to be retrieved
	 * @return The retrieved GestureSet
	 */
	public GestureSet getGestureSet(String setName) {
		if (storage.load(GestureSet.class, "name", setName).size() > 0)
			return storage.load(GestureSet.class, "name", setName).get(0);
		else
			return null;
	}

	/**
	 * Adds a new gesture class with name className to a gesture set with name
	 * setName
	 * 
	 * @param setName
	 * @param className
	 */
	public void addGestureClass(String setName, String className) {
		System.err.print("addGestureClass(): ");
		GestureClass newClass = new GestureClass(className);
		if (storage.load(GestureSet.class, "name", setName).size() > 0) {
			GestureSet tempSet = storage
					.load(GestureSet.class, "name", setName).get(0);
			tempSet.addGestureClass(newClass);
			storage.update(tempSet);
			storage.commit();
			System.err.println("Added new gesture class with name \""
					+ className + "\" to gesture set \"" + setName + "\"");
		} else
			System.err.println("Nothing added");
	}

	/**
	 * Adds the gesture sample that is read by the WiiReader reader to the
	 * Sample3DDescriptor in gesture class with name className in gesture set
	 * with name setName. If no Sample3DDescriptor is present in the gesture
	 * class a new one is created
	 * 
	 * @param setName
	 *            The name of the gesture set that contains the gesture class
	 * @param className
	 *            The name of the gesture class the sample should be added to
	 */
	public void addCurrentGestureSampleToGestureClass(String setName,
			String className) {
		System.err.print("addCurrentGestureSampleToGestureClass(): ");
		// If the gesture set with given name exists
		if (storage.load(GestureSet.class, "name", setName).size() > 0) {
			GestureSet tempSet = storage
					.load(GestureSet.class, "name", setName).get(0);
			// If the gesture set contains a gesture class with name className
			if (tempSet.getGestureClass(className) != null) {
				// If the gesture class does not contain a Sample3DDescriptor
				// yet
				if (tempSet.getGestureClass(className).getDescriptor(
						SampleDescriptor.class) == null) {
					SampleDescriptor desc = new SampleDescriptor();
					tempSet.getGestureClass(className).addDescriptor(desc);
				}
				GestureSample3D gesture = (GestureSample3D) reader.getGesture();
				// If the reader has a valid gesture sample available
				if (gesture.getGesture().getAccelerations() != null) {
					// Add the sample from Wiireader to the Sample3DDescriptor
					tempSet.getGestureClass(className).getDescriptor(
							SampleDescriptor.class).addSample(gesture);
					// Update the set in the database and commit changes
					storage.update(tempSet);
					storage.commit();
					System.err.println("Added GestureSample3D with name \""
							+ gesture.getName()
							+ "\" to gesture class with name \"" + className
							+ "\" in gesture set \"" + setName + "\"");
					// Job's done

					// DEBUG

					Iterator i = tempSet.getGestureClass(className)
							.getDescriptor(SampleDescriptor.class).getSamples()
							.iterator();
					while (i.hasNext()) {
						GestureSample3D tempSample = (GestureSample3D) i.next();
						System.out.println("Name: \"" + tempSample.getName()
								+ "\"");
					}

					return;
				}
			}
		}
		System.err.println("Nothing added");
	}

	/**
	 * Returns a list of gesture samples from gesture class with name className
	 * from gesture set with name setName
	 * 
	 * @param setName
	 *            The name of the gesture set to look in
	 * @param className
	 *            The name of the gesture class to look in
	 * @return The list of found gesture samples
	 */
	public List<Gesture<?>> getGestureSamples(String setName, String className) {
		System.err.println("getGestureSamples() for GestureSet " + setName
				+ " and GestureClass " + className + ".");
		// Create return variable
		List<Gesture<?>> returnList = new Vector<Gesture<?>>();
		// If there is a gesture set with name setName
		if (storage.load(GestureSet.class, "name", setName).size() > 0) {
			GestureSet tempSet = storage
					.load(GestureSet.class, "name", setName).get(0);
			// If there is a gesture class with name className in tempSet
			if (tempSet.getGestureClass(className) != null) {
				GestureClass tempClass = tempSet.getGestureClass(className);
				// Get descriptor from gesture class if it contains a
				// SampleDescriptor
				SampleDescriptor descriptor = tempClass
						.getDescriptor(SampleDescriptor.class);
				// If there is a descriptor, take the list of samples from the
				// descriptor
				if (descriptor != null)
					returnList = descriptor.getSamples();
				else
					System.err
							.println("There is no SampleDescriptor in gesture class with name \""
									+ className
									+ "\" in gesture set \""
									+ setName + "\".");
			} else
				System.err.println("There is no gesture class with name \""
						+ className + "\" in gesture set \"" + setName + "\".");
		} else
			System.err.println("There is no gesture set with name \"" + setName
					+ "\".");
		// Return list
		return returnList;
	}

	/**
	 * Recognises the current gesture from the WiiReader against the gesture set
	 * with name setName
	 * 
	 * @param setName
	 *            the set to be recognised against
	 */
	public void recognise(String setName) {
		// Configure recogniser
		new Rubine3DAlgorithm();
		Configuration config = new Configuration();
		config.addAlgorithm(Rubine3DAlgorithm.class.getName());
		config.addGestureSet(getGestureSet(setName));
		try {
			recogniser = new Recogniser(config);
		} catch (AlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Recognise
		ResultSet resultSet = recogniser.recognise(reader.getGesture());
		ui.setResultField(resultSet);
		//
		System.err.println("Number of Results in ResultSet: "
				+ resultSet.getResults().size());
	}

	/**
	 * Removes Sample with the number sampleNumber from the gesture class with
	 * name className from gesture set with name setName
	 * 
	 * @param setName
	 * @param className
	 * @param sampleNumber
	 */
	public void removeSample(String setName, String className, int sampleNumber) {
		GestureSet tempSet = storage.load(GestureSet.class, "name", setName)
				.get(0);
		if (tempSet.getGestureClass(className).getDescriptor(
				SampleDescriptor.class).getSamples().size() > 0) {
			tempSet.getGestureClass(className).getDescriptor(
					SampleDescriptor.class).getSamples().remove(sampleNumber);
			storage.update(tempSet);
		} else
			System.err
					.println("Gesture3DTool: No samples can be removed from Gesture class "
							+ className
							+ " in gesture set "
							+ setName
							+ " because there are no samples present.");

	}

	/**
	 * Removes the gesture set with name setName from storage
	 * 
	 * @param setName
	 */
	public void removeSet(String setName) {
		if (storage.load(GestureSet.class, "name", setName).get(0) != null) {
			storage.remove(storage.load(GestureSet.class, "name", setName).get(
					0));
		} else
			System.err.println("Gesture3DTool: Gesture set " + setName
					+ " can not be removed because it does not exist");
	}

	/**
	 * Removes the gesture class with name className from gesture set with name
	 * setName in storage
	 * 
	 * @param setName
	 * @param className
	 */
	public void removeClass(String setName, String className) {
		GestureSet tempSet = storage.load(GestureSet.class, "name", setName)
				.get(0);
		if (tempSet.getGestureClass(className) != null) {
			tempSet.removeGestureClass(tempSet.getGestureClass(className));
			storage.update(tempSet);
		} else
			System.err.println("Gesture3DTool: Gesture class " + className
					+ " in gesture set " + setName
					+ " can not be removed because it does not exist");

	}

}
