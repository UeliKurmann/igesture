/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 23.03.2008		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testbench.wrapper;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.DataObjectWrapper;
import org.ximtec.igesture.core.DefaultPropertyChangeNotifier;
import org.ximtec.igesture.tool.view.MainModel;


/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class AlgorithmWrapper extends DefaultPropertyChangeNotifier implements
      DataObjectWrapper {

   private static final String PROPERTY_CONFIGURATIONS = "configurations";

   Class< ? extends Algorithm> algorithmClass;

   MainModel mainModel;

   List<Configuration> configurations;


   public AlgorithmWrapper(MainModel mainModel, Class< ? extends Algorithm> algorithmClass) {
      this.algorithmClass = algorithmClass;
      this.mainModel = mainModel;
      updateReference();
   }


   public void addConfiguration(Configuration configuration) {
      mainModel.getStorageManager().store(configuration);
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_CONFIGURATIONS,
            0, null, configuration);
      updateReference();
   }


   public void removeConfiguration(Configuration configuration) {
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_CONFIGURATIONS,
            0, configuration, null);
      updateReference();
   }


   public Class< ? extends Algorithm> getAlgorithm() {
      return algorithmClass;
   }


   public String getName() {
      return algorithmClass.getSimpleName();
   }


   @Override
   public String toString() {
      return getName();
   }


   @Override
   public List<DataObject> getDataObjects() {
      List<DataObject> result = new ArrayList<DataObject>();
      result.addAll(configurations);
      return result;
   }


   public List<PropertyChangeListener> getListeners() {
      return Arrays.asList(propertyChangeSupport.getPropertyChangeListeners());
   }


   private synchronized void updateReference() {
      if (configurations == null) {
         configurations = new ArrayList<Configuration>();
      }
      configurations.clear();
      for (Configuration configuration : mainModel.getConfigurations()) {
         if (configuration.getAlgorithms().contains(algorithmClass.getName())) {
            configurations.add(configuration);
         }
      }

      // FIXME find another solution to notify the main controller about the
      // update.
      propertyChangeSupport.firePropertyChange("nil", "not", "yes");
   }

}
