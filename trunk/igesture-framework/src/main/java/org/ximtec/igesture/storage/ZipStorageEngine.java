/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 28.04.2008			ukurmann	Initial Release
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.util.XMLTool;
import org.ximtec.igesture.util.ZipFS;


/**
 * Comment
 * @version 1.0 28.04.2008
 * @author Ueli Kurmann
 */
public class ZipStorageEngine extends DefaultStorageEngine {

   private static final Logger LOGGER = Logger.getLogger(XMLStorageEngine.class
         .getName());

   private static final String GESTURE_SET = "GestureSet";

   private Map<Class< ? extends DataObject>, List<DataObject>> dataObjects;

   private ZipFS zipFS;

   private final static String GESTURE_SET_PATH = "sets";
   private final static String CONFIGURATION_PATH = "configs";
   private final static String TEST_SET_PATH = "testsets";
   private final static String XML_EXTENSION = ".xml";

   private String filename;

   private boolean changed;


   public ZipStorageEngine(String filename) {
      this.filename = filename;
      changed = false;
      initData();
   }


   private void initData() {
      try {
         File file = new File(filename);
         zipFS = new ZipFS(file);
      }
      catch (IOException e) {
         LOGGER.log(Level.SEVERE, "Could not initialise ZIP Storage Engine.", e);
         return;
      }

      dataObjects = new HashMap<Class< ? extends DataObject>, List<DataObject>>();

      dataObjects.put(GestureSet.class, new ArrayList<DataObject>());
      initGestureSet();

      dataObjects.put(Configuration.class, new ArrayList<DataObject>());
      initConfig();

      dataObjects.put(TestSet.class, new ArrayList<DataObject>());
      initTestSet();
   }


   private void initGestureSet() {
      for (ZipEntry entry : zipFS.listFiles(GESTURE_SET_PATH)) {
         if (entry.isDirectory()) {
            for (ZipEntry entrySet : zipFS.listFiles(entry.getName())) {
               if (entrySet.getName().toLowerCase().endsWith(XML_EXTENSION)) {
                  try {
                     GestureSet gestureSet = XMLTool.importGestureSet(zipFS
                           .getInputStream(entrySet));
                     dataObjects.get(GestureSet.class).add(gestureSet);

                     // TODO load images

                  }
                  catch (IOException e) {
                     LOGGER.log(Level.SEVERE, "Could not import GestureSet.", e);
                  }
               }
            }
         }
      }
   }


   private void initTestSet() {
      for (ZipEntry entry : zipFS.listFiles(TEST_SET_PATH)) {
         if (entry.getName().toLowerCase().endsWith(XML_EXTENSION)) {
            try {
               TestSet testSet = XMLTool.importTestSet(zipFS
                     .getInputStream(entry));

               if (testSet != null) {
                  dataObjects.get(TestSet.class).add(testSet);
               }

            }
            catch (IOException e) {
               LOGGER.log(Level.SEVERE, "Could not import Test sets.", e);
            }
         }
      }
   }


   private void initConfig() {
      for (ZipEntry entry : zipFS.listFiles(CONFIGURATION_PATH)) {
         if (entry.getName().toLowerCase().endsWith(XML_EXTENSION)) {
            try {
               Configuration config = XMLTool.importConfiguration(zipFS
                     .getInputStream(entry));
               dataObjects.get(Configuration.class).add(config);
            }
            catch (IOException e) {
               LOGGER.log(Level.SEVERE, "Could not import Configuration.", e);
            }
         }
      }
   }


   @Override
   public void commit() {
      // FIXME why always true? 
      if (changed || true) {
         // persist gesture sets
         persistGestureSets(dataObjects.get(GestureSet.class));

         // persist configurations
         persistConfiguration(dataObjects.get(Configuration.class));

         // persist test sets
         persistTestSets(dataObjects.get(TestSet.class));

         // FIXME why close zipFS?
         try {
            zipFS.close();
         }
         catch (IOException e) {
            e.printStackTrace();
         }

         initData();

         changed = false;
      }
   }


   private void persistGestureSets(List<DataObject> gestureSets) {
      for (DataObject dataObject : gestureSets) {
         try {
            GestureSet set = (GestureSet)dataObject;
            String name = getGestureSetPath(set.getId());
            zipFS.store(name, XMLTool.exportGestureSetAsStream(set));
         }
         catch (IOException e) {
            e.printStackTrace();
         }
      }
   }


   private String getGestureSetPath(String id) {
      return GESTURE_SET_PATH + ZipFS.SEPERATOR + id + ZipFS.SEPERATOR
            + GESTURE_SET + XML_EXTENSION;
   }


   private void persistConfiguration(List<DataObject> configurations) {
      for (DataObject dataObject : configurations) {
         try {
            Configuration config = (Configuration)dataObject;
            String name = getConfigurationPath(config.getId());
            zipFS.store(name, XMLTool.exportConfigurationAsStream(config));
         }
         catch (IOException e) {
            e.printStackTrace();
         }
      }
   }


   private String getConfigurationPath(String id) {
      return CONFIGURATION_PATH + ZipFS.SEPERATOR + id + XML_EXTENSION;
   }


   private void persistTestSets(List<DataObject> testSets) {
      for (DataObject dataObject : testSets) {
         try {
            TestSet testSet = (TestSet)dataObject;
            String name = getTestSetPath(testSet.getId());
            zipFS.store(name, XMLTool.exportTestSetAsStream(testSet));
         }
         catch (IOException e) {
            e.printStackTrace();
         }
      }
   }


   private String getTestSetPath(String id) {
      return TEST_SET_PATH + ZipFS.SEPERATOR + id + XML_EXTENSION;
   }


   @Override
   public void dispose() {
      try {
        zipFS.close();
      } catch (IOException e) {
        LOGGER.info("Could not close the ZIP file");
      }
   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.storage.StorageEngine#load(java.lang.Class,
    *      java.lang.String)
    */
   @Override
   public <T extends DataObject> T load(final Class<T> clazz, final String id) {
      T dataObject = null;

      if (dataObjects.get(clazz) != null) {

         for (final DataObject tmp : dataObjects.get(clazz)) {

            if (tmp.getId().equals(id)) {
               dataObject = clazz.cast(tmp);
               break;
            }

         }

      }

      return dataObject;
   } // load


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.storage.StorageEngine#load(java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   @Override
   public <T extends DataObject> List<T> load(Class<T> clazz) {
      if (dataObjects.get(clazz) != null) {
         return new ArrayList<T>((List<T>)dataObjects.get(clazz));
      }

      return new ArrayList<T>();
   } // load


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.storage.StorageEngine#store(org.ximtec.igesture.core.DataObject)
    */
   @Override
   public void store(DataObject dataObject) {
      addDataObject(dataObject);
      changed = true;
   } // store


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.storage.StorageEngine#update(org.ximtec.igesture.core.DataObject)
    */
   @Override
   public void update(DataObject dataObject) {
      addDataObject(dataObject);
      changed = true;
   } // update


   
   /*
    * Attention: Only Configuration, TestSet, GestureSet are processed!
    * TODO: OOP implementation
    * (non-Javadoc)
    * @see org.ximtec.igesture.storage.StorageEngine#remove(org.ximtec.igesture.core.DataObject)
    */
   @Override
   public void remove(DataObject dataObject) {
      removeDataObject(dataObject);
      String path = null;
      if (dataObject instanceof Configuration) {
         path = getConfigurationPath(dataObject.getId());
      }
      else if (dataObject instanceof GestureSet) {
         path = getGestureSetPath(dataObject.getId());
      }
      else if (dataObject instanceof TestSet) {
         path = getTestSetPath(dataObject.getId());
      }
      
      if(path != null){
         zipFS.delete(path);
         changed = true;
      }
   } // remove


   /**
    * Adds a data object to the object container.
    * 
    * @param dataObject the data object to be added.
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

   } // addDataObject


   /**
    * Removes a data object from the object container.
    * 
    * @param dataObject the data object to be removed.
    */
   private void removeDataObject(DataObject dataObject) {
      if (dataObjects.get(dataObject.getClass()) != null) {
         dataObjects.get(dataObject.getClass()).remove(dataObject);
      }

   } // removeDataObject

}
