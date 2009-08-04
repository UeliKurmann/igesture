/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *	
 * Purpose      : 	Storage engine implementation for db4o.
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ximtec.igesture.core.DataObject;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

/**
 * Storage engine implementation for db4o.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Db4oStorageEngine extends DefaultStorageEngine {

  ObjectContainer db;
  
  private Set<Object> objectCache;

  /**
   * Constructs a new db4o engine.
   * 
   * @param filename
   *          the database file.
   */
  public Db4oStorageEngine(String filename) {
    Db4o.configure().activationDepth(Integer.MAX_VALUE);
    Db4o.configure().updateDepth(Integer.MAX_VALUE);
    Db4o.configure().allowVersionUpdates(true);
    Db4o.configure().callConstructors(true);

    db = Db4o.openFile(filename);
    objectCache = new HashSet<Object>();
  }

  public void dispose() {
    db.rollback();
    db.close();
    
  } // dispose

  @SuppressWarnings("serial")
  public <T extends DataObject> T load(final Class<T> clazz, final String id) {
    T result = null;
    final ObjectSet<T> os = db.query(new Predicate<T>() {

      @Override
      public boolean match(T dataObject) {
        return dataObject.getClass().equals(clazz) && dataObject.getId().equals(id);
      }
    });

    if (!os.isEmpty()) {
      result = os.get(0);
    }
    objectCache.add(result);
    return result;
  } // load

  public <T extends DataObject> List<T> load(Class<T> clazz) {
    List<T> result = new ArrayList<T>(db.query(clazz));
    objectCache.addAll(result);
    return result;
  } // load

  public void store(DataObject dataObject) {
    db.set(dataObject);
    objectCache.add(dataObject);
  } // store

  public void update(DataObject dataObject) {
    db.set(dataObject);
    objectCache.add(dataObject);
  } // update

  public void remove(DataObject dataObject) {
    db.delete(dataObject);
    objectCache.remove(dataObject);
  } // remove

  @Override
  public synchronized void commit() {
    for(Object obj:objectCache){
      db.set(obj);
    }
    
    db.commit();
    
    objectCache.clear();
  }

  /*
   * (non-Javadoc)
   * @see org.ximtec.igesture.storage.StorageEngine#copyTo(java.io.File)
   */
  @Override
  public void copyTo(File file) {
    // FIXME
    throw new RuntimeException("Not yet implemented.");
    
  }
  
  

}
