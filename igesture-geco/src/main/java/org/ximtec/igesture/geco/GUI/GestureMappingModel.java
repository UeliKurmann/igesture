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
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;

import javax.swing.DefaultListModel;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.geco.GestureMappingTable;
import org.ximtec.igesture.geco.event.GestureSetLoadListener;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;



/**
 * Comment
 * @version 1.0 Nov 20, 2007
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureMappingModel {
   
   /**
    * The list of imported Gesture Set
    */
   private List<GestureSet> gestureSets;
   
   private SortedListModel gestureListModel = new SortedListModel();
   
   private SortedListModel mappingListModel = new SortedListModel();
   
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

   }

   /**
    * Loads the data.
    * 
    * @param engine the storage engine used by the storage manager.
    */
   /*
   public void loadData(StorageEngine engine) {
      if (storageManager != null) {
         storageManager.dispose();
      }

      storageManager = new StorageManager(engine);
      loadData();
   } // loadData
*/

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

      //fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // loadData

   
   /**
    * Adds the gesture set to the gesture main model, propagates the changes to
    * the database and fires the corresponding event.
    * 
    * @param gestureSet the gesture set to be added.
    */
   public void loadGestureSet(GestureSet gestureSet) {
      if (!gestureSets.contains(gestureSet)) {
         gestureSets.add(gestureSet);
      }
         
      addGestureSetToListModel(gestureSet);

//      storageManager.store(gestureSet);
//      fireGesturedSetChanged(new EventObject(Constant.EMPTY_STRING));
   } // addGestureSet


   /**
    * Returns the list of gesture sets.
    * 
    * @return the list of gesture sets.
    */
   public List<GestureSet> getGestureSets() {
      return gestureSets;
   } // getGestureSets
   

   
   public void removeGestureClass(GestureClass gc){
      gestureListModel.removeElement(gc);
   }
   
   public void addGestureClass(GestureClass gc){
      gestureListModel.add(gc);
   }
   
   public void addGestureSetToListModel(GestureSet gestureSet){
      for(GestureClass gc: gestureSet.getGestureClasses()){
         gestureListModel.add(gc);
      }
   }
   
   
   public void removeGestureSetToListModel(GestureSet gestureSet){
      for(GestureClass gc: gestureSet.getGestureClasses()){
         gestureListModel.removeElement(gc);
      }
   }
   
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
