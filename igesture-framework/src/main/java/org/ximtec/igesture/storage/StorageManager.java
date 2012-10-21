/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	The front-end of the storage system. The storage
 *                  manager uses a storage engine which provides
 *                  functionality to access different data sources. 
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.storage;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.DataObject;

/**
 * The front-end of the storage system. The storage manager uses a storage
 * engine which provides functionality to access different data sources.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class StorageManager implements IStorageManager {

  private static final Logger LOGGER = Logger.getLogger(StorageManager.class.getName());

  private static final String LOADING_DB = "Loading database from '";

  private StorageEngine storageEngine;
  
  

  public enum StorageEngineType {
    igd, igdc, igx, igz
  }

  /**
   * Instantiates the storage manager with the given storage engine.
   * 
   * @param engine
   *          the storage engine to be used.
   */
  public StorageManager(StorageEngine engine) {
    this.storageEngine = engine;
  }

  /**
   * Loads the data object of the given type with the given id.
   * 
   * @param clazz
   *          the type of the data object to be retrieved.
   * @param id
   *          the id of the data object.
   * @return the data object with the given id.
   */
  @Override
  public <T extends DataObject> T load(Class<T> clazz, String id) {
    return storageEngine.load(clazz, id);
  } // load

  /**
   * Returns a typed list of data objects. All objects available in the
   * collection with the given type are returned.
   * 
   * @param clazz
   *          the type of the data objects.
   * @return a list of data objects of the given type.
   */
  @Override
  public <T extends DataObject> List<T> load(Class<T> clazz) {
    return storageEngine.load(clazz);
  } // load

  /**
   * Removes the given data object from the storage.
   */
  @Override
  public void remove(DataObject dataObject) {
    storageEngine.remove(dataObject);
  } // remove

  /**
   * Stores a data object.
   * 
   * @param object
   *          the data object to be stored.
   */
  @Override
  public void store(DataObject dataObject) {
    storageEngine.store(dataObject);
  } // store

  /**
   * Stores a list of data objects.
   * 
   * @param dataObjects
   *          the data objects to be stored.
   */
  @Override
  public void store(List<DataObject> dataObjects) {
    for (final DataObject dataObject : dataObjects) {
      store(dataObject);
    }

  } // store

  /**
   * Updates a data object.
   * 
   * @param dataObject
   *          the data object to be updated.
   */
  @Override
  public void update(DataObject dataObject) {
    storageEngine.update(dataObject);
  } // update

  /**
   * Updates a list of data objects.
   * 
   * @param dataObjects
   *          the list of data objects to be updated.
   */
  @Override
  public void update(List<DataObject> dataObjects) {
    for (final DataObject obj : dataObjects) {
      update(obj);
    }

  } // update

  /**
   * Generates a UUID. This id is used to identify data objects.
   * 
   * @return an new UUID
   */
  public static String generateUUID() {
    return org.safehaus.uuid.UUIDGenerator.getInstance().generateRandomBasedUUID().toString();
  } // generateUUID

  /**
   * Creates a new storage engine. The type of the storage engine is determined
   * based on the file extension. Dynamic class loading should be used to avoid
   * any license conflicts.
   * 
   * @param file
   *          the file from which the storage engine is created.
   * @return the new storage engine.
   */
  public static StorageEngine createStorageEngine(File file) {
    LOGGER.info(LOADING_DB + file + Constant.SINGLE_QUOTE);
    StorageEngine engine = null;

    switch (getEngineType(file)) {
    case igd:
    case igdc:
      engine = new Db4oStorageEngine(file.getPath());
      break;
    case igx:
      engine = new XMLStorageEngine(file.getPath());
      break;
    case igz:
      engine = new ZipStorageEngine(file.getPath());
      break;
    default:
    }

    return engine;
  } // createStorageEngine

  public static StorageEngineType getEngineType(File file) {
    String extension = file.getName().substring(file.getName().lastIndexOf(Constant.DOT) + 1);
    return StorageEngineType.valueOf(extension);
  } // getFileType

  /**
   * Disposes the storage engine.
   */
  @Override
  public void dispose() {
    if (storageEngine != null) {
      storageEngine.dispose();
    }
  } // dispose

  @Override
  public <T extends DataObject> List<T> load(Class<T> clazz, String fieldName, Object value) {
    return storageEngine.load(clazz, fieldName, value);
  } // load

  @Override
  public void commit() {
    if (storageEngine != null) {
      storageEngine.commit();
    }
  } // commit
  
  /*
   * (non-Javadoc)
   * @see org.ximtec.igesture.storage.StorageEngine#copyTo(java.io.File)
   */
  @Override
  public void copyTo(File file) {
    storageEngine.copyTo(file);
    
  }

}
