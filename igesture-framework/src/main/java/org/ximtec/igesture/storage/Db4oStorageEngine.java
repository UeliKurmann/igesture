/*
 * @(#)Db4oStorageEngine.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.storage;

import java.util.List;

import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;


/**
 * Storage engine implementation for db4o.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Db4oStorageEngine implements StorageEngine {

   ObjectContainer db;


   /**
    * Constructs a new db4o engine.
    * 
    * @param filename the database file.
    */
   public Db4oStorageEngine(String filename) {
      Db4o.configure().activationDepth(Integer.MAX_VALUE);
      Db4o.configure().updateDepth(Integer.MAX_VALUE);
      Db4o.configure().objectClass(GestureSet.class.getName()).cascadeOnUpdate(
            true);
      Db4o.configure().objectClass(GestureClass.class.getName())
            .cascadeOnUpdate(true);
      Db4o.configure().objectClass(SampleDescriptor.class.getName())
            .cascadeOnUpdate(true);
      Db4o.configure().allowVersionUpdates(true);
      System.out.println(filename);
      db = Db4o.openFile(filename);
   }


   public void dispose() {
      db.close();
   } // dispose


   @SuppressWarnings("serial")
   public <T extends DataObject> T load(final Class<T> clazz, final String id) {
      T result = null;
      final ObjectSet<T> os = db.query(new Predicate<T>() {

         @Override
         public boolean match(T dataObject) {
            return dataObject.getClass().equals(clazz)
                  && dataObject.getID().equals(id);
         }
      });

      if (!os.isEmpty()) {
         result = os.get(0);
      }

      return result;
   } // load


   public <T extends DataObject> List<T> load(Class<T> clazz) {
      return db.query(clazz);
   } // load


   public void store(DataObject dataObject) {
      db.set(dataObject);
   } // store


   public void update(DataObject dataObject) {
      db.set(dataObject);
   } // update


   public void remove(DataObject dataObject) {
      db.delete(dataObject);
   } // remove

}
