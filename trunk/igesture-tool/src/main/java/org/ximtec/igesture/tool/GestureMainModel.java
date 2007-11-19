/*
 * @(#)GestureMainModel.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   This class represents the data model of the iGesture
 *                  Tool application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.util.Constant;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.event.ConfigurationListener;
import org.ximtec.igesture.tool.event.CurrentGestureListener;
import org.ximtec.igesture.tool.event.GestureClassListener;
import org.ximtec.igesture.tool.event.GestureSetListener;
import org.ximtec.igesture.tool.event.TestSetListener;


/**
 * This class represents the data model of the iGesture Tool application.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureMainModel {

   /**
    * Data structure to manage the gesture classes
    */
   private List<GestureClass> gestureClasses;

   /**
    * Data structure to manage the gesture sets
    */
   private List<GestureSet> gestureSets;

   /**
    * Data structure to manage the configurations
    */
   private List<Configuration> configurations;

   /**
    * Data structure to manage the test cases
    */
   private List<TestSet> testSets;

   /**
    * Data structure to manage the algorithms
    */
   private List<String> algorithms;

   /**
    * The storage manager. This instance is used to abstract all database
    * accesses.
    */
   private StorageManager storageManager;

   /**
    * Contains the most recently captured note.
    */
   private Note currentNote;

   /**
    * The pen client
    */
   private InputDeviceClient penClient;

   /**
    * Some hashsets to handle the listeners
    */
   private HashSet<GestureSetListener> gestureSetListeners;

   private HashSet<GestureClassListener> gestureClassListeners;

   private HashSet<ConfigurationListener> configurationListeners;

   private HashSet<CurrentGestureListener> currentGestureListeners;

   private HashSet<TestSetListener> testCaseListeners;


   /**
    * Constructs a new main model.
    * 
    * @param engine the storage engine used by the storage manager.
    * @param configuration the configuration to be used.
    */
   public GestureMainModel(StorageEngine engine,
         GestureConfiguration configuration) {
      gestureSetListeners = new HashSet<GestureSetListener>();
      gestureClassListeners = new HashSet<GestureClassListener>();
      configurationListeners = new HashSet<ConfigurationListener>();
      currentGestureListeners = new HashSet<CurrentGestureListener>();
      testCaseListeners = new HashSet<TestSetListener>();
      algorithms = configuration.getAlgorithms();
      loadData(engine);
      //TODO: more general (i.e. input device)
      penClient = new InputDeviceClient(configuration.getInputDevice(),
            configuration.getInputDeviceEventListener());
      penClient.init();
   }


   /**
    * Loads the data.
    * 
    * @param engine the storage engine used by the storage manager.
    */
   public void loadData(StorageEngine engine) {
      if (storageManager != null) {
         storageManager.dispose();
      }

      storageManager = new StorageManager(engine);
      loadData();
   } // loadData


   /**
    * Loads the data from the database. All elements are available in memory and
    * write operations are forwarded to the database.
    */
   private void loadData() {
      gestureClasses = new ArrayList<GestureClass>();

      for (GestureClass dataObject : storageManager.load(GestureClass.class)) {
         gestureClasses.add(dataObject);
      }

      gestureSets = new ArrayList<GestureSet>();

      for (GestureSet dataObject : storageManager.load(GestureSet.class)) {
         gestureSets.add(dataObject);
      }

      configurations = new ArrayList<Configuration>();

      for (Configuration dataObject : storageManager.load(Configuration.class)) {
         configurations.add(dataObject);
      }

      testSets = new ArrayList<TestSet>();

      for (TestSet dataObject : storageManager.load(TestSet.class)) {
         testSets.add(dataObject);
      }

      fireConfigurationChanged(new EventObject(Constant.EMPTY_STRING));
      fireGesturedClassChanged(new EventObject(Constant.EMPTY_STRING));
      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
      fireTestSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // loadData


   /**
    * Returns the list of configurations.
    * 
    * @return the list of configurations.
    */
   public List<Configuration> getConfigurations() {
      return configurations;
   } // getConfigurations


   /**
    * Adds the gesture class to the gesture main model and propagates the changes
    * to the database. An event is fired to notify listening instances.
    * 
    * @param gestureClass the gesture class to be added.
    */
   public void addGestureClass(GestureClass gestureClass) {
      if (!gestureClasses.contains(gestureClass)) {
         gestureClasses.add(gestureClass);
      }

      storageManager.store(gestureClass);
      fireGesturedClassChanged(new EventObject(Constant.EMPTY_STRING));
   } // addGestureClass


   /**
    * Returns the list of gesture classes.
    * 
    * @return the list of gesture classes.
    */
   public List<GestureClass> getGestureClasses() {
      return gestureClasses;
   } // getGestureClasses


   /**
    * Removes the given class from the gesture main model.
    * 
    * @param gestureClass the gesture class to be removed.
    */
   public void removeGestureClass(GestureClass gestureClass) {
      gestureClasses.remove(gestureClass);
      storageManager.remove(gestureClass);
      fireGesturedClassChanged(new EventObject(Constant.EMPTY_STRING));
   } // removeGestureClass


   /**
    * Adds the gesture set to the gesture main model, propagates the changes to
    * the database and fires the corresponding event.
    * 
    * @param gestureSet the gesture set to be added.
    */
   public void addGestureSet(GestureSet gestureSet) {
      if (!gestureSets.contains(gestureSet)) {
         gestureSets.add(gestureSet);
      }

      storageManager.store(gestureSet);
      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // addGestureSet


   /**
    * Returns the list of gesture sets.
    * 
    * @return the list of gesture sets.
    */
   public List<GestureSet> getGestureSets() {
      return gestureSets;
   } // getGestureSets


   /**
    * Removes the given gesture set from the gesture main model.
    * 
    * @param set the set to be removed.
    */
   public void removeGestureSet(GestureSet set) {
      storageManager.remove(set);
      gestureSets.remove(set);
      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // removeGestureSet


   /**
    * Adds an algorithm to the gesture main model.
    * 
    * @param algorithm the algorithm to be added.
    */
   public void addConfiguration(Configuration configuration) {
      if (!configurations.contains(configuration)) {
         configurations.add(configuration);
      }

      storageManager.store(configuration);
      fireConfigurationChanged(new EventObject(Constant.EMPTY_STRING));
   } // addConfiguration


   /**
    * Removes a configuration from the gesture main model.
    * 
    * @param configuration the configuration to be removed.
    */
   public void removeConfiguration(Configuration configuration) {
      storageManager.remove(configuration);
      configurations.remove(configuration);
      fireConfigurationChanged(new EventObject(Constant.EMPTY_STRING));
   } // removeConfiguration


   /**
    * Adds a gesture class to a gesture set.
    * 
    * @param set the gesture set the gesture class has to be added to.
    * @param gestureClass the gesture class to be added.
    */
   public void addClassToSet(GestureSet set, GestureClass gestureClass) {
      set.addGestureClass(gestureClass);
      storageManager.update(set);
      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // addClassToSet


   /**
    * Deletes the gesture class from the gesture set.
    * 
    * @param set the gesture set from which the class has to be removed.
    * @param gestureClass the gesture class to be removed.
    */
   public void removeClassFromSet(GestureSet set, GestureClass gestureClass) {
      set.removeGestureClass(gestureClass);
      storageManager.update(set);
      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // removeClassFromSet


   /**
    * Sets the most recently captured note.
    * 
    * @param note the note to be set.
    */
   public void setCurrentNote(Note note) {
      currentNote = note;
      fireCurrentGestureChanged(new EventObject(Constant.EMPTY_STRING));
   } // setCurrentNote


   /**
    * Returns a clone of the most recently captured note.
    * 
    * @return the cloned note.
    */
   public Note getCurrentNote() {
      return (currentNote != null) ? (Note)currentNote.clone() : null;
   } // getCurrentNote


   /**
    * Returns the pen client.
    * 
    * @return the pen client.
    */
   public InputDeviceClient getPenClient() {
      // TODO: more general (i.e. input device)
      return penClient;
   } // getPenClient


   /**
    * Adds a test case to the main gesture model.
    * 
    * @param testSet the test set to be added.
    */
   public void addTestSet(TestSet testSet) {
      storageManager.store(testSet);
      testSets.add(testSet);
      fireTestSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // addTestSet


   /**
    * Returns the list of test sets.
    * 
    * @return list of test sets.
    */
   public List<TestSet> getTestSets() {
      return testSets;
   } // getTestSets


   /**
    * Removes a test set from the main gesture model.
    * 
    * @param testSet the test case to be removed.
    */
   public void removeTestSet(TestSet testSet) {
      storageManager.remove(testSet);
      testSets.remove(testSet);
      fireTestSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // removeTestSet


   /**
    * Returns the list of algorithms.
    * 
    * @return the list of algorithms.
    */
   public List<String> getAlgorithms() {
      return algorithms;
   } // getAlgorithms


   /**
    * Updates a data object in the data source.
    * 
    * @param dataObject the data object to be updated.
    */
   public void updateDataObject(DataObject dataObject) {
      storageManager.store(dataObject);

      if (dataObject instanceof GestureClass) {
         fireGesturedClassChanged(new EventObject(dataObject));
      }
      else if (dataObject instanceof GestureSet) {
         fireGesturedSetChanged(new EventObject(dataObject));
      }
      else if (dataObject instanceof Algorithm) {
         fireConfigurationChanged(new EventObject(dataObject));
      }
      else if (dataObject instanceof TestSet) {
         fireTestSetChanged(new EventObject(dataObject));
      }
      else if (dataObject instanceof Configuration) {
         fireConfigurationChanged(new EventObject(dataObject));
      }

   } // updateDataObject


   public StorageManager getStorageManager() {
      return storageManager;
   } // getStorageManager


   /**
    * Adds a gesture class listener.
    * 
    * @param listener the gesture class listener to be added.
    */
   public synchronized void addGestureClassListener(GestureClassListener listener) {
      if (listener != null) {
         gestureClassListeners.add(listener);
      }

   } // addGestureClassListener


   public synchronized void addGestureSetListener(GestureSetListener listener) {
      if (listener != null) {
         gestureSetListeners.add(listener);
      }

   } // addGestureSetListener


   public synchronized void addConfigurationListener(
         ConfigurationListener listener) {
      if (listener != null) {
         configurationListeners.add(listener);
      }

   } // addConfigurationListener


   public synchronized void addCurrentGestureListener(
         CurrentGestureListener listener) {
      if (listener != null) {
         currentGestureListeners.add(listener);
      }

   } // addCurrentGestureListener


   public synchronized void addTestSetListener(TestSetListener listener) {
      if (listener != null) {
         testCaseListeners.add(listener);
      }

   } // addTestSetListener


   public synchronized void fireGesturedSetChanged(EventObject event) {
      for (GestureSetListener gestureSetListener : this.gestureSetListeners) {
         gestureSetListener.gestureSetChanged(event);
      }

   } // fireGesturedSetChanged


   public synchronized void fireGesturedClassChanged(EventObject event) {
      for (GestureClassListener gestureSetListener : this.gestureClassListeners) {
         gestureSetListener.gestureClassChanged(event);
      }

   } // fireGesturedClassChanged


   public synchronized void fireConfigurationChanged(EventObject event) {
      for (ConfigurationListener configListener : this.configurationListeners) {
         configListener.configurationChanged(event);
      }
   }


   public synchronized void fireCurrentGestureChanged(EventObject event) {
      for (CurrentGestureListener currentGestureListener : this.currentGestureListeners) {
         currentGestureListener.currentGestureChanged(event);
      }

   } // fireConfigurationChanged


   public synchronized void fireTestSetChanged(EventObject event) {
      for (TestSetListener testCaseListener : this.testCaseListeners) {
         testCaseListener.testSetChanged(event);
      }

   } // fireTestSetChanged

}
