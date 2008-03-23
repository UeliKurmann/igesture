/*
 * @(#)Geco.java 1.0   Nov 19, 2007
 *
 * Author       :   Michele Croci, mcroci@mgail.com
 *
 * Purpose      :   Main class for the geco application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 19, 2007     crocimi     Initial Release
 * Jan 20, 2008     bsigner     Cleanup and new GuiBundle functionality
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco;

import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.sigtec.graphix.GuiBundle;
import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.SplashScreen;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.geco.gui.MainModel;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.gui.action.OpenProjectAction;
import org.ximtec.igesture.geco.util.SystemTray;
import org.ximtec.igesture.geco.xml.XMLGeco;


/**
 * Main class for the geco application.
 * 
 * @version 0.9, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Geco {

   /**
    * The key used to retrieve information from the resource bundle.
    */
   public static final String KEY = "Geco";

   private static final Logger LOGGER = Logger.getLogger(Geco.class.getName());

   public static final String GECO_CONFIGURATION = "\\config\\geco.xml";

   private static final String GUI_BUNDLE_NAME = "geco";

   private static final String GUI_BUNDLE_FILE = "geco";

   private static final String GECO_LOGO = "images/gecoLogo.png";

   private static final String INITIALISING = "Initialising...";

   private static final String INITIALISED = "Initialised.";

   private static final String RUBINE_CONFIGURATION = "\\config\\rubineconfiguration.xml";

   public static final String LAST_PROJECT_FILE = "lastProject.txt";

   public static final String CONFIG = "config";

   private static final String DEFAULT_PROJECT = "\\mappings\\defaultMappings.xml";

   private org.ximtec.igesture.configuration.Configuration configuration;

   private Configuration gestureConfiguration;

   private MainView view;

   private File configFile;


   public Geco(String[] args) throws AlgorithmException {
      ImageIcon logo = new ImageIcon(Geco.class.getClassLoader().getResource(
            GECO_LOGO));
      SplashScreen splashScreen = new SplashScreen(logo, false, 4000);
      splashScreen.splash();
      GuiTool.addBundle(GUI_BUNDLE_NAME, GUI_BUNDLE_FILE);
      Logger.getAnonymousLogger().setLevel(Level.INFO);
      LOGGER.info(INITIALISING);

      if (args.length > 0) {
         gestureConfiguration = new Configuration(args[0]);
      }
      else {
         gestureConfiguration = new Configuration(GECO_CONFIGURATION);
      }

      try {
         configFile = new File(ClassLoader.getSystemResource(
               RUBINE_CONFIGURATION).toURI());
      }
      catch (URISyntaxException e) {
         configFile = new File(ClassLoader.getSystemResource(
               RUBINE_CONFIGURATION).getPath());
      }

      configuration = XMLGeco.importConfiguration(configFile);
      MainModel model = new MainModel(configuration, gestureConfiguration);
      view = new MainView(model);
      // open last opened Document
      openLastProject();
      new SystemTray(view);
      LOGGER.info(INITIALISED);
   }


   public static final GuiBundle getGuiBundle() {
      return GuiTool.getBundle(GUI_BUNDLE_NAME);
   } // getGuiBundle


   /**
    * Main method
    * 
    * @param args
    */
   public static void main(String[] args) {
      try {
         new Geco(args);
      }
      catch (Exception e) {
         e.printStackTrace();
      }

   }


   public void openLastProject() {
      String file = view.getModel().getGestureConfiguration().getLastProject();
      if ((file == null) || (file.equals(""))) {
         try {
            file = ClassLoader.getSystemResource(DEFAULT_PROJECT).toURI()
                  .getPath();
         }
         catch (URISyntaxException e) {
            file = ClassLoader.getSystemResource(DEFAULT_PROJECT).getPath();
         }
      }
      (new OpenProjectAction(view)).openProject(new File(file));
   }

}
