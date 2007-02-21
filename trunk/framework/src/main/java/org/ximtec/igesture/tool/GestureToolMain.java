/*
 * @(#)GestureToolMain.java    1.0   Nov 15, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   StartUp Class for the Gui Application
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 1.12.2006        ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;


public class GestureToolMain {

   private static final String DEFAULT_CONFIGURATION = "config.xml";

   public static void main(String[] args) {
      Logger.getAnonymousLogger().setLevel(Level.ALL);

      GestureConfiguration configuration;
      if(args.length > 0){
    	  configuration = new GestureConfiguration(args[0]);
      }else{
    	  configuration = new GestureConfiguration(DEFAULT_CONFIGURATION);
      }
      
      StorageEngine engine = StorageManager.createStorageEngine(new File(System
            .getProperty("user.dir")
            + "/" + configuration.getDatabase()));

      GestureMainModel model = new GestureMainModel(engine, configuration);

      new GestureToolView(model, configuration);
   }
}
