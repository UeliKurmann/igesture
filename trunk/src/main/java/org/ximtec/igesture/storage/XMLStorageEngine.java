/*
 * @(#)XMLStorageEngine.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Storage Engine implementation XML
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
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ximtec.igesture.core.DataObject;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLStorageEngine implements StorageEngine {

	private File xmlFile;;

	private HashMap<Class<? extends DataObject>, List<DataObject>> dataObjects;

	public XMLStorageEngine(String filename) {
		xmlFile = new File(filename);
		if(xmlFile.exists()){
			dataObjects = deserialize(xmlFile);
		}else{
			dataObjects = new HashMap<Class<? extends DataObject>, List<DataObject>>();
		}
	}

	/**
	 * Serialize the internal datastructure to an XML fiel
	 * 
	 * @param objects
	 * @param file
	 */
	private void serialize(
			HashMap<Class<? extends DataObject>, List<DataObject>> objects,
			File file) {
		final XStream xstream = new XStream(new DomDriver());
		final String xml = xstream.toXML(objects);

		try {
			final FileWriter fw = new FileWriter(file);
			fw.write(xml);
			fw.flush();
			fw.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * deserialize an XML file
	 * 
	 * @param file
	 *            the file to be deserialized
	 * @return the internal data structure handling the data objects
	 */
	@SuppressWarnings("unchecked")
	private HashMap<Class<? extends DataObject>, List<DataObject>> deserialize(
			File file) {
		final XStream xstream = new XStream(new DomDriver());
		HashMap<Class<? extends DataObject>, List<DataObject>> dataObjects = null;
		try {
			
			final FileReader fr = new FileReader(file);
			dataObjects = (HashMap<Class<? extends DataObject>, List<DataObject>>) xstream
					.fromXML(fr);
			fr.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final ClassCastException e) {
			e.printStackTrace();
		}
		if (dataObjects == null) {
			dataObjects = new HashMap<Class<? extends DataObject>, List<DataObject>>();
		}
		return dataObjects;
	}

	public void dispose() {
		serialize(dataObjects, xmlFile);
	}

	@SuppressWarnings("unchecked")
	public <T extends DataObject> T load(final Class<T> clazz, final String id) {
		T dataObject = null;
		if (dataObjects.get(clazz) != null) {
			for (final DataObject tmp : dataObjects.get(clazz)) {
				if (tmp.getID().equals(id)) {
					dataObject = (T) tmp;
					break;
				}
			}
		}
		return dataObject;
	}

	@SuppressWarnings("unchecked")
	public <T extends DataObject> List<T> load(Class<T> clazz) {
		if (dataObjects.get(clazz) != null) {
			return (List<T>) dataObjects.get(clazz);
		}
		return new ArrayList<T>();
	}

	public void store(DataObject dataObject) {
		addDataObject(dataObject);
	}

	public void update(DataObject dataObject) {
		addDataObject(dataObject);
	}

	public void remove(DataObject dataObject) {
		removeDataObject(dataObject);
	}

	/**
	 * Adds a DataObject to the object container.
	 * 
	 * @param dataObject
	 *            the DataObject to add
	 */
	private void addDataObject(DataObject dataObject) {
		// create a list for a specific type if it doesn't exist
		if (dataObjects.get(dataObject.getClass()) == null) {
			dataObjects.put(dataObject.getClass(), new ArrayList<DataObject>());
		}

		// only add dataObject if it isn't already present in the list
		if (!dataObjects.get(dataObject.getClass()).contains(dataObject)) {
			dataObjects.get(dataObject.getClass()).add(dataObject);
		}
	}

	/**
	 * Removes a DataObject from the object container
	 * 
	 * @param dataObject
	 *            the DataObject to remove
	 */
	private void removeDataObject(DataObject dataObject) {
		if (dataObjects.get(dataObject.getClass()) != null) {
			dataObjects.get(dataObject.getClass()).remove(dataObject);
		}
	}

}
