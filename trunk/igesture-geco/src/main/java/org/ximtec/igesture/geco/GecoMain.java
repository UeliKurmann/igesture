/*
 * @(#)GestureMappingTable.java 1.0   Nov 19, 2007
 *
 * Author       :   Michele Croci, mcroci@mgail.com
 *
 * Purpose      :  Main class for the geco application
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JLabel;

import org.sigtec.graphix.GuiTool;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.geco.GUI.GecoMainModel;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.util.GecoSystemTray;
import org.ximtec.igesture.geco.xml.XMLGeco;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.tool.GestureConfiguration;




/**
 *  Main class for the geco application
 * 
 * @version 1.0 Nov 15, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class GecoMain  {
   
   private static final Logger LOGGER = Logger.getLogger(GecoMain.class
         .getName());

   private static final String DEFAULT_CONFIGURATION = "config.xml";
   
   private static final String GUI_BUNDLE_FILE = "gecoProperties";


    /**
     * Main method
     * 
     * @param args
     */
    public static void main(String[] args) {
       /*
       System.out.println("Starting Geco...");
       JDialog dialog = new JDialog();
       dialog.add(new JLabel("Hello"));
       dialog.setVisible(true);
       dialog.setSize(100,100);
       dialog.setLocation(100,100);
       */
       try{
          new GecoMain(args);
       }
       catch(Exception e){
          e.printStackTrace();
       }

    }

       private static final String INITIALISING = "Initialising...";

       private static final String INITIALISED = "Initialised.";

       private static final String RUBINE_CONFIGURATION = "rubineconfiguration.xml";

       private InputDeviceClient client;
       
       private Configuration configuration;


       

       public GecoMain(String[] args) throws AlgorithmException {
             
          
          
              GuiTool.addGuiBundle(GUI_BUNDLE_FILE);
              Logger.getAnonymousLogger().setLevel(Level.ALL);
              LOGGER.info(INITIALISING);
              
              
              GestureConfiguration conf;
              
              if (args.length > 0) {
                 conf = new GestureConfiguration(args[0]);

              }
              else {
                 conf = new GestureConfiguration(DEFAULT_CONFIGURATION);
              }
              configuration = XMLGeco.importConfiguration(new File(
                    ClassLoader.getSystemResource(RUBINE_CONFIGURATION).getFile()));
              
              client = new InputDeviceClient(conf.getInputDevice(),
                    conf.getInputDeviceEventListener());
              
              GecoMainModel model = new GecoMainModel(configuration, client);
              GecoMainView view = new GecoMainView(model);
              initDevice(conf);
              //add icon to tray
              new GecoSystemTray(view);
              LOGGER.info(INITIALISED);
       }


       
       /**
        * Init the input device
        */
       private void initDevice(GestureConfiguration configuration) {
          client = new InputDeviceClient(configuration.getInputDevice(),
                configuration.getInputDeviceEventListener());
       } // initDevice



       
}
