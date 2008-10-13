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
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.storage.IStorageManager;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.locator.Service;
import org.ximtec.igesture.tool.util.PropertyChangeVisitor;
import org.ximtec.igesture.tool.util.StorageManagerProxy;
import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmList;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmWrapper;
import org.ximtec.igesture.tool.view.testset.wrapper.TestSetList;


public class MainModel implements Service {

   public static final String IDENTIFIER = "MainModel";

   private IStorageManager storageManager;

   private MainController mainController;

   /**
    * Constructs a new Main Model. 
    * @param engine the storage engine to be used.
    * @param mainController the main controller of the application.
    */
   public MainModel(StorageEngine engine, MainController mainController) {

      this.mainController = mainController;
 
      // StorageManager is wrapped with a Dynamic Proxy to register a
      // PropertyChangeListener
      setStorageEngine(engine);
   }

   /**
    * Returns a list of Gesture Sets
    * @return a list of Gesture Sets
    */
   public List<GestureSet> getGestureSets() {
      return storageManager.load(GestureSet.class);
   }
   
   /**
    * Returns a list of Test Sets
    * @return a list of Test Sets
    */
   public List<TestSet> getTestSets() {
      return storageManager.load(TestSet.class);
   }


   /**
    * Returns a list of all available algorithms. 
    * 
    * TODO: The list of algorithm should be configurable in a property file. 
    * @return a list of all available algorithms. 
    */
   public List<Class< ? extends Algorithm>> getAlgorithms() {
      List<Class< ? extends Algorithm>> algorithms = new ArrayList<Class< ? extends Algorithm>>();
      algorithms.add(RubineAlgorithm.class);
      algorithms.add(SigerRecogniser.class);
      algorithms.add(SignatureAlgorithm.class);
      return algorithms;
   }

   /**
    * Returns a list of configurations
    * @return a list of configurations
    */
   public List<Configuration> getConfigurations() {
      return storageManager.load(Configuration.class);
   }


   /**
    * Returns a GestureSetList (all Gesture Sets are wrapped into a GestureSetList)
    * @return a GestureSetList
    */
   public GestureSetList getGestureSetList() {
      GestureSetList rootSet = new GestureSetList();
      rootSet.addPropertyChangeListener(mainController);
      
      return rootSet;
   }
   
   public TestSetList getTestSetList(){
      TestSetList testSetList = new TestSetList();
      testSetList.addPropertyChangeListener(mainController);
      return testSetList;
   }
   
   /**
    * Returns a GestureSetList (all Algorithm are wrapped into a AlgorithmList)
    * @return a GestureSetList (all Algorithm are wrapped into a AlgorithmList)
    */
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


   /**
    * Returns the Storage Manager
    * TODO: Investigate design! Should be encapsulated. (UK) 
    * @return
    */
   public IStorageManager getStorageManager() {
      return storageManager;
   }
   
   /**
    * Sets the StorageManager. This method is used to change the data source
    * of the Main Model. 
    * 
    * A proxy is used to handle wrapper objects. 
    * 
    * @param storageEngine
    */
   public void setStorageEngine(StorageEngine storageEngine){
      PropertyChangeVisitor visitor = new PropertyChangeVisitor(mainController);
      this.storageManager = StorageManagerProxy.newInstance(new StorageManager(storageEngine), visitor);
   }

}
