/*
 * @(#)GestureMappingTable.java 1.0   Nov 19, 2007
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

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.SplashScreen;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.geco.gui.GecoMainModel;
import org.ximtec.igesture.geco.gui.GecoMainView;
import org.ximtec.igesture.geco.util.GecoSystemTray;
import org.ximtec.igesture.geco.xml.XMLGeco;


/**
 * Main class for the geco application.
 * 
 * @version 0.9, Nov 15, 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GecoMain {

   private static final Logger LOGGER = Logger.getLogger(GecoMain.class
         .getName());

   public static final String GECO_CONFIGURATION = "gecoConfig.xml";

   private static final String GUI_BUNDLE_FILE = "geco";

   private static final String GECO_LOGO = "images/gecoLogo.png";

   private static final String INITIALISING = "Initialising...";

   private static final String INITIALISED = "Initialised.";

   private static final String RUBINE_CONFIGURATION = "rubineconfiguration.xml";

   private Configuration configuration;


   public GecoMain(String[] args) throws AlgorithmException {
      ImageIcon logo = new ImageIcon(GecoMain.class.getClassLoader()
            .getResource(GECO_LOGO));
      SplashScreen splashScreen = new SplashScreen(logo, false, 4000);
      splashScreen.splash();
      GuiTool.addGuiBundle(GUI_BUNDLE_FILE);
      Logger.getAnonymousLogger().setLevel(Level.ALL);
      LOGGER.info(INITIALISING);
      GecoConfiguration gestureConfiguration;

      if (args.length > 0) {
         gestureConfiguration = new GecoConfiguration(args[0]);
      }
      else {
         gestureConfiguration = new GecoConfiguration(GECO_CONFIGURATION);
      }
      // configuration = XMLGeco.importConfiguration(new File(
      // ClassLoader.getSystemResource(RUBINE_CONFIGURATION).getFile()));
      File confFile;
      try {
         confFile = new File(ClassLoader.getSystemResource(RUBINE_CONFIGURATION)
               .toURI());
      }
      catch (URISyntaxException e) {
         confFile = new File(ClassLoader.getSystemResource(RUBINE_CONFIGURATION)
               .getPath());
      }

      configuration = XMLGeco.importConfiguration(confFile);
      GecoMainModel model = new GecoMainModel(configuration,
            gestureConfiguration);
      GecoMainView view = new GecoMainView(model);
      new GecoSystemTray(view);
      LOGGER.info(INITIALISED);
   }


   /**
    * Main method
    * 
    * @param args
    */
   public static void main(String[] args) {
      try {
         new GecoMain(args);
      }
      catch (Exception e) {
         e.printStackTrace();
      }

   }

}
