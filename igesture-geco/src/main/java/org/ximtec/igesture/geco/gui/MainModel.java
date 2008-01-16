/*
 * @(#)MainModel.java	1.0   Nov 20, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Model for GestureMappingView class 
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.input.InputDeviceEvent;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.EventManager;
import org.ximtec.igesture.geco.Configuration;
import org.ximtec.igesture.geco.gui.action.OpenProjectAction;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.geco.util.SortedListModel;
import org.ximtec.igesture.io.ButtonDeviceEventListener;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;


/**
 * Comment
 * @version 0.9, Nov 20, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class MainModel implements ButtonDeviceEventListener {

   private GestureSet gestureSet;

   private SortedListModel<GestureClass> gestureListModel;

   private SortedListModel<GestureToActionMapping> mappingListModel;

   private EventManager eventManager = new EventManager();

   private Hashtable<GestureClass, GestureToActionMapping> mappings = new Hashtable<GestureClass, GestureToActionMapping>();

   public Hashtable<String, GestureClass> gestureClassesTable = new Hashtable<String, GestureClass>();

   private File projectFile;

   private String gestureSetFileName;

   private String projectName;

   private org.ximtec.igesture.configuration.Configuration configuration;

   private Configuration gestureConfiguration;

   private boolean toBeSaved;

   private StorageManager storageManager;

   private List<GestureClass> gestureClasses;

   public static final String GESTURE_SET = "ms_application_gestures.xml";

   private Recogniser recogniser;

   private InputDeviceClient client;


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
   private void loadData() {
      gestureClasses = new ArrayList<GestureClass>();

      for (GestureClass dataObject : storageManager.load(GestureClass.class)) {
         gestureClasses.add(dataObject);
      }

       for (GestureSet dataObject : storageManager.load(GestureSet.class)) {
       gestureSets.add(dataObject);
       }
   } // loadData
*/

   /**
    * Adds the gesture set to the gesture main model
    * 
    * @param gestureSet the gesture set to be added.
    */
   public void loadGestureSet(GestureSet gestureSet) {
      // if (configuration.getGestureSets().contains(gestureSet))
      // configuration.removeGestureSet(this.gestureSet);

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
   public EventManager getEventManager() {
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
            this.configuration.setEventManager(eventManager);
            configuration.addGestureSet(gestureSet);
            recogniser = new Recogniser(configuration);
            client.addButtonDeviceEventListener(this);
         }
         catch (Exception e) {
            e.printStackTrace();
         }
      }
   }


   /**
    * Handle events coming from the input device
    * 
    * @param event the event
    */
   public void handleButtonPressedEvent(InputDeviceEvent event) {
      Note note = client.createNote(0, event.getTimestamp(), 70);
      if (note.getPoints().size() > 5) {
         recogniser.recognise(note);
      }
   } // handleButtonPressedEvent


   /**
    * Configure Input Client
    */
   public void resetInputDevice() {
      if(client!=null){
         client.reset();
      }
   }
   
   public void configureInputDevice(){
         client = new InputDeviceClient(gestureConfiguration.getInputDevice(),
               gestureConfiguration.getInputDeviceEventListener());
       //  client.addButtonDeviceEventListener(this);
     if(recogniser!=null){
        client.addButtonDeviceEventListener(this);
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
   


}
