/*
 * @(#)GestureMappingModel.java	1.0   Nov 20, 2007
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.EventManager;
import org.ximtec.igesture.geco.GestureMappingTable;
import org.ximtec.igesture.geco.event.GestureSetLoadListener;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.GestureConfiguration;



/**
 * Comment
 * @version 1.0 Nov 20, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class GecoMainModel {
   
   /**
    * The list of imported Gesture Set
    */
   private GestureSet gestureSet;
   
   //private SortedListModel gestureListModel;
   
   //private SampleListModel gestureListModel;
   
   private SortedListModel<GestureClass> gestureListModel;
   
   private SortedListModel<GestureToActionMapping> mappingListModel;
   
   private EventManager eventManager =  new EventManager();
   
   private HashSet<GestureSetLoadListener> gestureSetListeners;
   
   public GestureMappingTable mappingTable = new GestureMappingTable();
   
   public Hashtable gestureClassesTable = new Hashtable();
   
   private File projectFile;

   private String projectName;
   
   private Configuration configuration;
   
   private boolean toBeSaved;
   
   /**
    * The storage manager. 
    */
   private StorageManager storageManager;
   
   /**
    * Data structure to manage the gesture classes
    */
   private List<GestureClass> gestureClasses;
   
   
   /**
    * Constructs a new main model.
    * 
    * @param engine the storage engine used by the storage manager.
    * @param configuration the configuration to be used.
    */
   public GecoMainModel(StorageEngine engine) {
      gestureSetListeners = new HashSet<GestureSetLoadListener>();
//      loadData(engine);
      Comparator c1 =new Comparator<GestureClass>() {
         public int compare(GestureClass a, GestureClass b) {
            return a.getName().compareTo(b.getName());
          }
        };
        /*
        Comparator c1 =new Comparator<Descriptor>() {
           public int compare(Descriptor a, Descriptor b) {
              return a.toString().compareTo(b.toString());
            }
          };
        */
        Comparator c2 =new Comparator<GestureToActionMapping>() {
           public int compare(GestureToActionMapping a, GestureToActionMapping b) {
              return a.getGestureClass().getName().compareTo(b.getGestureClass().getName());
            }
          };
          //gestureListModel =  new SampleListModel();
        gestureListModel =  new SortedListModel<GestureClass>(c1);
        mappingListModel =  new SortedListModel<GestureToActionMapping>(c2);
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
   //   loadData();
   } // loadData


   /**
    * Loads the data from the database. All elements are available in memory and
    * write operations are forwarded to the database.
    */
   private void loadData() {
      gestureClasses = new ArrayList<GestureClass>();

      for (GestureClass dataObject : storageManager.load(GestureClass.class)) {
         gestureClasses.add(dataObject);
      //   List<Descriptor> list = dataObject.getDescriptors();
      //   System.out.println("hoi");
      }



   //   for (GestureSet dataObject : storageManager.load(GestureSet.class)) {
   //      gestureSets.add(dataObject);
   //   }
      
      //fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // loadData

   
   /**
    * Adds the gesture set to the gesture main model
    * 
    * @param gestureSet the gesture set to be added.
    */
   public void loadGestureSet(GestureSet gestureSet) {
         this.gestureSet=gestureSet;
         gestureClassesTable.clear();
      
         
         for(GestureClass gc: gestureSet.getGestureClasses()){
            gestureListModel.add(gc);
            gestureClassesTable.put(gc.getName(), gc);
         }
         toBeSaved=true;
   } // addGestureSet
   
   public void clearData(){
      gestureClassesTable.clear();
      mappingListModel.clear();
      gestureListModel.clear();
      
   }
   
   public void setProjectFile(File f){
      projectFile = f;
   }
   
   public void setProjectName(String n){
      projectName = n;
      toBeSaved=true;
   }
   
   public File getProjectFile(){
      return projectFile;
   }
   
   public String getProjectName(){
      return projectName ;
   }
   


   /**
    * Returns the list of gesture sets.
    * 
    * @return the list of gesture sets.
    */
   public GestureSet getGestureSet() {
      return gestureSet;
   } // getGestureSets
   
   
   public void addMapping(GestureToActionMapping gm){
      gestureListModel.removeElement(gm.getGestureClass());
      eventManager.registerEventHandler(gm.getGestureClass().getName(),gm.getAction());
      mappingListModel.add(gm);
      toBeSaved=true;
   }
   
   

   public void removeMapping(GestureToActionMapping gm){
      mappingTable.remove(gm.getGestureClass());
      eventManager.unRegisterEventHandler(gm.getGestureClass().getName());
      mappingListModel.removeElement(gm);
      gestureListModel.add(gm.getGestureClass());
      toBeSaved=true;
   }
   

   /*
   public void removeGestureClass(GestureClass gc){
      gestureListModel.removeElement(gc);
   }
   
   public void addGestureClass(GestureClass gc){
      gestureListModel.add(gc);
   }
   */
   
   public SortedListModel getGestureListModel(){
      return gestureListModel;
   }
   
   public SortedListModel getMappingListModel(){
      return mappingListModel;
   }
   
   
   public EventManager getEventManager(){
      return eventManager;
   }
   
   public Configuration getConfiguration(){
      return configuration;
   }
   
   public void setConfiguration(Configuration c){
      configuration=c;
   }
   
   public void setNeedSave(boolean b){
      toBeSaved = b;
   }
   
   public boolean needSave(){
      return toBeSaved;
   }
   
   
   

   
/*
   public synchronized void fireGesturedSetChanged(EventObject event) {
      for (GestureSetLoadListener gestureSetListener : this.gestureSetListeners) {
         gestureSetListener.gestureSetChanged(event);
      }

   } // fireGesturedSetChanged
*/


}
