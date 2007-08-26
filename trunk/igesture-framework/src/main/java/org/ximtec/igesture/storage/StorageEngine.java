/*
 * @(#)StorageEngine.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Interface for StorageEngines implementations
 *                  realising access functionaliy for data sources.  
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

import java.util.List;

import org.ximtec.igesture.core.DataObject;


/**
 * Interface for StorageEngines implementations realising access functionaliy for
 * data sources.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface StorageEngine {

   /**
    * Loads a data object of a specific type.
    */
   public <T extends DataObject> T load(Class<T> clazz, String id);


   /**
    * Loads a collection of data objects of a given type. In general this method
    * retuns all instances of the specific type.
    */
   public <T extends DataObject> List<T> load(Class<T> clazz); 
   
   /**
    * Loads a collection of data objects
    */
   public <T extends DataObject> List<T> load(Class<T> clazz, String fieldName, Object value);


   /**
    * Stores a data object. The engine is responsible to set an unique id to the
    * data object.
    */
   public void store(DataObject dataObjects);


   /**
    * Updates a data object.
    */
   public void update(DataObject obj);


   /**
    * Removes a data object.
    */
   public void remove(DataObject ojb);


   /**
    * Disposes the engine.
    */
   public void dispose();

}
