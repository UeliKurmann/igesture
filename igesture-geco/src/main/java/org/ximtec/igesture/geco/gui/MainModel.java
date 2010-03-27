/*
 * @(#)$Id$
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Model for MainView class 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 20, 2007		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.gui;

import java.io.File;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.GestureActionManager;
import org.ximtec.igesture.geco.Configuration;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.geco.util.SortedListModel;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.GestureEventListener;
import org.ximtec.igesture.io.mouseclient.MouseReader;
import org.ximtec.igesture.io.mouseclient.SwingMouseReader;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;


/**
 * Model for MainView class
 * @version 0.9, Nov 20, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class MainModel implements GestureEventListener {

   private GestureSet gestureSet;

   private SortedListModel<GestureClass> gestureListModel;

   private SortedListModel<GestureToActionMapping> mappingListModel;

   private GestureActionManager eventManager = new GestureActionManager();

   private Hashtable<GestureClass, GestureToActionMapping> mappings = new Hashtable<GestureClass, GestureToActionMapping>();

   public Hashtable<String, GestureClass> gestureClassesTable = new Hashtable<String, GestureClass>();

   private File projectFile;

   private String gestureSetFileName;

   private String projectName;

   private org.ximtec.igesture.configuration.Configuration configuration;

   private Configuration gestureConfiguration;

   private boolean toBeSaved;

   private StorageManager storageManager;

   public static final String GESTURE_SET = "gestureSet/msApplicationGestures.xml";

   private Recogniser recogniser;

   private SwingMouseReader client;

   private boolean minimize;


   /**
    * Constructs a new main model.
    * 
    * @param engine the storage engine used by the storage manager.
    */
   public MainModel(org.ximtec.igesture.configuration.Configuration conf,
         Configuration gestConf) {
      this.configuration = conf;
      this.gestureConfiguration = gestConf;
      configureInputDevice();
      minimize = gestureConfiguration.getMinimize();

      Comparator<GestureClass> c1 = new Comparator<GestureClass>() {

         public int compare(GestureClass a, GestureClass b) {
            return a.getName().compareTo(b.getName());
         }
      };
      Comparator<GestureToActionMapping> c2 = new Comparator<GestureToActionMapping>() {

         public int compare(GestureToActionMapping a, GestureToActionMapping b) {
            return a.getGestureClass().getName().compareTo(
                  b.getGestureClass().getName());
         }
      };
      gestureListModel = new SortedListModel<GestureClass>(c1);
      mappingListModel = new SortedListModel<GestureToActionMapping>(c2);
   }// GecoMainModel


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
      // loadData();
   } // loadData


   /**
    * Loads the data from the database. All elements are available in memory and
    * write operations are forwarded to the database.
    */
   /*
    * private void loadData() { gestureClasses = new ArrayList<GestureClass>();
    * 
    * for (GestureClass dataObject : storageManager.load(GestureClass.class)) {
    * gestureClasses.add(dataObject); }
    * 
    * for (GestureSet dataObject : storageManager.load(GestureSet.class)) {
    * gestureSets.add(dataObject); } } // loadData
    */

   /**
    * Adds the gesture set to the gesture main model
    * 
    * @param gestureSet the gesture set to be added.
    */
   public void loadGestureSet(GestureSet gestureSet) {
      if (!configuration.getGestureSets().contains(gestureSet))
         configuration.addGestureSet(gestureSet);

      this.gestureSet = gestureSet;
      gestureClassesTable.clear();

      for (GestureClass gc : gestureSet.getGestureClasses()) {
         gestureListModel.add(gc);
         gestureClassesTable.put(gc.getName(), gc);

      }

      toBeSaved = true;
   } // loadGestureSet


   /**
    * Clear the list models and the mapping tables
    */
   public void clearData() {
      gestureClassesTable.clear();
      mappingListModel.clear();
      gestureListModel.clear();
      mappings.clear();
   }// clearData


   /**
    * Set the project file
    * 
    * @param f the xml file in which the project is saved
    */
   public void setProjectFile(File f) {
      projectFile = f;
   }// setProjectFile


   /**
    * Set the project name
    * 
    * @param name the name of the project
    */
   public void setProjectName(String name) {
      projectName = name;
      toBeSaved = true;
   }// setProjectName


   /**
    * Returns the project file
    * 
    * @return the xml file in which the project is saved
    */
   public File getProjectFile() {
      return projectFile;
   }// getProjectFile


   /**
    * Set the project name
    * 
    * @param name the name of the project
    */
   public void setGestureSetFileName(String name) {
      gestureSetFileName = name;
      toBeSaved = true;
   }// setProjectName


   /**
    * Returns the project file
    * 
    * @return the xml file in which the project is saved
    */
   public String getGestureSetFileName() {
      return gestureSetFileName;
   }// getProjectFile


   /**
    * Adds the gesture set to the gesture main model
    * 
    * @param gestureSet the gesture set to be added.
    */
   public String getProjectName() {
      return projectName;
   }// getProjectName


   /**
    * Returns the gesture set
    * 
    * @return the current GestureSet
    */
   public GestureSet getGestureSet() {
      return gestureSet;
   } // getGestureSets


   public Hashtable<GestureClass, GestureToActionMapping> getMappings() {
      return mappings;
   } // getMappings


   /**
    * Adds a gesture-action mapping
    * 
    * @param gm the mapping to be added
    */
   public void addMapping(GestureToActionMapping gm) {
      gestureListModel.removeElement(gm.getGestureClass());
      eventManager.unRegisterEventHandler(gm.getGestureClass().getName());
      eventManager.registerEventHandler(gm.getGestureClass().getName(), gm
            .getAction());
      mappingListModel.removeElement(gm);
      mappingListModel.add(gm);
      mappings.put(gm.getGestureClass(), gm);
      toBeSaved = true;
   }// addMapping


   /**
    * Removes a gesture-action mapping
    * 
    * @param the mapping to be removed
    */
   public void removeMapping(GestureToActionMapping gm) {
      mappings.remove(gm.getGestureClass());
      eventManager.unRegisterEventHandler(gm.getGestureClass().getName());
      mappingListModel.removeElement(gm);
      gestureListModel.add(gm.getGestureClass());
      toBeSaved = true;
   }// removeMapping


   /**
    * Returns the model for the gesture List
    * 
    * @return the model
    */
   public SortedListModel<GestureClass> getGestureListModel() {
      return gestureListModel;
   }// getGestureListModel


   /**
    * Returns the model for the mapping List
    * 
    * @return the model
    */
   public SortedListModel<GestureToActionMapping> getMappingListModel() {
      return mappingListModel;
   }// getMappingListModel


   /**
    * Returns the event manager
    * 
    * @return the event manager
    */
   public GestureActionManager getEventManager() {
      return eventManager;
   }// getEventManager


   /**
    * Returns the Configuration
    * 
    * @return the configuration
    */
   public org.ximtec.igesture.configuration.Configuration getConfiguration() {
      return configuration;
   }// getConfiguration


   /**
    * Set the configuration
    * 
    * @param c the Configuration to be set
    */
   public void setConfiguration(org.ximtec.igesture.configuration.Configuration c) {
      configuration = c;
   }// setConfiguration


   /**
    * Set toBeSaved flag
    * 
    * @param the new value of the flag
    */
   public void setNeedSave(boolean b) {
      toBeSaved = b;
   }// setNeedSave


   /**
    * Should project be saved?
    * 
    * @return
    */
   public boolean needSave() {
      return toBeSaved;
   }// needSave


   public void initRecogniser(GestureSet gestureSet) {
      if (recogniser == null) {

         try {

            configuration.addGestureSet(gestureSet);
            recogniser = new Recogniser(configuration);
            recogniser.addGestureHandler(eventManager);
            client.addGestureHandler(this);
         }
         catch (Exception e) {
            e.printStackTrace();
         }

      }

   } // initRecogniser


   /**
    * Configure Input Client
    */
   public void resetInputDevice() {
      if (client != null) {
         client.clear();
      }
   }


   public void configureInputDevice() {
      
      client = new SwingMouseReader();
      client.init();
      // client = new InputDeviceClient(gestureConfiguration.getInputDevice(),
      // gestureConfiguration.getInputDeviceEventListener());
      // client.addButtonDeviceEventListener(this);
      if (recogniser != null) {
         client.addGestureHandler(this);
         
      }
   }


   /**
    * Get GestureConfiguration
    */
   public Configuration getGestureConfiguration() {
      return gestureConfiguration;
   }


   /**
    * Set GestureConfiguration
    */
   public void setGestureConfiguration(Configuration conf) {
      gestureConfiguration = conf;
   }


   /**
    * Application should be minimized
    */
   public boolean minimizeAsStartup() {
      return minimize;
   }


   @Override
   public void handleChunks(List< ? > chunks) {
      // TODO Auto-generated method stub

   }


   @Override
   public void handleGesture(Gesture< ? > gesture) {

      if (client.getGesture() != null) {
         Note note = client.getGesture().getGesture();
         if (note.getPoints().size() > 5) {
            recogniser.recognise(note);
         }
      }

   }
}
