/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.admin.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.DataObjectWrapper;
import org.ximtec.igesture.core.DefaultPropertyChangeOwner;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.view.MainModel;


public class GestureSetList extends DefaultPropertyChangeOwner implements DataObjectWrapper{

   public static final String PROPERTY_SETS = "sets";
   
   MainModel model;
   
   List<GestureSet> sets;


   public GestureSetList() {
      model = Locator.getDefault().getService(MainModel.IDENTIFIER, MainModel.class);
      sets = model.getGestureSets();
   }


   public void addGestureSet(GestureSet gestureSet) {
      model.getStorageManager().store(gestureSet);
      sets = model.getGestureSets();
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_SETS, 0, null, gestureSet);
   }


   public void removeGestureSet(GestureSet gestureSet) {
      model.getStorageManager().remove(gestureSet);
      sets = model.getGestureSets();
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_SETS, 0, gestureSet, null);
   }


   public List<GestureSet> getGestureSets() {
      for(GestureSet set:model.getGestureSets()){
         System.out.println(set.getName());
      }
      System.out.println(sets.size());
      
      return sets;
   }


   @Override
   public String toString() {
      //FIXME use resource bundle
      return "GestureSets";
   }


   @Override
   public List<DataObject> getDataObjects() {
      List<DataObject> result = new ArrayList<DataObject>();
      result.addAll(getGestureSets());
      return result;
   }
}
