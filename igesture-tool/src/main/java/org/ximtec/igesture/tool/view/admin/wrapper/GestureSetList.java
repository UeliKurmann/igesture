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


public class GestureSetList extends DefaultPropertyChangeOwner implements DataObjectWrapper{

   public static final String PROPERTY_SETS = "sets";

   public List<GestureSet> sets;


   public GestureSetList() {
      sets = new ArrayList<GestureSet>();
   }


   public void addGestureSet(GestureSet set) {
      sets.add(set);
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_SETS, sets
            .indexOf(set), null, set);
   }


   public void removeGestureSet(GestureSet set) {
      sets.remove(set);
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_SETS, 0, set,
            null);
   }


   public List<GestureSet> getGestureSets() {
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
