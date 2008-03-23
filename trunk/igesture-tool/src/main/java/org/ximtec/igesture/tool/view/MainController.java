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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JComponent;

import org.sigtec.input.BufferedInputDeviceEventListener;
import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.io.MouseReader;
import org.ximtec.igesture.io.MouseReaderEventListener;
import org.ximtec.igesture.storage.XMLStorageEngine;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.service.InputDeviceClientService;
import org.ximtec.igesture.tool.view.admin.AdminController;


public class MainController implements Controller {

   private static final String RESOURCE_BUNDLE = "igestureMenu";

   private static final Logger LOG = Logger.getLogger(MainController.class.getName());

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
      mainModel = new MainModel(new XMLStorageEngine("file.xml"), this);

      deviceClient = new InputDeviceClientService(new MouseReader(),
            new BufferedInputDeviceEventListener(new MouseReaderEventListener(),
                  100));

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
   }


   @Override
   public void propertyChange(PropertyChangeEvent evt) {
      LOG.info("PropertyChange");

      // dispatch property change event to all controllers
      for (PropertyChangeListener listener : controllers) {
         listener.propertyChange(evt);
      }

      // FIXME implement store insert, remove and update persistent model
      if (evt.getSource() instanceof IndexedPropertyChangeEvent) {
         LOG.info("Store, Delete, Update: indexed property " + evt.getSource());
         if (evt.getOldValue() == null) {
            mainModel.getStorageManager().store((DataObject)evt.getNewValue());
            mainModel.getStorageManager().update((DataObject)evt.getSource());
         }

      }
      else {
         LOG.info("Update: property " + evt.getSource());
         mainModel.getStorageManager().update((DataObject)evt.getSource());
      }

   }


   @Override
   public JComponent getView() {
      return null;
   }

}
