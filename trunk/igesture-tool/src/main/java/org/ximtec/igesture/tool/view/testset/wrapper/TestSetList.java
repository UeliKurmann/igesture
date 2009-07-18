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

package org.ximtec.igesture.tool.view.testset.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.DataObjectWrapper;
import org.ximtec.igesture.core.DefaultPropertyChangeNotifier;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.view.MainModel;


public class TestSetList extends DefaultPropertyChangeNotifier implements DataObjectWrapper{

   public static final String PROPERTY_SETS = "sets";
   
   private MainModel model;
   
   private List<TestSet> sets;


   public TestSetList(MainModel mainModel) {
      model = mainModel;
      sets = model.getTestSets();
   }


   public void addTestSet(TestSet testSet) {
      model.getStorageManager().store(testSet);
      sets = model.getTestSets();
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_SETS, 0, null, testSet);
   }


   public void removeTestSet(TestSet testSet) {
      model.getStorageManager().remove(testSet);
      sets = model.getTestSets();
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_SETS, 0, testSet, null);
   }


   public List<TestSet> getTestSet() {
      return sets;
   }

   public String getName(){
      return "TestSets";
   }

   @Override
   public String toString() {
      return getName();
   }
      


   @Override
   public List<DataObject> getDataObjects() {
      List<DataObject> result = new ArrayList<DataObject>();
      result.addAll(getTestSet());
      return result;
   }
}
