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


package org.ximtec.igesture.tool.view;

import java.util.ArrayList;
import java.util.List;

import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.rubine.RubineAlgorithm;
import org.ximtec.igesture.algorithm.siger.SigerRecogniser;
import org.ximtec.igesture.algorithm.signature.SignatureAlgorithm;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.storage.IStorageManager;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.locator.Service;
import org.ximtec.igesture.tool.util.PropertyChangeVisitor;
import org.ximtec.igesture.tool.util.StorageManagerProxy;
import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmList;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmWrapper;


public class MainModel implements Service {

   public static final String IDENTIFIER = "MainModel";

   private IStorageManager storageManager;

   private MainController mainController;


   public MainModel(StorageEngine engine, MainController mainController) {

      this.mainController = mainController;

      // StorageManager is wrapped with a Dynamic Proxy to register a
      // PropertyChangeListener
      PropertyChangeVisitor visitor = new PropertyChangeVisitor(mainController);
      this.storageManager = StorageManagerProxy.newInstance(new StorageManager(
            engine), visitor);
   }



   /**
    * Returns a list of Gesture Sets
    * @return a list of Gesture Sets
    */
   public List<GestureSet> getGestureSets() {
      return storageManager.load(GestureSet.class);
   }


   public List<Class< ? extends Algorithm>> getAlgorithms() {
      // FIXME TODO load class names from a property file
      List<Class< ? extends Algorithm>> algorithms = new ArrayList<Class< ? extends Algorithm>>();
      algorithms.add(RubineAlgorithm.class);
      algorithms.add(SigerRecogniser.class);
      algorithms.add(SignatureAlgorithm.class);
      return algorithms;
   }


   public List<Configuration> getConfigurations() {
      return storageManager.load(Configuration.class);
   }


   public GestureSetList getRootSet() {
      GestureSetList rootSet = new GestureSetList();
      for (GestureSet gestureSet : getGestureSets()) {
         rootSet.addGestureSet(gestureSet);
      }
      rootSet.addPropertyChangeListener(mainController);

      return rootSet;
   }
   
   public AlgorithmList getAlgorithmList() {
      AlgorithmList algorithmList = new AlgorithmList();
      for (Class< ? extends Algorithm> algorithmClass : getAlgorithms() ) {
         AlgorithmWrapper algorithmWrapper = new AlgorithmWrapper(algorithmClass);
         algorithmWrapper.addPropertyChangeListener(mainController);
         algorithmList.addAlgorithm(algorithmWrapper);
      }
      algorithmList.addPropertyChangeListener(mainController);

      return algorithmList;
   }


   @Override
   public String getIdentifier() {
      return IDENTIFIER;
   }


   @Override
   public void reset() {
      // TODO Auto-generated method stub
   }


   @Override
   public void start() {
      // TODO Auto-generated method stub

   }


   @Override
   public void stop() {
      storageManager.dispose();
   }


   public IStorageManager getStorageManager() {
      return storageManager;
   }

}
