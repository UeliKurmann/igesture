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
 * 
 * @version 1.0 28.04.2008
 * @author Ueli Kurmann
 */
public class ZipStorageEngine extends DefaultFileStorageEngine {

  private static final Logger LOGGER = Logger.getLogger(XMLStorageEngine.class.getName());

  private final static String GESTURE_SET = "GestureSet";
  private final static String GESTURE_SET_PATH = "sets";
  private final static String CONFIGURATION_PATH = "configs";
  private final static String TEST_SET_PATH = "testsets";
  private final static String XML_EXTENSION = ".xml";

  private List<DataObject> dataObjectsToRemove;

  // private ZipFS zipFS;

  public ZipStorageEngine(String filename) {
    super(new File(filename));
    dataObjectsToRemove = new ArrayList<DataObject>();
  }

  private List<DataObject> initGestureSet(ZipFS zipFS) {
    List<DataObject> dataObjects = new ArrayList<DataObject>();

    for (ZipEntry entry : zipFS.listFiles(GESTURE_SET_PATH)) {
      if (entry.isDirectory()) {
        for (ZipEntry entrySet : zipFS.listFiles(entry.getName())) {
          if (entrySet.getName().toLowerCase().endsWith(XML_EXTENSION)) {
            try {
              GestureSet gestureSet = XMLTool.importGestureSet(zipFS.getInputStream(entrySet));
              dataObjects.add(gestureSet);

              // TODO load images

            } catch (IOException e) {
              LOGGER.log(Level.SEVERE, "Could not import GestureSet.", e);
            }
          }
        }
      }
    }

    return dataObjects;
  }

  private List<DataObject> initTestSet(ZipFS zipFS) {

    List<DataObject> dataObjects = new ArrayList<DataObject>();

    for (ZipEntry entry : zipFS.listFiles(TEST_SET_PATH)) {
      if (entry.getName().toLowerCase().endsWith(XML_EXTENSION)) {
        try {
          TestSet testSet = XMLTool.importTestSet(zipFS.getInputStream(entry));

          if (testSet != null) {
            dataObjects.add(testSet);
          }

        } catch (IOException e) {
          LOGGER.log(Level.SEVERE, "Could not import Test sets.", e);
        }
      }
    }

    return dataObjects;
  }

  private List<DataObject> initConfig(ZipFS zipFS) {

    List<DataObject> dataObjects = new ArrayList<DataObject>();
    for (ZipEntry entry : zipFS.listFiles(CONFIGURATION_PATH)) {
      if (entry.getName().toLowerCase().endsWith(XML_EXTENSION)) {
        try {
          Configuration config = XMLTool.importConfiguration(zipFS.getInputStream(entry));
          dataObjects.add(config);
        } catch (IOException e) {
          LOGGER.log(Level.SEVERE, "Could not import Configuration.", e);
        }
      }
    }

    return dataObjects;
  }

  private void persistGestureSets(List<DataObject> gestureSets, ZipFS zipFS) {
    for (DataObject dataObject : gestureSets) {
      try {
        GestureSet set = (GestureSet) dataObject;
        String name = getGestureSetPath(set.getId());
        zipFS.store(name, XMLTool.exportGestureSetAsStream(set));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private String getGestureSetPath(String id) {
    return GESTURE_SET_PATH + ZipFS.SEPERATOR + id + ZipFS.SEPERATOR + GESTURE_SET + XML_EXTENSION;
  }

  private void persistConfiguration(List<DataObject> configurations, ZipFS zipFS) {
    for (DataObject dataObject : configurations) {
      try {
        Configuration config = (Configuration) dataObject;
        String name = getConfigurationPath(config.getId());
        zipFS.store(name, XMLTool.exportConfigurationAsStream(config));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private String getConfigurationPath(String id) {
    return CONFIGURATION_PATH + ZipFS.SEPERATOR + id + XML_EXTENSION;
  }

  private void persistTestSets(List<DataObject> testSets, ZipFS zipFS) {
    for (DataObject dataObject : testSets) {
      try {
        TestSet testSet = (TestSet) dataObject;
        String name = getTestSetPath(testSet.getId());
        zipFS.store(name, XMLTool.exportTestSetAsStream(testSet));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private String getTestSetPath(String id) {
    return TEST_SET_PATH + ZipFS.SEPERATOR + id + XML_EXTENSION;
  }

  @Override
  public void dispose() {
    // FIXME clear dataobjects list?
    LOGGER.info("Dispose the ZIP Storage Engine ZIP file");

  }

  /*
   * Attention: Only Configuration, TestSet, GestureSet are processed! TODO: OOP
   * implementation (non-Javadoc)
   * 
   * @see
   * org.ximtec.igesture.storage.StorageEngine#remove(org.ximtec.igesture.core
   * .DataObject)
   */
  @Override
  public void remove(DataObject dataObject) {
    removeDataObject(dataObject);
    dataObjectsToRemove.add(dataObject);
    setDoChanged(true);

  } // remove

  /*
   * (non-Javadoc)
   * 
   * @see org.ximtec.igesture.storage.StorageEngine#copyTo(java.io.File)
   */
  @Override
  public synchronized void copyTo(File file) {
    setStorageFile(file);
    commit();

  }

  @Override
  protected HashMap<Class<? extends DataObject>, List<DataObject>> deserialize(File storageFile) {
    HashMap<Class<? extends DataObject>, List<DataObject>> dataObjects = new HashMap<Class<? extends DataObject>, List<DataObject>>();

    try {
      ZipFS zipFS = new ZipFS(storageFile);
      dataObjects.put(GestureSet.class, initGestureSet(zipFS));

      dataObjects.put(Configuration.class, initConfig(zipFS));

      dataObjects.put(TestSet.class, initTestSet(zipFS));
      
      zipFS.close();

    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Could not initialise ZIP Storage Engine.", e);
      return new HashMap<Class<? extends DataObject>, List<DataObject>>();
    }

    return dataObjects;
  }

  @Override
  protected void serialize(HashMap<Class<? extends DataObject>, List<DataObject>> dataObjects, File file) {

    if (isDoChanged() || true) {
      try {

        if (file.exists()) {
          file.delete();
        }

        ZipFS zipFS = new ZipFS(file);

        // remove deleted data objects from zip fs
        removeDataObjectsFromZipFS(zipFS);

        // persist gesture sets
        persistGestureSets(dataObjects.get(GestureSet.class), zipFS);

        // persist configurations
        persistConfiguration(dataObjects.get(Configuration.class), zipFS);

        // persist test sets
        persistTestSets(dataObjects.get(TestSet.class), zipFS);

        // FIXME why close zipFS?
        try {
          zipFS.close();
        } catch (IOException e) {
          e.printStackTrace();
        }

        //dataObjects = deserialize(getStorageFile());

        setDoChanged(false);

      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "Could not initialise ZIP Storage Engine.", e);
      }
    }

  }

  private void removeDataObjectsFromZipFS(ZipFS zipFS) {
    for (DataObject dataObjecttoRemove : dataObjectsToRemove) {
      String path = null;

      if (dataObjecttoRemove instanceof Configuration) {
        path = getConfigurationPath(dataObjecttoRemove.getId());
      } else if (dataObjecttoRemove instanceof GestureSet) {
        path = getGestureSetPath(dataObjecttoRemove.getId());
      } else if (dataObjecttoRemove instanceof TestSet) {
        path = getTestSetPath(dataObjecttoRemove.getId());
      }

      if (path != null) {
        zipFS.delete(path);
      }
    }
  }
}
