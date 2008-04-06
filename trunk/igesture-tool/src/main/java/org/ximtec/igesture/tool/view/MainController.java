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

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

import org.sigtec.input.BufferedInputDeviceEventListener;
import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.DataObjectWrapper;
import org.ximtec.igesture.io.MouseReader;
import org.ximtec.igesture.io.MouseReaderEventListener;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.service.InputDeviceClientService;
import org.ximtec.igesture.tool.view.admin.AdminController;
import org.ximtec.igesture.tool.view.testbench.TestbenchController;


public class MainController implements Controller {

   private static final String RESOURCE_BUNDLE = "igestureMenu";

   private static final Logger LOG = Logger.getLogger(MainController.class
         .getName());

   private Locator locator;
   private MainModel mainModel;
   private MainView mainView;
   private GuiBundleService guiBundle;
   private InputDeviceClientService deviceClient;

   private List<Controller> controllers;


   public MainController() {
      controllers = new ArrayList<Controller>();

      guiBundle = new GuiBundleService(RESOURCE_BUNDLE);

      // FIXME for test purposes only!
      mainModel = new MainModel(StorageManager
            .createStorageEngine(getDatabase()), this);

      // FIXME Buffer Size?
      deviceClient = new InputDeviceClientService(new MouseReader(),
            new BufferedInputDeviceEventListener(new MouseReaderEventListener(),
                  32000));

      /**
       * Register services
       */
      locator = Locator.getDefault();
      locator.addService(mainModel);
      locator.addService(guiBundle);
      locator.addService(deviceClient);
      locator.startAll();

      initViews();

   }


   private void initViews() {
      mainView = new MainView();

      AdminController adminController = new AdminController();
      controllers.add(adminController);
      mainView.addTab((TabbedView)adminController.getView());

      TestbenchController testbenchController = new TestbenchController();
      controllers.add(testbenchController);
      mainView.addTab((TabbedView)testbenchController.getView());
   }


   @Override
   public void propertyChange(PropertyChangeEvent evt) {
      LOG.info("PropertyChange");

      // dispatch property change event to all controllers
      for (PropertyChangeListener listener : controllers) {
         listener.propertyChange(evt);
      }

      // FIXME implement store insert, remove and update persistent model
      // Dispatch DataObjects
      if (evt.getSource() instanceof DataObject) {
         if (evt instanceof IndexedPropertyChangeEvent) {
            persist((IndexedPropertyChangeEvent)evt);
         }
         else {
            persist((PropertyChangeEvent)evt);
         }
      }else if(evt.getSource() instanceof DataObjectWrapper){
         if(evt.getOldValue() instanceof DataObject && evt.getNewValue() == null){
            mainModel.getStorageManager().remove((DataObject)evt.getOldValue());
         }else if(evt.getNewValue() instanceof DataObject && evt.getOldValue() == null){
            mainModel.getStorageManager().store((DataObject)evt.getNewValue());
         }else if(evt.getNewValue() instanceof DataObject && evt.getOldValue() != null){
            mainModel.getStorageManager().update((DataObject)evt.getNewValue());
         }
         LOG.info("DataObjectWrapper");
      }
   }
   
   private void persist(PropertyChangeEvent event){
      LOG.info("Update: property " + event.getSource());
      mainModel.getStorageManager().update((DataObject)event.getSource());
   }
   
   private void persist(IndexedPropertyChangeEvent event){
      LOG.info("Store, Delete, Update: indexed property " + event.getSource());
      if (event.getOldValue() == null) {
         mainModel.getStorageManager().store((DataObject)event.getNewValue());
      }else if(event.getNewValue() == null && event.getOldValue() instanceof DataObject){
         mainModel.getStorageManager().remove((DataObject)event.getOldValue());
      }
      mainModel.getStorageManager().update((DataObject)event.getSource()); 
   }
   
   @Override
   public JComponent getView() {
      return null;
   }

   /**
    * TODO select database file (access like application)
    * @return
    */
   private File getDatabase() {
      JFileChooser chooser = new JFileChooser();
      chooser.showOpenDialog(null);
      return chooser.getSelectedFile();
   }

}
