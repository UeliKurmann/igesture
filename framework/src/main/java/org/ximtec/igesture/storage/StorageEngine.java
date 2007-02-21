/*
 * @(#)StorageEngine.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Interface for StorageEngines
 * 					Implementations realising access functionaliy
 * 					for data sources.  
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

import java.util.List;

import org.ximtec.igesture.core.DataObject;


public interface StorageEngine {

   /**
    * loads a dataobject of a specific type
    */
   public <T extends DataObject> T load(Class<T> clazz, String id);


   /**
    * loads a collection of dataobjects of a given type. in general this method
    * retuns all instances of the specific type.
    * 
    * @param clazz
    * @return
    */
   public <T extends DataObject> List<T> load(Class<T> clazz);


   /**
    * stores a dataobject. the engine is responsible to set an unique id to the
    * dataobject.
    * 
    * @param dataObjects
    * @return
    */
   public void store(DataObject dataObjects);


   /**
    * updates a dataobject
    * 
    * @param obj
    */
   public void update(DataObject obj);


   /**
    * Removes a data object
    * 
    * @param ojb
    */
   public void remove(DataObject ojb);


   /**
    * Disposes the Engine
    */
   public void dispose();
}
