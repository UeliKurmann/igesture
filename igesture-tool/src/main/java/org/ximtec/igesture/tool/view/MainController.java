package org.ximtec.igesture.tool.view;

import hacks.GuiBundleService;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.apache.log4j.Logger;
import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.storage.XMLStorageEngine;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.view.admin.AdminController;

public class MainController implements Controller {
  
  private static final Logger LOG = Logger.getLogger(MainController.class);

  private Locator locator;
  private MainModel mainModel;
  private MainView mainView;
  private GuiBundleService guiBundle;
  
  private List<Controller> controllers;

  public MainController() {
    controllers = new ArrayList<Controller>();
    
    guiBundle = new GuiBundleService("igestureMenu");
    mainModel = new MainModel(new XMLStorageEngine("file.xml"),this);
    
    locator = Locator.getDefault();
    locator.addService(mainModel);
    locator.addService(guiBundle);
    
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
    
    
    
    //dispatch property change event to all controllers
    for(PropertyChangeListener listener:controllers){
      listener.propertyChange(evt);
    }
    
    //FIXME implement store insert, remove and update persistent model
    if(evt.getSource() instanceof IndexedPropertyChangeEvent){
      LOG.info("Store, Delete, Update: indexed property "+evt.getSource());
      if(evt.getOldValue() == null){
        mainModel.getStorageManager().store((DataObject)evt.getNewValue());
        mainModel.getStorageManager().update((DataObject)evt.getSource());
      }
      
    }else{
      LOG.info("Update: property "+evt.getSource());
      mainModel.getStorageManager().update((DataObject)evt.getSource());
    }
    
   
    
  }

  @Override
  public JComponent getView() {
    return null;
  }
  
 

}
