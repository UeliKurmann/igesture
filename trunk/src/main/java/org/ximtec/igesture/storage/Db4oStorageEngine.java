/*
 * @(#)Db4oStorageEngine.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *	
 * Purpose      : 	Storage Engine implementation db4objects
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
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;


public class Db4oStorageEngine implements StorageEngine {

   ObjectContainer db;


   /**
    * Constructor
    * 
    * @param filename the database file
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
   }


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
   }


   public <T extends DataObject> List<T> load(Class<T> clazz) {
      return db.query(clazz);
   }


   public void store(DataObject dataObject) {
      db.set(dataObject);
   }


   public void update(DataObject dataObject) {
      db.set(dataObject);
   }


   public void remove(DataObject dataObject) {
      db.delete(dataObject);
   }

}
