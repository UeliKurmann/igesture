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

import java.awt.Cursor;
import java.awt.Point;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.DataObjectWrapper;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.storage.StorageEngineConverter;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.storage.StorageManager.StorageEngineType;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.EdtProxy;
import org.ximtec.igesture.tool.core.ExecCmd;
import org.ximtec.igesture.tool.core.GenericLocateableAction;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.locator.Service;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.ExtensionFileFilter;
import org.ximtec.igesture.tool.util.FileType;
import org.ximtec.igesture.tool.view.admin.AdminController;
import org.ximtec.igesture.tool.view.batch.BatchController;
import org.ximtec.igesture.tool.view.devicemanager.DeviceManagerController;
import org.ximtec.igesture.tool.view.devicemanager.IDeviceManager;
import org.ximtec.igesture.tool.view.testbench.TestbenchController;
import org.ximtec.igesture.tool.view.testset.TestSetController;
import org.ximtec.igesture.tool.view.welcome.WelcomeController;

/**
 * The main controller class.
 * 
 * @version 1.0, Mar 2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class MainController extends DefaultController implements Service {

	private static final Logger LOGGER = Logger.getLogger(MainController.class
			.getName());

	// Command Strings
	public static final String CMD_LOAD = "load";
	public static final String CMD_EXIT = "close";
	public static final String CMD_SAVE = "save";
	public static final String CMD_SAVE_AS = "saveAs";
	public static final String CMD_START_WAITING = "startWaiting";
	public static final String CMD_STOP_WAITING = "stopWaiting";
	public static final String CMD_SHOW_ABOUT_DIALOG = "showAboutDialog";
	public static final String CMD_CLOSE_WS = "closeWorkspace";
	public static final String CMD_CHANGE_TAB = "changeTab";
	public static final String CMD_SHOW_DEVICE_MANAGER = "showDeviceManager";

	// List of controllers (a project is active)
	private static Class<?>[] activeControllers = new Class<?>[] {
			AdminController.class, TestbenchController.class,
			BatchController.class, TestSetController.class };

	// List of controllers (no project active)
	private static Class<?>[] passiveControllers = new Class<?>[] { WelcomeController.class };

	private static final String IDENTIFIER = "mainController";

	// Services
	private MainModel mainModel;
	private GuiBundleService guiBundle;
	private SwingMouseReaderService deviceClient;
	private StorageEngineType storageEngineType;

	// Device Manager
	private IDeviceManager deviceManagerController;
	
	// Main View
	private IMainView mainView;

	// Properties
	private Properties properties;

	/**
	 * Flag indicates, if the project is modified False: Project was not
	 * modified, no save needed.
	 */
	private boolean modelIsModified;

	/**
	 * Default Constructor. Initialises the application.
	 * <ul>
	 * <li>Init Services</li>
	 * <li>Init Main View</li>
	 * <li>Init Sub-Controller and Sub-Views</li>
	 * </ul>
	 */
	public MainController() {
		super(null);
		initServices();
		initMainView();
		initSubControllersAndViews(passiveControllers);
		initDeviceManager();
		getAction(CMD_CLOSE_WS).setEnabled(false);
		getAction(CMD_SAVE).setEnabled(false);
		getAction(CMD_SAVE_AS).setEnabled(false);
		this.modelIsModified = false;
	}

	/**
	 * Instantiate a device manager controller
	 */
	private void initDeviceManager() {
		deviceManagerController = new DeviceManagerController(this,GestureConstants.DEVICE_MANAGER,
				getLocator().getService(GuiBundleService.IDENTIFIER,GuiBundleService.class));		
	}
	

	/**
	 * Instantiates a controller using reflection. If the controller has a
	 * Constructor taking a parent controller as argument, this constructor is
	 * used. If no such constructor exists, the default constructor is used.
	 * 
	 * @param controllerClass
	 *            the type of the controller
	 * @return the created controller
	 * @throws Exception
	 */
	private Controller createController(Class<?> controllerClass)
		throws Exception
	{
		
		boolean instantiated = false;
		
		Controller controller = null;
		try {
			if (!instantiated && (controllerClass.getConstructor(new Class<?>[]{Controller.class,IDeviceManager.class}) != null)) {
				Constructor<?> constructor = controllerClass
						.getConstructor(new Class<?>[]{Controller.class,IDeviceManager.class});
				controller = (Controller) constructor
						.newInstance(MainController.this,deviceManagerController);
				instantiated = true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (!instantiated && (controllerClass.getConstructor(Controller.class) != null)){ 
				Constructor<?> constructor = controllerClass
						.getConstructor(Controller.class);
				controller = (Controller) constructor
						.newInstance(MainController.this);
				instantiated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(!instantiated)
		{
			try {
				controller = (Controller) controllerClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(controller == null)
			throw new Exception();
		return controller;
	}

	/**
	 * Command is executed after changing a tab.
	 */
	@ExecCmd(name = CMD_CHANGE_TAB)
	protected void execChangeTab() {
		LOGGER.info("Change Tab");
		GestureDevice<?, ?> gestureDevice = getLocator().getService(
				SwingMouseReaderService.IDENTIFIER, GestureDevice.class);
		if (gestureDevice != null) {
			gestureDevice.clear();
		}
	}

	/**
	 * Command to close a project.
	 */
	@ExecCmd(name = CMD_CLOSE_WS)
	protected void execCloseWsCommand() {
		LOGGER.info("Command Close Workspace");
		if (modelIsModified
				&& mainModel.isActive()
				&& JOptionPane.YES_OPTION == showYesNoDialog(GestureConstants.MAIN_CONTROLLER_DIALOG_SAVE)) {
			mainModel.getStorageManager().commit();
		}
		mainView.removeAllTabs();
		mainModel.stop();
		mainModel.setStorageEngine(null);
		initSubControllersAndViews(passiveControllers);

		getAction(CMD_CLOSE_WS).setEnabled(false);
		getAction(CMD_SAVE).setEnabled(false);
		getAction(CMD_SAVE_AS).setEnabled(false);
		getAction(CMD_LOAD).setEnabled(true);

		mainView.setTitlePostfix(null);

	} // execLoadCommand

	/**
	 * Command to exit the application.
	 */
	@ExecCmd(name = CMD_EXIT)
	protected void execExitCommand() {
		LOGGER.info("Command Exit");

		if (modelIsModified) {
			switch (showYesNoDialog(GestureConstants.MAIN_CONTROLLER_DIALOG_EXIT)) {
			case JOptionPane.YES_OPTION:
				mainModel.getStorageManager().commit();
				shutdownApplication();
				break;

			case JOptionPane.NO_OPTION:
				shutdownApplication();
				break;

			case JOptionPane.CANCEL_OPTION:
				LOGGER.info("Exit cancelled.");
				break;
			}
		} else {
			shutdownApplication();
		}
	} // execExitCommand

	/**
	 * Terminates the application. All services are stopped, the properties are
	 * stored. afterwards the application is terminated with an exit statement.
	 */
	private void shutdownApplication() {
		getLocator().stopAll();
		storeProperties();
		System.exit(0);
	} // shutdownAPplication

	/**
	 * Persist the properties. This method should be called before shutting down
	 * the application.
	 */
	private void storeProperties() {
		try {
			mainModel.getProperties().storeToXML(
					new FileOutputStream(GestureConstants.PROPERTIES),
					"iGesture: " + new Date());
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Failed to store properties.", e);
		}
	}

	/**
	 * Command to load a project.
	 */
	@ExecCmd(name = CMD_LOAD)
	protected void execLoadCommand() {
		LOGGER.info("Command Load");
		File dataBase = getDatabase(false);

		if (dataBase != null) {
		  mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			mainView.removeAllTabs();
			mainModel.stop();
			loadAndInitProject(dataBase);
			mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

	} // execLoadCommand

	private void loadAndInitProject(File dataBase) {
		storageEngineType = StorageManager.getEngineType(dataBase);
		mainModel
				.setStorageEngine(StorageManager.createStorageEngine(dataBase));
		mainModel.start();
		initSubControllersAndViews(activeControllers);

		// activate actions
		getAction(CMD_CLOSE_WS).setEnabled(true);
		getAction(CMD_SAVE).setEnabled(true);
		getAction(CMD_LOAD).setEnabled(false);
		getAction(CMD_SAVE_AS).setEnabled(true);

		this.modelIsModified = false;
		mainModel.setProjectName(dataBase.getName());
		mainView.setTitlePostfix(dataBase);
	}

	/**
	 * Command to save as a project.
	 */
	@ExecCmd(name = CMD_SAVE_AS)
	protected void execSaveAsCommand() {
		LOGGER.info("Command Save AS");
		File dataBase = getDatabase(true);

		StorageEngineType targetFileType = StorageManager
				.getEngineType(dataBase);

		if (dataBase != null) {
			if (storageEngineType == targetFileType) {
				mainModel.getStorageManager().copyTo(dataBase);
				this.modelIsModified = false;
				mainView.setTitlePostfix(dataBase);
			} else {

				File workingCopy = new File(dataBase.getParentFile(), Long
						.toString(System.currentTimeMillis())
						+ Constant.DOT + storageEngineType.name());
				mainModel.getStorageManager().copyTo(workingCopy);
				mainView.removeAllTabs();
				mainModel.stop();
				mainModel.setStorageEngine(null);
				
				StorageEngineConverter converter = new StorageEngineConverter();
				converter.convert(workingCopy, dataBase);
				loadAndInitProject(dataBase);
				workingCopy.delete();
				
				storageEngineType = targetFileType;
				mainView.setTitlePostfix(dataBase);

			}
		}

	} // execSaveCommand

	/**
	 * Command to save the project
	 */
	@ExecCmd(name = CMD_SAVE)
	protected void execSaveCommand() {
		LOGGER.info("Command Save");
		mainModel.getStorageManager().commit();
		this.modelIsModified = false;
	} // execSaveCommand

	/**
	 * Command to show the about dialog
	 */
	@ExecCmd(name = CMD_SHOW_ABOUT_DIALOG)
	protected void execShowAboutDialog() {
		LOGGER.info("Show About Dialog.");
		AboutDialog dialog = new AboutDialog(GestureConstants.ABOUT,
				getLocator().getService(GuiBundleService.IDENTIFIER,
						GuiBundleService.class));
		Point point = mainView.getLocation();
		point.translate(100, 60);
		dialog.setLocation(point);
		dialog.setVisible(true);
	} // execShowAboutDialog
	
	/**
	 * Command to show the device manager.
	 */
	@ExecCmd(name = CMD_SHOW_DEVICE_MANAGER)
	protected void execShowDeviceManager() {
		LOGGER.info("Show Device Manager.");
		Point point = mainView.getLocation();
		point.translate(100, 60);
		deviceManagerController.showView(point);
		//TODO
	}

	/**
	 * Returns the component factory referenced in the locator.
	 * 
	 * @return the component factory referenced in the locator.
	 */
	private ComponentFactory getComponentFactory() {
		return getLocator().getService(ComponentFactory.class.getName(),
				ComponentFactory.class);
	}

	/**
	 * Returns a file handle to the database to be opened.
	 * 
	 * @return file handle to the database to be opened.
	 */
	private File getDatabase(boolean isSaveDialog) {
		File file = null;

		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(FileType.db4oWorkbench.getFilter());
		chooser.addChoosableFileFilter(FileType.xstreamWorkbench.getFilter());
		chooser.setFileFilter(FileType.compressedWorkbench.getFilter());
		chooser.setCurrentDirectory(new File(properties
				.getProperty(Property.WORKING_DIRECTORY)));

		if (isSaveDialog) {
			chooser.showSaveDialog(null);
		} else {
			chooser.showOpenDialog(null);
		}

		file = chooser.getSelectedFile();

		if (file != null) {
			try {
				ExtensionFileFilter fileFilter = (ExtensionFileFilter) chooser
						.getFileFilter();
				if (!fileFilter.accept(file)) {
					file = new File(file.getAbsolutePath() + Constant.DOT
							+ fileFilter.getExtension());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			properties
					.setProperty(Property.WORKING_DIRECTORY, file.getParent());
		}

		return file;
	} // getDatabase

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ximtec.igesture.tool.locator.Service#getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	} // getIdentifier

	@Override
	public TabbedView getView() {
		return null;
	} // getView

	/**
	 * Initialises the controllers and sub views. This method has to be called
	 * in the EDT.
	 * 
	 * @param controllers
	 */
	private void initControllers(Class<?>[] controllers) {
		if (!SwingUtilities.isEventDispatchThread()) {
			throw new RuntimeException("Must not be executed in the EDT.");
		}

		for (Class<?> clazz : controllers) {
			try {
				Controller controller = createController(clazz);
				addController(controller);
				mainView.addTab(controller.getView());
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Could not initialize view. "
						+ clazz.getName(), e);
			}
		}
	}

	/**
	 * Initialises the main view.
	 * <ul>
	 * <li>Add Actions</li>
	 * </ul>
	 */
	private void initMainView() {
		if (mainView == null) {

			addAction(CMD_LOAD, new GenericLocateableAction(this,
					GestureConstants.OPEN_PROJECT, CMD_LOAD));
			addAction(CMD_SAVE, new GenericLocateableAction(this,
					GestureConstants.SAVE, CMD_SAVE));
			addAction(CMD_EXIT, new GenericLocateableAction(this,
					GestureConstants.EXIT, CMD_EXIT));
			addAction(CMD_SHOW_ABOUT_DIALOG, new GenericLocateableAction(this,
					GestureConstants.ABOUT, CMD_SHOW_ABOUT_DIALOG));
			addAction(CMD_CLOSE_WS, new GenericLocateableAction(this,
					GestureConstants.CLOSE_PROJECT, CMD_CLOSE_WS));
			addAction(CMD_SAVE_AS, new GenericLocateableAction(this,
					GestureConstants.SAVE_AS, CMD_SAVE_AS));
			addAction(CMD_SHOW_DEVICE_MANAGER, new GenericLocateableAction(this,
					GestureConstants.DEVICE_MANAGER, CMD_SHOW_DEVICE_MANAGER));
	
			mainView = EdtProxy.newInstance(new MainView(this), IMainView.class);
			mainView.addWindowListener(new MainWindowAdapter(this));
		}
	}

	/**
	 * Initialises the different services. These are the
	 * <ul>
	 * <li>MainModel</li>
	 * <li>Gui Bundle</li>
	 * <li>Gesture Device Client</li>
	 * <li>Component Factory</li>
	 * </ul>
	 * 
	 * After creating the services, they are started.
	 */
	private void initServices() {
		guiBundle = new GuiBundleService(GestureConstants.RESOURCE_BUNDLE);

		properties = new Properties();

		try {
			properties.loadFromXML(new FileInputStream(
					GestureConstants.PROPERTIES));
		} catch (Exception e) {
			// if no properties are available, set default values
			properties.setProperty(Property.WORKING_DIRECTORY, System
					.getProperty(GestureConstants.USER_DIR));
			LOGGER.log(Level.WARNING, "Failed to load properties.");
		}

		mainModel = new MainModel(null, this, properties);
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
	 * Initialises controllers an views connected to the main controller. All
	 * controllers are initialised in the EDT.
	 * 
	 * @param controllers
	 *            An array of controllers. These controllers are initialised.
	 */
	private void initSubControllersAndViews(final Class<?>[] controllers) {

		if (SwingUtilities.isEventDispatchThread()) {
			initControllers(controllers);
		} else {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {

					@Override
					public void run() {
						initControllers(controllers);
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.log(Level.SEVERE, "View Initialization failed. ");
			}
		}
	}

	/**
	 * Handles property change events and persists the changed data model.
	 * 
	 * @param event
	 */
	private void persist(IndexedPropertyChangeEvent event) {
		LOGGER.info("Store, Delete, Update: indexed property "
				+ event.getSource());

		if (event.getOldValue() == null) {
			mainModel.getStorageManager().store(
					(DataObject) event.getNewValue());
		} else if (event.getNewValue() == null
				&& event.getOldValue() instanceof DataObject) {
			mainModel.getStorageManager().remove(
					(DataObject) event.getOldValue());
		}

		mainModel.getStorageManager().update((DataObject) event.getSource());
	} // persist

	/**
	 * Handles property change events and persists the changed data model.
	 * 
	 * @param event
	 */
	private void persist(PropertyChangeEvent event) {
		LOGGER.info("Update: property " + event.getSource());
		mainModel.getStorageManager().update((DataObject) event.getSource());
	} // persist

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ximtec.igesture.tool.core.DefaultController#propertyChange(java.beans
	 * .PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		LOGGER.info("PropertyChange");
		super.propertyChange(event);

		this.modelIsModified = true;

		// Dispatch DataObjects
		if (event.getSource() instanceof DataObject) {

			if (event instanceof IndexedPropertyChangeEvent) {
				persist((IndexedPropertyChangeEvent) event);
			} else {
				persist(event);
			}

		} else if (event.getSource() instanceof DataObjectWrapper) {
			LOGGER.info("DataObjectWrapper: "
					+ event.getSource().getClass().getName());

			if (event.getOldValue() instanceof DataObject
					&& event.getNewValue() == null) {
				mainModel.getStorageManager().remove(
						(DataObject) event.getOldValue());
			} else if (event.getNewValue() instanceof DataObject
					&& event.getOldValue() == null) {
				mainModel.getStorageManager().store(
						(DataObject) event.getNewValue());
			} else if (event.getNewValue() instanceof DataObject
					&& event.getOldValue() != null) {
				mainModel.getStorageManager().update(
						(DataObject) event.getNewValue());
			}
		}
	} // propertyChange

	/**
	 * Shows a Yes/No Dialog
	 * 
	 * @param key
	 *            the key used in the Gui Bundle
	 * @return the return value of the Yes/No Dialog
	 */
	private int showYesNoDialog(String key) {
		String title = getComponentFactory().getGuiBundle().getName(
				GestureConstants.APPLICATION_ROOT);
		String text = Constant.SINGLE_QUOTE + mainModel.getProjectName()
				+ Constant.SINGLE_QUOTE_BLANK
				+ getComponentFactory().getGuiBundle().getShortDescription(key);
		return JOptionPane.showConfirmDialog(null, text, title,
				JOptionPane.YES_NO_CANCEL_OPTION);
	}

}
