package org.ximtec.igesture.io.wiimote;

import java.io.File;
import java.util.List;

import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Sample3DDescriptor;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.storage.StorageManager;

public class TestController {

	private StorageManager storage; // The storage manager for database or xml
	private WiiReader reader; // The WiiReader to read from the WiiMote

	public TestController() {
		storage = new StorageManager(StorageManager
				.createStorageEngine(new File("C:\\gesturedata.db")));
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
			GestureSet tempSet = storage.load(GestureSet.class, "name", setName).get(0);
			tempSet.addGestureClass(newClass);
			storage.update(tempSet);
			storage.commit();
			System.err.println("Added new gesture class with name \"" + className
					+ "\" to gesture set \"" + setName + "\"");
		}
		else
			System.err.println("Nothing added");
	}

	public void addCurrentGestureSampleToGestureClass(String setName, String className){
		System.err.print("addCurrentGestureSampleToGestureClass(): ");
		Sample3DDescriptor sample = new Sample3DDescriptor();
		sample.addSample((GestureSample3D) reader.getGesture());
		if (storage.load(GestureSet.class, "name", setName).size() > 0) {
			GestureSet tempSet = storage.load(GestureSet.class, "name", setName).get(0);
			tempSet.getGestureClass(className).addDescriptor(sample);
			storage.update(tempSet);
			storage.commit();
			System.err.println("Added new GestureSample3D to gesture class with name \"" + className
					+ "\" in gesture set \"" + setName + "\"");
		}
		else
			System.err.println("Nothing added");
	}


	public GestureSample3D getFirstGestureSample(String setName, String className) {
		System.err.println("getFirstGestureSample() for GestureSet " + setName + " and GestureClass " + className + ".");
		if (storage.load(GestureSet.class, "name", setName).size() > 0) {
			GestureSet tempSet = storage.load(GestureSet.class, "name", setName).get(0);
			if(tempSet.getGestureClass(className) != null){
				GestureClass tempClass = tempSet.getGestureClass(className);
				if (tempClass.getDescriptor(Sample3DDescriptor.class) != null){
					return (GestureSample3D)tempClass.getDescriptor(Sample3DDescriptor.class).getSample(0);
				}
			}

		}
		return null;
	}
	
}
