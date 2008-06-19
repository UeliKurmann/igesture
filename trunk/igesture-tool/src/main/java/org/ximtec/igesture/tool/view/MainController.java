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
import java.io.File;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.sigtec.input.BufferedInputDeviceEventListener;
import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.DataObjectWrapper;
import org.ximtec.igesture.io.MouseReaderEventListener;
import org.ximtec.igesture.io.SwingMouseReader;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.locator.Service;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.service.InputDeviceClientService;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.view.admin.AdminController;
import org.ximtec.igesture.tool.view.batch.BatchController;
import org.ximtec.igesture.tool.view.testbench.TestbenchController;


public class MainController extends DefaultController implements Service {

   public static final String CMD_LOAD = "load";
   public static final String CMD_CLOSE = "close";
   public static final String CMD_SAVE = "save";
   
   public enum Cmd{loadWorkspace, newWorkspace}
   
   public static final String IDENTIFIER = "mainController";
   
   private static final String RESOURCE_BUNDLE = "igestureMenu";

   private static final Logger LOG = Logger.getLogger(MainController.class
         .getName());

   // Service Locator
   private Locator locator;
   
   // Services
   private MainModel mainModel;
   private GuiBundleService guiBundle;
   private InputDeviceClientService deviceClient;

   // Main View
   private MainView mainView;
   
   public MainController() {
      
      
      
      initServices();
      initViews();
   }
   
   private void initServices(){
      guiBundle = new GuiBundleService(RESOURCE_BUNDLE);

      mainModel = new MainModel(StorageManager
            .createStorageEngine(getDatabase()), this);

      
      deviceClient = new InputDeviceClientService(new SwingMouseReader(),
            new BufferedInputDeviceEventListener(new MouseReaderEventListener(),
                  32000));

      /**
       * Register services
       */
      locator = Locator.getDefault();
      locator.addService(mainModel);
      locator.addService(guiBundle);
      locator.addService(deviceClient);
      locator.addService(this);
      locator.startAll();
   }


   /**
    * Initializes Views
    * TODO: generalization
    */
   private void initViews() {
      if(mainView == null){
         mainView = new MainView();
         mainView.addWindowListener(new MainWindowAdapter(this));
      }
      
      // TODO: avoid casts
      
      // Init Admin Tab
      AdminController adminController = new AdminController();
      addController(adminController);
      mainView.addTab((TabbedView)adminController.getView());

      // Init TestBench Tab
      TestbenchController testbenchController = new TestbenchController();
      addController(testbenchController);
      mainView.addTab((TabbedView)testbenchController.getView());
      
      // Batch Processing Tat
      BatchController batchController = new BatchController();
      addController(batchController);
      mainView.addTab((TabbedView)batchController.getView());
   }
   
   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.core.DefaultController#execute(org.ximtec.igesture.tool.core.ControllerCommand)
    */
   @Override
   public void execute(Command command) {
      // command dispatcher
      if(command != null && command.getCommand() != null){
         if(CMD_LOAD.equals(command.getCommand())){
            execLoadCommand();
         }else if(CMD_CLOSE.equals(command.getCommand())){
            execCloseCommand();
         }else if(CMD_SAVE.equals(command.getCommand())){
            execSaveCommand();
         }else{
            LOG.warning("Command not supportet. "+command.getCommand());
         }
      }else{
         LOG.warning("Command not set.");
      }
   }
   
   private void execLoadCommand(){
      LOG.info("Command Load");
      mainView.removeAllTabs();
      mainModel.stop();
      mainModel.setStorageEngine(StorageManager.createStorageEngine(getDatabase()));
      mainModel.start();
      
      initViews();
   }
   
   private void execSaveCommand(){
      LOG.info("Command Save");
      mainModel.getStorageManager().commit();
   }
   
   private void execCloseCommand(){
      LOG.info("Command Close");
      
      String title = ComponentFactory.getGuiBundle().getName(GestureConstants.MAIN_CONTROLLER_DIALOG_EXIT);
      String text = ComponentFactory.getGuiBundle().getShortDescription(GestureConstants.MAIN_CONTROLLER_DIALOG_EXIT);
      
      if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, text, title, JOptionPane.YES_NO_OPTION)){
         mainModel.getStorageManager().commit();
         Locator.getDefault().stopAll();
         System.exit(0);
      }
   }
 
   @Override
   public void propertyChange(PropertyChangeEvent event) {
      LOG.info("PropertyChange");
      super.propertyChange(event);
     
      // Dispatch DataObjects
      if (event.getSource() instanceof DataObject) {
         if (event instanceof IndexedPropertyChangeEvent) {
            persist((IndexedPropertyChangeEvent)event);
         }
         else {
            persist((PropertyChangeEvent)event);
         }
      }else if(event.getSource() instanceof DataObjectWrapper){
         LOG.info("DataObjectWrapper");
         if(event.getOldValue() instanceof DataObject && event.getNewValue() == null){
            mainModel.getStorageManager().remove((DataObject)event.getOldValue());
         }else if(event.getNewValue() instanceof DataObject && event.getOldValue() == null){
            mainModel.getStorageManager().store((DataObject)event.getNewValue());
         }else if(event.getNewValue() instanceof DataObject && event.getOldValue() != null){
            mainModel.getStorageManager().update((DataObject)event.getNewValue());
         }
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
    * TODO select database file (ms access like application). show recent used files. 
    * @return
    */
   private File getDatabase() {
      File file = null;
      while(file == null){
         JFileChooser chooser = new JFileChooser();
         chooser.showOpenDialog(null);
         file = chooser.getSelectedFile();
      }
      return file;
   }

   @Override
   public String getIdentifier() {
      return IDENTIFIER;
   }

   @Override
   public void reset() {
      LOG.warning("method not implemented.");
   }

   @Override
   public void start() {
      LOG.warning("method not implemented.");      
   }

   @Override
   public void stop() {
      LOG.warning("method not implemented.");
   }

}
