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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

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
 * @version 0.9, Nov 15, 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Geco {

   private static final Logger LOGGER = Logger.getLogger(Geco.class.getName());

   public static final String GECO_CONFIGURATION = "\\config\\gecoConfig.xml";

   private static final String GUI_BUNDLE_FILE = "geco";

   private static final String GECO_LOGO = "images/gecoLogo.png";

   private static final String INITIALISING = "Initialising...";

   private static final String INITIALISED = "Initialised.";

   private static final String RUBINE_CONFIGURATION = "\\config\\rubineconfiguration.xml";
   
   public static final String LAST_PROJECT = "\\config\\lastProject.txt";
   
   private static final String DEFAULT_PROJECT = "\\Mappings\\ms_gestures_mapping.xml";

   private org.ximtec.igesture.configuration.Configuration configuration;
   
   private Configuration gestureConfiguration;
   
   private MainView view;


   public Geco(String[] args) throws AlgorithmException {
      ImageIcon logo = new ImageIcon(Geco.class.getClassLoader().getResource(
            GECO_LOGO));
      SplashScreen splashScreen = new SplashScreen(logo, false, 4000);
      splashScreen.splash();
      GuiTool.addGuiBundle(GUI_BUNDLE_FILE);
      Logger.getAnonymousLogger().setLevel(Level.ALL);
      LOGGER.info(INITIALISING);


      if (args.length > 0) {
         gestureConfiguration = new Configuration(args[0]);
      }
      else {
         gestureConfiguration = new Configuration(GECO_CONFIGURATION);
      }
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
      MainModel model = new MainModel(configuration, gestureConfiguration);
      view = new MainView(model);
      //open last opened Document
      openLastProject();
      new SystemTray(view);
      LOGGER.info(INITIALISED);
   }


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
   
   public void openLastProject(){ 
      String file = "";
      try
      {
            File confFile;
            try {
               confFile = new File(ClassLoader.getSystemResource(LAST_PROJECT).toURI());
            }
            catch (URISyntaxException e) {
               confFile = new File(ClassLoader.getSystemResource(LAST_PROJECT)
                     .getPath());
            }

            FileReader fr = new FileReader(confFile);  
             file = new BufferedReader(fr).readLine();
             fr.close();        
         }
         catch (IOException e)
         {
             e.printStackTrace();
         }
         if ((file==null)||file.equals("")){
               try {
                  file = ClassLoader.getSystemResource(DEFAULT_PROJECT).toURI().getPath();
               }
               catch (URISyntaxException e) {
                  file = ClassLoader.getSystemResource(DEFAULT_PROJECT).getPath();
               }
         }
         (new OpenProjectAction(view)).openProject(new File(file));
   }

}
