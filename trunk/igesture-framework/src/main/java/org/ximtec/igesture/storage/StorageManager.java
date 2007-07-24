/*
 * @(#)StorageManager.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	The front-end of the storage system. The storage
 *                  manager uses a storage engine which realises access
 *                  functionality to data sources. 
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.util.ReflectTools;


/**
 * The front-end of the storage system. The storage manager uses a storage engine
 * which realises access functionality to data sources.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class StorageManager {

   private static final Logger LOGGER = Logger.getLogger(StorageManager.class
         .getName());

   private StorageEngine storageEngine;

   public enum Filetype {
      db, xml
   };


   /**
    * Instantiates the storage manager with the given storage engine.
    * 
    * @param engine the storage engine to be used.
    */
   public StorageManager(StorageEngine engine) {
      this.storageEngine = engine;
   }


   /**
    * Loads the data object of the given type with the given id.
    * 
    * @param <T>
    * @param clazz the type of the data object to be retrieved.
    * @param id the id of the data object.
    * @return the data object with the given id.
    */
   public <T extends DataObject> T load(Class<T> clazz, String id) {
      return storageEngine.load(clazz, id);
   } // load


   /**
    * Returns a typed list of data objects. All objects available in the
    * collection with the given type are returned.
    * 
    * @param <T>
    * @param clazz the type of the data objects.
    * @return a list of data objects of the given type.
    */
   public <T extends DataObject> List<T> load(Class<T> clazz) {
      return storageEngine.load(clazz);
   } // load


   /**
    * Removes the given data object from the storage.
    */
   public void remove(DataObject obj) {
      storageEngine.remove(obj);
   } // remove


   /**
    * Stores a data object.
    * 
    * @param dataObject the data object to be stored.
    */
   public void store(DataObject dataObject) {
      storageEngine.store(dataObject);
   } // store


   /**
    * Stores a list of data objects.
    * 
    * @param <T>
    * @param dataObjects the data objects to be stored.
    */
   public void store(List<DataObject> dataObjects) {
      for (final DataObject dataObject : dataObjects) {
         store(dataObject);
      }

   } // store


   /**
    * Updates a data object.
    * 
    * @param dataObject the data object to be updated.
    */
   public void update(DataObject dataObject) {
      storageEngine.update(dataObject);
   } // update


   /**
    * Updates a list of data objects.
    * 
    * @param dataObjects the list of data objects to be updated.
    */
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
      return org.safehaus.uuid.UUIDGenerator.getInstance()
            .generateRandomBasedUUID().toString();
   } // generateUUID


   /**
    * Creates a new storage engine. The type of the storage engine is determined
    * based on the file extension. Dynamic class loading should be used to avoid
    * any license conflicts.
    * 
    * @param file the file from which the storage engine is created.
    * @return the new storage engine.
    */
   public static StorageEngine createStorageEngine(File file) {
      StorageEngine engine = null;
      switch (getFileType(file)) {
         case db:
            engine = new Db4oStorageEngine(file.getPath());
            break;
         case xml:
            engine = new XMLStorageEngine(file.getPath());
            break;
         default:
      }
      return engine;
   } // createStorageEngine


   public static Filetype getFileType(File file) {
      String extension = file.getName().substring(
            file.getName().lastIndexOf(Constant.DOT) + 1);
      return Filetype.valueOf(extension);
   } // getFileType


   /**
    * Disposes the storage engine.
    */
   public void dispose() {
      storageEngine.dispose();
   } // dispose
   
   public <T extends DataObject> List<T> load(Class<T> clazz, String fieldName, Object value){
	   List<T> result = new ArrayList<T>();
	   for(T dataObject:load(clazz)){
		   if(value == null){
			   if(ReflectTools.getFieldValue(dataObject, fieldName) == null){
				   result.add(dataObject);
			   }
		   }else{
			   if(value.equals(ReflectTools.getFieldValue(dataObject, fieldName))){
				   result.add(dataObject);
			   }
		   }
	   }
	   return result;
   }

}
