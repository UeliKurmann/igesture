/*
 * @(#)GestureMainModel.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Static String Variables for the GUI Application
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
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
 * This class represents the datamodel of the IGestureTool Application.
 * 
 * @author Ueli Kurmann
 * 
 */

public class GestureMainModel {

   /**
    * Datastructure to manage the Gesture Classes
    */
   private List<GestureClass> gestureClasses;

   /**
    * Datastructure to manage the Gesture Sets
    */
   private List<GestureSet> gestureSets;

   /**
    * Datastructure to manage the Configurations
    */
   private List<Configuration> configurations;

   /**
    * Datastructure to manage the test cases
    */
   private List<TestSet> testSets;

   /**
    * Datastructure to manage the Algorithms
    */
   private List<String> algorithms;

   /**
    * The storage manager. this instance is used to abstract all database
    * accesses.
    */
   private StorageManager storageManager;

   /**
    * Contains the last captured note.
    */
   private Note currentNote;

   /**
    * The PenClient
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
    * Constructs a new MainModel.
    * 
    * @param engine the storage engine used by the storage manager
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

      penClient = new InputDeviceClient(configuration.getInputDevice(), configuration.getInputDeviceEventListener());
      penClient.init();
   }

   /**
    * loads the data
    * 
    * @param engine the storage engine used by the storage manager
    */
   public void loadData(StorageEngine engine) {
      if (storageManager != null) {
         storageManager.dispose();
      }
      storageManager = new StorageManager(engine);
      loadData();
   }


   /**
    * loads the data out of the database. all elements are available in memory
    * and write operations are forwarded to the database.
    * 
    */
   private void loadData() {
      gestureClasses = new ArrayList<GestureClass>();
      for (final GestureClass dataObject : storageManager
            .load(GestureClass.class)) {
         gestureClasses.add(dataObject);
      }

      gestureSets = new ArrayList<GestureSet>();
      for (final GestureSet dataObject : storageManager.load(GestureSet.class)) {
         gestureSets.add(dataObject);
      }

      configurations = new ArrayList<Configuration>();
      for (final Configuration dataObject : storageManager
            .load(Configuration.class)) {
         configurations.add(dataObject);
      }

      testSets = new ArrayList<TestSet>();
      for (final TestSet dataObject : storageManager.load(TestSet.class)) {
         testSets.add(dataObject);
      }

      fireConfigurationChanged(new EventObject(Constant.EMPTY_STRING));
      fireGesturedClassChanged(new EventObject(Constant.EMPTY_STRING));
      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
      fireTestSetChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Returns the list of configurations
    * 
    * @return List<Algoritm>
    */
   public List<Configuration> getConfigurations() {
      return configurations;
   }


   /**
    * Returns the list of Gesture Classes
    * 
    * @return
    */
   public List<GestureClass> getGestureClasses() {
      return gestureClasses;
   }


   /**
    * Returns the list of Gesture Sets
    * 
    * @return
    */
   public List<GestureSet> getGestureSets() {
      return gestureSets;
   }


   /**
    * Adds the gesture set to the collection and propagates the changes to the
    * database and fire the corresponding event.
    * 
    * @param gestureSet the gesture set to add
    */
   public void addGestureSet(GestureSet gestureSet) {
      if (!gestureSets.contains(gestureSet)) {
         gestureSets.add(gestureSet);
      }

      storageManager.store(gestureSet);
      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Adds the gesture class to the collection and propagates the changes to the
    * database. an event is fired to notify listening instances.
    * 
    * @param gestureClass
    */
   public void addGestureClass(GestureClass gestureClass) {
      if (!gestureClasses.contains(gestureClass)) {
         gestureClasses.add(gestureClass);
      }
      storageManager.store(gestureClass);
      fireGesturedClassChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Removes the given class from the collection
    * 
    * @param gestureClass the gesture class to remove
    */
   public void removeGestureClass(GestureClass gestureClass) {
      gestureClasses.remove(gestureClass);
      storageManager.remove(gestureClass);
      fireGesturedClassChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Adds an algorithm to the collection
    * 
    * @param algorithm the algorithm to add
    */
   public void addConfiguration(Configuration configuration) {
      if (!configurations.contains(configuration)) {
         configurations.add(configuration);
      }

      storageManager.store(configuration);
      fireConfigurationChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Remove a Configuration from the collection
    * 
    * @param testCase
    */
   public void removeConfiguration(Configuration configuration) {
      storageManager.remove(configuration);
      configurations.remove(configuration);
      fireConfigurationChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Adds a gesture class to a gesture set
    * 
    * @param set
    * @param gestureClass
    */
   public void addClassToSet(GestureSet set, GestureClass gestureClass) {
      set.addGestureClass(gestureClass);
      storageManager.update(set);
      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Deletes the gesture class from the gesutre set
    * 
    * @param set the gesture set from whitch the class should be removed
    * @param gestureClass the gesture calss to remove
    */
   public void removeClassFromSet(GestureSet set, GestureClass gestureClass) {
      set.delGestureClass(gestureClass);
      storageManager.update(set);
      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Removes the given gesture set from the collection
    * 
    * @param set
    */
   public void removeGestureSet(GestureSet set) {
      storageManager.remove(set);
      gestureSets.remove(set);
      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Sets the current trace
    * 
    * @param currentTrace
    */
   public void setCurrentNote(Note note) {
      this.currentNote = note;
      fireCurrentGestureChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Returns a clone of the last captured note.
    * 
    * @return the cloned Note
    */
   public Note getCurrentNote() {
      if (currentNote != null) {
         final Note note = (Note) currentNote.clone();
         return note;
      }
      else {
         return null;
      }
   }


   /**
    * Returns the Magicomm Pen Client
    * 
    * @return
    */
   public InputDeviceClient getPenClient() {
      return penClient;
   }


   /**
    * Add a TestCase to the collection
    * 
    * @param testSet
    */
   public void addTestSet(TestSet testSet) {
      storageManager.store(testSet);
      testSets.add(testSet);
      fireTestSetChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Remove a TestCase from the collection
    * 
    * @param testCase
    */
   public void removeTestSet(TestSet testSet) {
      storageManager.remove(testSet);
      testSets.remove(testSet);
      fireTestSetChanged(new EventObject(Constant.EMPTY_STRING));
   }


   /**
    * Returns the list of TestCases
    * 
    * @return list of test cases
    */
   public List<TestSet> getTestSets() {
      return testSets;
   }


   /**
    * Returns the list of Algorithms
    * 
    * @return
    */
   public List<String> getAlgorithms() {
      return algorithms;
   }


   /**
    * This methods provides update access to the datasource
    * 
    * @param dataObject
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
   }


   public StorageManager getStorageManager() {
      return storageManager;
   }


   /** ******************** - Event Handling - *********************** */

   public synchronized void addGestureClassListener(GestureClassListener listener) {
      if (listener != null) {
         this.gestureClassListeners.add(listener);
      }
   }


   public synchronized void addGestureSetListener(GestureSetListener listener) {
      if (listener != null) {
         this.gestureSetListeners.add(listener);
      }
   }


   public synchronized void addConfigurationListener(
         ConfigurationListener listener) {
      if (listener != null) {
         this.configurationListeners.add(listener);
      }
   }


   public synchronized void addCurrentGestureListener(
         CurrentGestureListener listener) {
      if (listener != null) {
         this.currentGestureListeners.add(listener);
      }
   }


   public synchronized void addTestSetListener(TestSetListener listener) {
      if (listener != null) {
         this.testCaseListeners.add(listener);
      }
   }


   public synchronized void fireGesturedSetChanged(EventObject event) {
      for (final GestureSetListener gestureSetListener : this.gestureSetListeners) {
         gestureSetListener.gestureSetChanged(event);
      }
   }


   public synchronized void fireGesturedClassChanged(EventObject event) {
      for (final GestureClassListener gestureSetListener : this.gestureClassListeners) {
         gestureSetListener.gestureClassChanged(event);
      }
   }


   public synchronized void fireConfigurationChanged(EventObject event) {
      for (final ConfigurationListener configListener : this.configurationListeners) {
         configListener.configurationChanged(event);
      }
   }


   public synchronized void fireCurrentGestureChanged(EventObject event) {
      for (final CurrentGestureListener currentGestureListener : this.currentGestureListeners) {
         currentGestureListener.currentGestureChanged(event);
      }
   }


   public synchronized void fireTestSetChanged(EventObject event) {
      for (final TestSetListener testCaseListener : this.testCaseListeners) {
         testCaseListener.testSetChanged(event);
      }
   }

}
