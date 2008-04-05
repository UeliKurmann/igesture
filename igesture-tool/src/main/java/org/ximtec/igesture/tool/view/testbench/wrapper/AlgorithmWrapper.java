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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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
import org.ximtec.igesture.core.DefaultPropertyChangeOwner;



/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class AlgorithmWrapper extends DefaultPropertyChangeOwner implements DataObjectWrapper{
   
   Class<? extends Algorithm> algorithmClass;
   
   List<Configuration> configurations;
   
   public AlgorithmWrapper(Class<? extends Algorithm> algorithmClass){
      this.algorithmClass = algorithmClass;
      this.configurations = new ArrayList<Configuration>();
   }
   
   public void addConfiguration(Configuration configuration){
      configurations.add(configuration);
      propertyChangeSupport.fireIndexedPropertyChange("configurations", 0, null, configuration);
   }
   
   public void removeConfiguration(Configuration configuration){
      configurations.remove(configuration);
      propertyChangeSupport.fireIndexedPropertyChange("configurations", 0, configuration, null);
   }
   
   public Class<? extends Algorithm> getAlgorithm(){
      return algorithmClass;
   }
   
   @Override
   public String toString() {
      return algorithmClass.getSimpleName();
   }

   @Override
   public List<DataObject> getDataObjects() {
     List<DataObject> result = new ArrayList<DataObject>();
     result.addAll(configurations);
     return result;
   }
   
   public List<PropertyChangeListener> getListeners(){
      return Arrays.asList(propertyChangeSupport.getPropertyChangeListeners());
   }

}
