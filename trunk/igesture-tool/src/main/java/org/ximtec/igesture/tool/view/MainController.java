/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose      :   The main controller
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view;

import java.awt.Point;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.sigtec.input.BufferedInputDeviceEventListener;
import org.sigtec.util.Constant;
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
import org.ximtec.igesture.tool.util.FileFilterFactory;
import org.ximtec.igesture.tool.view.admin.AdminController;
import org.ximtec.igesture.tool.view.batch.BatchController;
import org.ximtec.igesture.tool.view.testbench.TestbenchController;
import org.ximtec.igesture.tool.view.testset.TestSetController;
import org.ximtec.igesture.tool.view.welcome.WelcomeController;


/**
 * The main controller class.
 * 
 * @version 1.0, Mar 2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class MainController extends DefaultController implements Service {

   private static final Logger LOGGER = Logger.getLogger(MainController.class
         .getName());

   public static final String CMD_LOAD = "load";
   public static final String CMD_CLOSE = "close";
   public static final String CMD_SAVE = "save";
   public static final String CMD_START_WAITING = "startWaiting";
   public static final String CMD_STOP_WAITING = "stopWaiting";
   public static final String CMD_SHOW_ABOUT_DIALOG = "showAboutDialog";

   public static final int BUFFER_SIZE = 32000;

   public enum Cmd {
      loadWorkspace, newWorkspace
   }

   public static final String IDENTIFIER = "mainController";

   private static final String RESOURCE_BUNDLE = "igestureMenu";

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


   private void initServices() {
      guiBundle = new GuiBundleService(RESOURCE_BUNDLE);
      mainModel = new MainModel(StorageManager
            .createStorageEngine(getDatabase()), this);
      deviceClient = new InputDeviceClientService(new SwingMouseReader(),
            new BufferedInputDeviceEventListener(new MouseReaderEventListener(),
                  BUFFER_SIZE));

      /**
       * Register the services
       */
      locator = Locator.getDefault();
      locator.addService(mainModel);
      locator.addService(guiBundle);
      locator.addService(deviceClient);
      locator.addService(this);
      locator.startAll();
   } // initServices


   /**
    * Initialises the views TODO: use a dynamic initialisation
    */
   private void initViews() {
      if (mainView == null) {
         mainView = new MainView();
         mainView.addWindowListener(new MainWindowAdapter(this));
      }

      final CyclicBarrier barrier = new CyclicBarrier(2);
      SwingUtilities.invokeLater(new Runnable() {

         @Override
         public void run() {
            // TODO: avoid casts
            // Init Welcome Tab
            WelcomeController welcomeController = new WelcomeController();
            addController(welcomeController);
            mainView.addTab((TabbedView)welcomeController.getView());

            // Init Admin Tab
            AdminController adminController = new AdminController();
            addController(adminController);
            mainView.addTab((TabbedView)adminController.getView());

            // Init TestBench Tab
            TestbenchController testbenchController = new TestbenchController();
            addController(testbenchController);
            mainView.addTab((TabbedView)testbenchController.getView());

            // Batch Processing Tab
            BatchController batchController = new BatchController();
            addController(batchController);
            mainView.addTab((TabbedView)batchController.getView());

            // Test Set Tab
            TestSetController testSetController = new TestSetController();
            addController(testSetController);
            mainView.addTab((TabbedView)testSetController.getView());

            try {
               barrier.await();
            }
            catch (Exception e) {
               LOGGER.log(Level.SEVERE, "View Initialization failed.");
            }

         }
      });

      try {
         barrier.await();
      }
      catch (Exception e) {
         LOGGER.log(Level.SEVERE, "View Initialization failed.");
      }

   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.core.DefaultController#execute(org.ximtec.igesture.tool.core.ControllerCommand)
    */
   @Override
   public void execute(Command command) {
      // command dispatcher
      if (command != null && command.getCommand() != null) {

         if (CMD_LOAD.equals(command.getCommand())) {
            execLoadCommand();
         }
         else if (CMD_CLOSE.equals(command.getCommand())) {
            execCloseCommand();
         }
         else if (CMD_SAVE.equals(command.getCommand())) {
            execSaveCommand();
         }
         else if (CMD_START_WAITING.equals(command.getCommand())) {
            execStartWaiting();
         }
         else if (CMD_STOP_WAITING.equals(command.getCommand())) {
            execStopWaiting();
         }
         else if (CMD_SHOW_ABOUT_DIALOG.equals(command.getCommand())) {
            execShowAboutDialog();
         }
         else {
            LOGGER.warning("Command not handled by MainController: '" + command.getCommand()
                  + Constant.SINGLE_QUOTE);
            super.execute(command);
         }

      }
      else {
         LOGGER.warning("Command not set.");
      }

   } // execute


   private void execLoadCommand() {
      LOGGER.info("Command Load");
      File dataBase = getDatabase();

      if (dataBase != null) {
         mainView.removeAllTabs();
         mainModel.stop();
         mainModel
               .setStorageEngine(StorageManager.createStorageEngine(dataBase));
         mainModel.start();
         initViews();
      }

   } // execLoadCommand


   private void execSaveCommand() {
      LOGGER.info("Command Save");
      mainModel.getStorageManager().commit();
   } // execSaveCommand


   private void execCloseCommand() {
      LOGGER.info("Command Close");
      String title = ComponentFactory.getGuiBundle().getName(
            GestureConstants.MAIN_CONTROLLER_DIALOG_EXIT);
      String text = ComponentFactory.getGuiBundle().getShortDescription(
            GestureConstants.MAIN_CONTROLLER_DIALOG_EXIT);

      if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, text,
            title, JOptionPane.YES_NO_OPTION)) {
         mainModel.getStorageManager().commit();
         Locator.getDefault().stopAll();
         System.exit(0);
      }

   } // execCloseCommand


   private void execStartWaiting() {
      LOGGER.info("Start Progress Panel.");
   } // execStartWaiting


   private void execStopWaiting() {
      LOGGER.info("Stop Progress Panel.");
   } // execStopWaiting


   private void execShowAboutDialog() {
      LOGGER.info("Show About Dialog.");
      AboutDialog dialog = new AboutDialog(GestureConstants.MENUBAR_ABOUT,
            Locator.getDefault().getService(GuiBundleService.IDENTIFIER,
                  GuiBundleService.class));
      Point point = mainView.getLocation();
      point.translate(100, 60);
      dialog.setLocation(point);
      dialog.setVisible(true);
   } // execShowAboutDialog


   @Override
   public void propertyChange(PropertyChangeEvent event) {
      LOGGER.info("PropertyChange");
      super.propertyChange(event);

      // Dispatch DataObjects
      if (event.getSource() instanceof DataObject) {

         if (event instanceof IndexedPropertyChangeEvent) {
            persist((IndexedPropertyChangeEvent)event);
         }
         else {
            persist((PropertyChangeEvent)event);
         }

      }
      else if (event.getSource() instanceof DataObjectWrapper) {
         LOGGER.info("DataObjectWrapper");

         if (event.getOldValue() instanceof DataObject
               && event.getNewValue() == null) {
            mainModel.getStorageManager()
                  .remove((DataObject)event.getOldValue());
         }
         else if (event.getNewValue() instanceof DataObject
               && event.getOldValue() == null) {
            mainModel.getStorageManager().store((DataObject)event.getNewValue());
         }
         else if (event.getNewValue() instanceof DataObject
               && event.getOldValue() != null) {
            mainModel.getStorageManager()
                  .update((DataObject)event.getNewValue());
         }

      }

   } // propertyChange


   private void persist(PropertyChangeEvent event) {
      LOGGER.info("Update: property " + event.getSource());
      mainModel.getStorageManager().update((DataObject)event.getSource());
   } // persist


   private void persist(IndexedPropertyChangeEvent event) {
      LOGGER
            .info("Store, Delete, Update: indexed property " + event.getSource());

      if (event.getOldValue() == null) {
         mainModel.getStorageManager().store((DataObject)event.getNewValue());
      }
      else if (event.getNewValue() == null
            && event.getOldValue() instanceof DataObject) {
         mainModel.getStorageManager().remove((DataObject)event.getOldValue());
      }

      mainModel.getStorageManager().update((DataObject)event.getSource());
   } // persist


   @Override
   public JComponent getView() {
      return null;
   } // getView


   /**
    * Returns a file handle to the database to be opened.
    * @return file handle to the database to be opened.
    */
   // TODO select database file (ms access like application). show recent used
   // files.
   private File getDatabase() {
      File file = null;

      while (file == null) {
         JFileChooser chooser = new JFileChooser();
         chooser.addChoosableFileFilter(FileFilterFactory.getWorkspaceDb4o());
         chooser.addChoosableFileFilter(FileFilterFactory.getWorkspaceXStream());
         chooser.setFileFilter(FileFilterFactory.getWorkspaceCompressed());
         chooser.showOpenDialog(null);
         file = chooser.getSelectedFile();
      }

      return file;
   } // getDatabase


   @Override
   public String getIdentifier() {
      return IDENTIFIER;
   } // getIdentifier

}
