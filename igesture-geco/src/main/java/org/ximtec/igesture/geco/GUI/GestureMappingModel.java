/*
 * @(#)GestureMappingModel.java	1.0   Nov 20, 2007
 *
 * Author		:	Beat Signer, signer@inf.ethz.ch
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					bsigner		Initial Release
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;

import javax.swing.DefaultListModel;

import org.sigtec.graphix.SimpleListModel;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.DigitalDescriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.event.EventManager;
import org.ximtec.igesture.geco.GestureMappingTable;
import org.ximtec.igesture.geco.event.GestureSetLoadListener;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.frame.classlist.SampleListModel;



/**
 * Comment
 * @version 1.0 Nov 20, 2007
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureMappingModel {
   
   /**
    * The list of imported Gesture Set
    */
   private List<GestureSet> gestureSets =  new ArrayList<GestureSet>();
   
   //private SortedListModel gestureListModel;
   
   //private SampleListModel gestureListModel;
   
   private SortedListModel gestureListModel;
   
   private SortedListModel mappingListModel;
   
   private EventManager eventManager =  new EventManager();
   
   private HashSet<GestureSetLoadListener> gestureSetListeners;
   
   public GestureMappingTable mappingTable = new GestureMappingTable();
   
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
   public GestureMappingModel(StorageEngine engine) {
      gestureSetListeners = new HashSet<GestureSetLoadListener>();
      loadData(engine);
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
        gestureListModel =  new SortedListModel<Descriptor>(c1);
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

      gestureSets = new ArrayList<GestureSet>();

      for (GestureSet dataObject : storageManager.load(GestureSet.class)) {
         gestureSets.add(dataObject);
      }
      

      //fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // loadData

   
   /**
    * Adds the gesture set to the gesture main model
    * 
    * @param gestureSet the gesture set to be added.
    */
   public void addGestureSet(GestureSet gestureSet) {
      if (!gestureSets.contains(gestureSet)) {
         gestureSets.add(gestureSet);
      }
      
      for(GestureClass gc: gestureSet.getGestureClasses()){

         gestureListModel.add(gc);
         
      }
      
      /*
      List list =  new ArrayList();
         
      for(GestureClass gc: gestureSet.getGestureClasses()){
         Descriptor d = gc.getDescriptors().get(0);
         //DigitalDescriptor d = gc.getDescriptor(DigitalDescriptor.class);
         
         SampleDescriptor sample=null;
         if (d instanceof SampleDescriptor){
            System.out.println("sample");
            sample = (SampleDescriptor)d;
            list.add(sample.getSamples().get(0));
         }
         //gestureListModel.add(gc.getDescriptors().get(0));
         
      }
      System.out.println("length: "+list.size());
       gestureListModel =  new SampleListModel(list);
       */
       
       
//      storageManager.store(gestureSet);
//      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // addGestureSet
   
   /**
    * Adds the gesture set to the gesture main model, propagates the changes to
    * the database and fires the corresponding event.
    * 
    * @param gestureSet the gesture set to be added.
    */
   public void removeGestureSet(GestureSet gestureSet) {
      if (gestureSets.contains(gestureSet)) {
         gestureSets.remove(gestureSet);
      }
         
      for(GestureClass gc: gestureSet.getGestureClasses()){
         //gestureListModel.removeElement(gc);
      }
//      storageManager.store(gestureSet);
//      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   } //remove addGestureSet



   /**
    * Returns the list of gesture sets.
    * 
    * @return the list of gesture sets.
    */
   public List<GestureSet> getGestureSets() {
      return gestureSets;
   } // getGestureSets
   
   
   public void addMapping(GestureToActionMapping gm){
      gestureListModel.removeElement(gm.getGestureClass());
      mappingListModel.add(gm);
      eventManager.registerEventHandler(gm.getGestureClass().getName(), gm.getAction());
      
   }
   
   

   public void removeMapping(GestureToActionMapping gm){
      mappingTable.remove(gm.getGestureClass());
      mappingListModel.removeElement(gm);
      gestureListModel.add(gm.getGestureClass());
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
   
   

   
/*
   public synchronized void fireGesturedSetChanged(EventObject event) {
      for (GestureSetLoadListener gestureSetListener : this.gestureSetListeners) {
         gestureSetListener.gestureSetChanged(event);
      }

   } // fireGesturedSetChanged
*/


}
