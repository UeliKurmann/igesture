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
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

	private static final String PROPERTIES = "properties.xml";

	public static final String CMD_LOAD = "load";
	public static final String CMD_CLOSE = "close";
	public static final String CMD_SAVE = "save";
	public static final String CMD_START_WAITING = "startWaiting";
	public static final String CMD_STOP_WAITING = "stopWaiting";
	public static final String CMD_SHOW_ABOUT_DIALOG = "showAboutDialog";

	private static Class<?>[] controllers = new Class<?>[] {
			WelcomeController.class, AdminController.class,
			TestbenchController.class, BatchController.class,
			TestSetController.class };

	public static final String IDENTIFIER = "mainController";

	private static final String RESOURCE_BUNDLE = "igestureMenu";

	// Service Locator
	private Locator locator;

	// Services
	private MainModel mainModel;
	private GuiBundleService guiBundle;
	private SwingMouseReaderService deviceClient;

	// Main View
	private MainView mainView;

	private Properties properties;

	public MainController() {
		initServices();
		initViews();
	}

	private void initServices() {
		guiBundle = new GuiBundleService(RESOURCE_BUNDLE);

		properties = new Properties();

		try {
			properties.loadFromXML(new FileInputStream(PROPERTIES));
		} catch (Exception e) {
			// if no properties are available, set default values
			properties.setProperty(Property.WORKING_DIRECTORY, System
					.getProperty("user.dir"));
			LOGGER.log(Level.WARNING, "Failed to load properties.");
		}

		File database = null;
		while (database == null) {
			database = getDatabase();
		}

		mainModel = new MainModel(StorageManager.createStorageEngine(database),
				this, properties);
		deviceClient = new SwingMouseReaderService();

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
	 * Initialises the views
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

				for (Class<?> clazz : controllers) {
					try {
						Controller controller = (Controller) clazz
								.newInstance();
						addController(controller);
						mainView.addTab(controller.getView());
					} catch (Exception e) {
						LOGGER.log(Level.SEVERE, "Could not initialize view. "
								+ clazz.getName());
					}
				}

				try {
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

	@ExecCmd(name = CMD_LOAD)
	protected void execLoadCommand() {
		LOGGER.info("Command Load");
		File dataBase = getDatabase();

		if (dataBase != null) {
			mainView.removeAllTabs();
			mainModel.stop();
			mainModel.setStorageEngine(StorageManager
					.createStorageEngine(dataBase));
			mainModel.start();
			initViews();
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

		String title = ComponentFactory.getGuiBundle().getName(
				GestureConstants.MAIN_CONTROLLER_DIALOG_EXIT);

		String text = ComponentFactory.getGuiBundle().getShortDescription(
				GestureConstants.MAIN_CONTROLLER_DIALOG_EXIT);

		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, text,
				title, JOptionPane.YES_NO_OPTION)) {
			mainModel.getStorageManager().commit();
			Locator.getDefault().stopAll();

			try {
				mainModel.getProperties().storeToXML(
						new FileOutputStream(PROPERTIES),
						"iGesture: " + new Date());
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Failed to store properties.", e);
			}
			System.exit(0);
		}

	} // execCloseCommand

	protected void execStartWaiting() {
		LOGGER.info("Start Progress Panel.");
	} // execStartWaiting

	protected void execStopWaiting() {
		LOGGER.info("Stop Progress Panel.");
	} // execStopWaiting

	@ExecCmd(name = CMD_SHOW_ABOUT_DIALOG)
	protected void execShowAboutDialog() {
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
				persist((IndexedPropertyChangeEvent) event);
			} else {
				persist((PropertyChangeEvent) event);
			}

		} else if (event.getSource() instanceof DataObjectWrapper) {
			LOGGER.info("DataObjectWrapper");

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

	private void persist(PropertyChangeEvent event) {
		LOGGER.info("Update: property " + event.getSource());
		mainModel.getStorageManager().update((DataObject) event.getSource());
	} // persist

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
		chooser.setCurrentDirectory(new File(properties
				.getProperty(Property.WORKING_DIRECTORY)));
		chooser.showOpenDialog(null);
		file = chooser.getSelectedFile();
		
		try{
			ExtensionFileFilter fileFilter = (ExtensionFileFilter)chooser.getFileFilter();
			if(!fileFilter.accept(file)){
				file = new File(file.getAbsolutePath()+"."+fileFilter.getExtension());
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		if (file != null) {
			properties
					.setProperty(Property.WORKING_DIRECTORY, file.getParent());
		}

		return file;
	} // getDatabase

	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	} // getIdentifier

}
