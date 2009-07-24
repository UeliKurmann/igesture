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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.DataObjectWrapper;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.ExecCmd;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.locator.Service;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.ExtensionFileFilter;
import org.ximtec.igesture.tool.util.FileFilterFactory;
import org.ximtec.igesture.tool.view.action.ExitAction;
import org.ximtec.igesture.tool.view.action.LoadWorkspaceAction;
import org.ximtec.igesture.tool.view.action.ShowAboutAction;
import org.ximtec.igesture.tool.view.action.StoreWorkspaceAction;
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

  private static final String USER_DIR = "user.dir";

  private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

  private static final String PROPERTIES = "properties.xml";

  public static final String CMD_LOAD = "load";
  public static final String CMD_CLOSE = "close";
  public static final String CMD_SAVE = "save";
  public static final String CMD_START_WAITING = "startWaiting";
  public static final String CMD_STOP_WAITING = "stopWaiting";
  public static final String CMD_SHOW_ABOUT_DIALOG = "showAboutDialog";

  private static Class<?>[] controllers = new Class<?>[] { WelcomeController.class, AdminController.class,
      TestbenchController.class, BatchController.class, TestSetController.class };

  public static final String IDENTIFIER = "mainController";

  private static final String RESOURCE_BUNDLE = "igestureMenu";

  // Services
  private MainModel mainModel;
  private GuiBundleService guiBundle;
  private SwingMouseReaderService deviceClient;

  // Main View
  private MainView mainView;

  private Properties properties;

  public MainController() {
    super(null);
    initServices();
    initMainView();
    initSubContrllersViews();
  }

  private void initServices() {
    guiBundle = new GuiBundleService(RESOURCE_BUNDLE);

    properties = new Properties();

    try {
      properties.loadFromXML(new FileInputStream(PROPERTIES));
    } catch (Exception e) {
      // if no properties are available, set default values
      properties.setProperty(Property.WORKING_DIRECTORY, System.getProperty(USER_DIR));
      LOGGER.log(Level.WARNING, "Failed to load properties.");
    }

    File database = null;
    while (database == null) {
      database = getDatabase();
    }

    mainModel = new MainModel(StorageManager.createStorageEngine(database), this, properties);
    deviceClient = new SwingMouseReaderService();

    /**
     * Register the services
     */

    setLocator(Locator.getDefault());
    getLocator().addService(mainModel);
    getLocator().addService(guiBundle);
    getLocator().addService(deviceClient);
    getLocator().addService(new ComponentFactory(guiBundle));
    getLocator().addService(this);
    getLocator().startAll();
  } // initServices

  /**
   * Initialises controllers an views connected to the main controller.
   */
  private void initSubContrllersViews() {
  
    if(SwingUtilities.isEventDispatchThread()){
      throw new RuntimeException("Must not be executed in the Event Dispatch Thread.");
    }
    
    final CyclicBarrier barrier = new CyclicBarrier(2);
    
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {

        for (Class<?> clazz : controllers) {
          try {

            Controller controller = null;
            if (clazz.getConstructor(Controller.class) != null) {
              // FIXME Generics!
              Constructor<?> constructor = clazz.getConstructor(Controller.class);
              controller = (Controller) constructor.newInstance(MainController.this);
            } else {
              controller = (Controller) clazz.newInstance();
            }
            addController(controller);
            mainView.addTab(controller.getView());
          } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not initialize view. " + clazz.getName(), e);
          }
        }

        try {
          //FIXME try to set a timeout to handle a deadlock
          barrier.await();
        } catch (Exception e) {
          LOGGER.log(Level.SEVERE, "View Initialization failed.");
        }

      }
    });

    try {
      barrier.await();
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "View Initialization failed.");
    }

  }

  private void initMainView() {
    if (mainView == null) {

      addAction(LoadWorkspaceAction.class, new LoadWorkspaceAction(this));
      addAction(StoreWorkspaceAction.class, new StoreWorkspaceAction(this));
      addAction(ExitAction.class, new ExitAction(this));
      addAction(ShowAboutAction.class, new ShowAboutAction(this));

      mainView = new MainView(this);
      mainView.addWindowListener(new MainWindowAdapter(this));
    }
  }

  @ExecCmd(name = CMD_LOAD)
  protected void execLoadCommand() {
    LOGGER.info("Command Load");
    File dataBase = getDatabase();

    if (dataBase != null) {
      mainView.removeAllTabs();
      mainModel.stop();
      mainModel.setStorageEngine(StorageManager.createStorageEngine(dataBase));
      mainModel.start();
      initSubContrllersViews();
    }

  } // execLoadCommand

  @ExecCmd(name = CMD_SAVE)
  protected void execSaveCommand() {
    LOGGER.info("Command Save");
    mainModel.getStorageManager().commit();
  } // execSaveCommand

  @ExecCmd(name = CMD_CLOSE)
  protected void execCloseCommand() {
    LOGGER.info("Command Close");

    String title = getComponentFactory().getGuiBundle().getName(GestureConstants.MAIN_CONTROLLER_DIALOG_EXIT);

    String text = getComponentFactory().getGuiBundle()
        .getShortDescription(GestureConstants.MAIN_CONTROLLER_DIALOG_EXIT);

    if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, text, title, JOptionPane.YES_NO_OPTION)) {
      mainModel.getStorageManager().commit();
      getLocator().stopAll();

      try {
        mainModel.getProperties().storeToXML(new FileOutputStream(PROPERTIES), "iGesture: " + new Date());
      } catch (Exception e) {
        LOGGER.log(Level.WARNING, "Failed to store properties.", e);
      }
      System.exit(0);
    }

  } // execCloseCommand

  private ComponentFactory getComponentFactory() {
    return getLocator().getService(ComponentFactory.class.getName(), ComponentFactory.class);
  }

  protected void execStartWaiting() {
    LOGGER.info("Start Progress Panel.");
  } // execStartWaiting

  protected void execStopWaiting() {
    LOGGER.info("Stop Progress Panel.");
  } // execStopWaiting

  @ExecCmd(name = CMD_SHOW_ABOUT_DIALOG)
  protected void execShowAboutDialog() {
    LOGGER.info("Show About Dialog.");
    AboutDialog dialog = new AboutDialog(GestureConstants.MENUBAR_ABOUT, getLocator().getService(
        GuiBundleService.IDENTIFIER, GuiBundleService.class));
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
        persist((IndexedPropertyChangeEvent) event);
      } else {
        persist((PropertyChangeEvent) event);
      }

    } else if (event.getSource() instanceof DataObjectWrapper) {
      LOGGER.info("DataObjectWrapper: "+event.getSource().getClass().getName() );

      if (event.getOldValue() instanceof DataObject && event.getNewValue() == null) {
        mainModel.getStorageManager().remove((DataObject) event.getOldValue());
      } else if (event.getNewValue() instanceof DataObject && event.getOldValue() == null) {
        mainModel.getStorageManager().store((DataObject) event.getNewValue());
      } else if (event.getNewValue() instanceof DataObject && event.getOldValue() != null) {
        mainModel.getStorageManager().update((DataObject) event.getNewValue());
      }

    }

  } // propertyChange

  private void persist(PropertyChangeEvent event) {
    LOGGER.info("Update: property " + event.getSource());
    mainModel.getStorageManager().update((DataObject) event.getSource());
  } // persist

  private void persist(IndexedPropertyChangeEvent event) {
    LOGGER.info("Store, Delete, Update: indexed property " + event.getSource());

    if (event.getOldValue() == null) {
      mainModel.getStorageManager().store((DataObject) event.getNewValue());
    } else if (event.getNewValue() == null && event.getOldValue() instanceof DataObject) {
      mainModel.getStorageManager().remove((DataObject) event.getOldValue());
    }

    mainModel.getStorageManager().update((DataObject) event.getSource());
  } // persist

  @Override
  public TabbedView getView() {
    return null;
  } // getView

  /**
   * Returns a file handle to the database to be opened.
   * 
   * @return file handle to the database to be opened.
   */
  // TODO select database file (ms access like application). show recent used
  // files.
  private File getDatabase() {
    File file = null;

    JFileChooser chooser = new JFileChooser();
    chooser.addChoosableFileFilter(FileFilterFactory.getWorkspaceDb4o());
    chooser.addChoosableFileFilter(FileFilterFactory.getWorkspaceXStream());
    chooser.setFileFilter(FileFilterFactory.getWorkspaceCompressed());
    chooser.setCurrentDirectory(new File(properties.getProperty(Property.WORKING_DIRECTORY)));
    chooser.showOpenDialog(null);
    file = chooser.getSelectedFile();

    try {
      ExtensionFileFilter fileFilter = (ExtensionFileFilter) chooser.getFileFilter();
      if (!fileFilter.accept(file)) {
        file = new File(file.getAbsolutePath() + "." + fileFilter.getExtension());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    if (file != null) {
      properties.setProperty(Property.WORKING_DIRECTORY, file.getParent());
    }

    return file;
  } // getDatabase

  @Override
  public String getIdentifier() {
    return IDENTIFIER;
  } // getIdentifier

}
