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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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
import org.ximtec.igesture.core.DefaultPropertyChangeNotifier;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.view.MainModel;


public class GestureSetList extends DefaultPropertyChangeNotifier implements DataObjectWrapper{

   public static final String PROPERTY_SETS = "sets";
   
   MainModel model;
   
   List<GestureSet> sets;


   public GestureSetList(MainModel mainModel) {
      model = mainModel;
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
      return sets;
   }

   public String getName(){
      return "GestureSets";
   }

   @Override
   public String toString() {
      return getName();
   }
      


   @Override
   public List<DataObject> getDataObjects() {
      List<DataObject> result = new ArrayList<DataObject>();
      result.addAll(getGestureSets());
      return result;
   }
}
