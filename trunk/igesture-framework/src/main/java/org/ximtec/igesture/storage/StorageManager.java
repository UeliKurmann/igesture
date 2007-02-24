/*
 * @(#)StorageManager.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	The front-end of the storage system. The storage manager
 * 					uses a storage engine which realises access funktionality
 * 					to data sources. 
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

package org.ximtec.igesture.storage;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.DataObject;

/**
 * @version 1.0, Dec. 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */

public class StorageManager {

	private StorageEngine storageEngine;

	public enum Filetype {
		db, xml
	};

	/**
	 * Instantiates the storage manager with the given storage engine
	 * 
	 * @param engine
	 *            the storage engine to be used
	 */
	public StorageManager(StorageEngine engine) {
		this.storageEngine = engine;
	}

	/**
	 * Loads the DataObject of the given type with the given id
	 * 
	 * @param <T>
	 * @param clazz
	 *            the type of the dataobject to be retrieved
	 * @param id
	 *            the id of the dataobject
	 * @return the DataObject with the given id
	 */
	public <T extends DataObject> T load(Class<T> clazz, String id) {
		return storageEngine.load(clazz, id);
	}

	/**
	 * Returns a typed list of dataobjects. all objects available int the
	 * collection with the given type are returned.
	 * 
	 * @param <T>
	 * @param clazz
	 *            the type of the dataobjects
	 * @return a list of dataobjects of the given type
	 */
	public <T extends DataObject> List<T> load(Class<T> clazz) {
		return storageEngine.load(clazz);
	}

	/**
	 * Removes the given dataobject from the storage
	 * 
	 * @param <T>
	 * @param obj
	 */
	public void remove(DataObject obj) {
		storageEngine.remove(obj);
	}

	/**
	 * Stores a DataObject
	 * 
	 * @param dataObjects
	 */
	public void store(DataObject dataObjects) {
		storageEngine.store(dataObjects);
	}

	/**
	 * Sotreas a list of DataObjects
	 * 
	 * @param <T>
	 * @param dataObjects
	 */
	public void store(List<DataObject> dataObjects) {
		for (final DataObject dataObject : dataObjects) {
			store(dataObject);
		}
	}

	/**
	 * Updates a DataObject
	 * 
	 * @param obj
	 */
	public void update(DataObject obj) {
		storageEngine.update(obj);
	}

	/**
	 * Updates a list of DataObjects
	 * 
	 * @param list
	 */
	public void update(List<DataObject> list) {
		for (final DataObject obj : list) {
			update(obj);
		}
	}

	/**
	 * Generates a UUID. This id is used to identify DataObejcts
	 * 
	 * @return an new UUID
	 */
	public static String generateUUID() {
		return org.safehaus.uuid.UUIDGenerator.getInstance()
				.generateRandomBasedUUID().toString();
	}

	/**
	 * Creates a new StorageEngine. The type of the storage engine is determined
	 * on basis of the file extension. Dynamical class loading should be used to
	 * avoid license conflicts.
	 * 
	 * @param file
	 * @return
	 */
	public static StorageEngine createStorageEngine(File file) {
		StorageEngine engine = null;
		switch (getFileType(file)) {
		case db:
			try {
				Class db4oEngine = Class.forName(Db4oStorageEngine.class.toString());
				Constructor constructor = db4oEngine.getConstructor(new Class[]{String.class});
				engine = (StorageEngine)constructor.newInstance(new Object[]{file.getPath()});	
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case xml:
			engine =  new XMLStorageEngine(file.getPath());
			break;
		default:
		}
		return engine;
	}

	public static Filetype getFileType(File file) {
		String extension = file.getName().substring(
				file.getName().lastIndexOf(Constant.DOT) + 1);
		return Filetype.valueOf(extension);
	}

	/**
	 * Disposes the storage engine
	 * 
	 */
	public void dispose() {
		storageEngine.dispose();
	}
}
