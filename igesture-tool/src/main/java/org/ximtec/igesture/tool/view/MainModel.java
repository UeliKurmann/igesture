/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose      :   The main model.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 23.03.2008       ukurmann    Initial Release
 * 29.10.2008       bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.rubine.RubineAlgorithm;
import org.ximtec.igesture.algorithm.siger.SigerAlgorithm;
import org.ximtec.igesture.algorithm.signature.SiGridAlgorithm;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.storage.IStorageManager;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.locator.RunnableService;
import org.ximtec.igesture.tool.util.PropertyChangeVisitor;
import org.ximtec.igesture.tool.util.StorageManagerProxy;
import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmList;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmWrapper;
import org.ximtec.igesture.tool.view.testset.wrapper.TestSetList;

/**
 * The main model.
 * 
 * @version 1.0, Mar 2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class MainModel implements RunnableService {

  public static final String IDENTIFIER = "MainModel";

  private IStorageManager storageManager;

  private MainController mainController;

  private Properties properties;

  private AlgorithmList algorithmList;

  /**
   * Constructs a new main model.
   * 
   * @param engine
   *          the storage engine to be used.
   * @param mainController
   *          the main controller of the application.
   * @param proeprties
   *          the properties
   */
  public MainModel(StorageEngine engine, MainController mainController, Properties properties) {
    this.mainController = mainController;
    this.properties = properties;
    setStorageEngine(engine);
  }

  /**
   * Returns a list with all gesture sets.
   * 
   * @return a list with all gesture sets.
   */
  public List<GestureSet> getGestureSets() {
    return storageManager.load(GestureSet.class);
  } // getGestureSets

  /**
   * Returns a list with all test sets.
   * 
   * @return a list with all test sets.
   */
  public List<TestSet> getTestSets() {
    return storageManager.load(TestSet.class);
  } // getTestSets

  /**
   * Returns a list of all available algorithms.
   * 
   * TODO: The list of algorithm should be configurable in a property file.
   * 
   * @return a list of all available algorithms.
   */
  public List<Class<? extends Algorithm>> getAlgorithms() {
    List<Class<? extends Algorithm>> algorithms = new ArrayList<Class<? extends Algorithm>>();
    algorithms.add(RubineAlgorithm.class);
    algorithms.add(SigerAlgorithm.class);
    algorithms.add(SiGridAlgorithm.class);
    return algorithms;
  } // getAlgorithms

  /**
   * Returns a list of configurations.
   * 
   * @return a list of configurations.
   */
  public List<Configuration> getConfigurations() {
    return storageManager.load(Configuration.class);
  } // getConfigurations

  /**
   * Returns a gesture set list (all gesture sets are wrapped in a
   * GestureSetList)
   * 
   * @return a gesture set list.
   */
  public GestureSetList getGestureSetList() {
    GestureSetList rootSet = new GestureSetList(this);
    rootSet.addPropertyChangeListener(mainController);
    return rootSet;
  } // getGestureSetList

  public TestSetList getTestSetList() {
    TestSetList testSetList = new TestSetList(this);
    testSetList.addPropertyChangeListener(mainController);
    return testSetList;
  } // getTestSetList

  /**
   * Returns an algorithm list (all algorithms are wrapped in an AlgorithmList)
   * 
   * Attention: Implementation depends on the assumption that the list of algorithm
   * will not change during runtime.
   * 
   * TODO: Merge new/removed algorithms in the list during runtime.
   * 
   * @return an algorithm list (all algorithms are wrapped in an AlgorithmList)
   */
  public synchronized AlgorithmList getAlgorithmList() {
    if (algorithmList == null) {
      algorithmList = new AlgorithmList(this);

      for (Class<? extends Algorithm> algorithmClass : getAlgorithms()) {
        AlgorithmWrapper algorithmWrapper = new AlgorithmWrapper(this, algorithmClass);
        algorithmWrapper.addPropertyChangeListener(mainController);
        algorithmList.addAlgorithm(algorithmWrapper);
      }

      algorithmList.addPropertyChangeListener(mainController);
    }
    return algorithmList;
  } // getAlgorithmList

  @Override
  public String getIdentifier() {
    return IDENTIFIER;
  } // getIdentifier

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
   * Returns the Storage Manager TODO: Investigate design! Should be
   * encapsulated. (UK)
   * 
   * @return
   */
  public IStorageManager getStorageManager() {
    return storageManager;
  }

  /**
   * Sets the storage manager. This method is used to change the main model's
   * data source (the storage manager is wrapped in a dynamic proxy to register
   * a PropertyChangeListener).
   * 
   * @param storageEngine
   */
  public void setStorageEngine(StorageEngine storageEngine) {
    PropertyChangeVisitor visitor = new PropertyChangeVisitor(mainController);
    this.storageManager = StorageManagerProxy.newInstance(new StorageManager(storageEngine), visitor);
  } // setStorageEngine

  /**
   * Add / Set a property. Properties are key/value pairs and made persistent
   * after shutting down the application.
   * 
   * @param key
   * @param value
   */
  public void setProperty(String key, String value) {
    properties.setProperty(key, value);
  }

  /**
   * Returns the property value of the given key.
   * 
   * @param key
   *          the key of the property.
   * @return the value of the property.
   */
  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  /**
   * Removes a property.
   * 
   * @param key
   */
  public void removeProperty(String key) {
    properties.remove(key);
  }

  public Properties getProperties() {
    return properties;
  }

}
