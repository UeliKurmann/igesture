/*
 * @(#)$Id$
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.sigtec.graphix.SplashScreen;
import org.sigtec.util.FileHandler;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.geco.gui.MainModel;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.gui.action.OpenProjectAction;
import org.ximtec.igesture.geco.util.Constant;
import org.ximtec.igesture.geco.util.SystemTray;
import org.ximtec.igesture.util.XMLTool;


/**
 * Main class for the geco application.
 * 
 * @version 0.9, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class Geco {

   private static final Logger LOGGER = Logger.getLogger(Geco.class.getName());

   public static final String GECO_CONFIGURATION = "config/geco.xml";

   private static final String DEFAULT_PROJECT = "mappings/defaultMappings.xml";

   private static final String GECO_LOGO = "images/gecoLogo.png";

   private static final String RUBINE_CONFIGURATION = "config/rubineconfiguration.xml";

   public static final String CONFIG = "config";

   private org.ximtec.igesture.configuration.Configuration configuration;

   private Configuration gecoConfiguration;

   private MainView view;


   public Geco(String[] args) throws AlgorithmException {
      ImageIcon logo = new ImageIcon(Geco.class.getClassLoader().getResource(
            GECO_LOGO));
      SplashScreen splashScreen = new SplashScreen(logo, false, 4000);
      splashScreen.splash();
      Logger.getAnonymousLogger().setLevel(Level.INFO);
      LOGGER.info(Constant.INITIALISING);

      if (args.length > 0) {
         gecoConfiguration = new Configuration(args[0]);
      }
      else {
         gecoConfiguration = new Configuration(GECO_CONFIGURATION);
      }

      configuration = XMLTool.importConfiguration(ClassLoader
            .getSystemResourceAsStream(RUBINE_CONFIGURATION));
      MainModel model = new MainModel(configuration, gecoConfiguration);
      view = new MainView(model);
      openMostRecentProject();
      new SystemTray(view);
      LOGGER.info(Constant.INITIALISED);
   }


   /**
    * Main method
    * 
    * @param args
    */
   public static void main(String[] args) {
	   try {
    	  /* see http://java.sun.com/docs/books/tutorial/uiswing/lookandfeel/nimbus.html */
    	  for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    	        if ("Nimbus".equals(info.getName())) {
    	            UIManager.setLookAndFeel(info.getClassName());
    	            break;
    	        }
    	    }
      }
      catch (Exception e) {
         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         }
         catch (Exception e1) {
            e1.printStackTrace();
         }
      }
      try {
         new Geco(args);
      }
      catch (Exception e) {
         e.printStackTrace();
      }

   }


   private void openMostRecentProject() {
      File file = null;
      String filename = view.getModel().getGestureConfiguration()
            .getMostRecentProject();

      if ((filename == null) || (filename.equals(Constant.EMPTY_STRING))) {
         file = FileHandler.getResource(DEFAULT_PROJECT);
      }

      (new OpenProjectAction(view)).openProject(file);
   } // openMostRecentProject

}
