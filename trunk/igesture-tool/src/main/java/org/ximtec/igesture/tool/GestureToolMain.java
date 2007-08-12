/*
 * @(#)GestureToolMain.java    1.0   Nov 15, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Startup class for the GUI application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.util.Constant;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;


/**
 * Startup class for the GUI application.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureToolMain {

   private static final String DEFAULT_CONFIGURATION = "config.xml";

   private static final String PROPERTY_USER_DIR = "user.dir";


   public static void main(String[] args) {
      Logger.getAnonymousLogger().setLevel(Level.ALL);
      GestureConfiguration configuration;

      if (args.length > 0) {
         configuration = new GestureConfiguration(args[0]);
      }
      else {
         configuration = new GestureConfiguration(DEFAULT_CONFIGURATION);
      }

      StorageEngine engine = StorageManager.createStorageEngine(new File(System
            .getProperty(PROPERTY_USER_DIR)
            + Constant.SLASH + configuration.getDatabase()));

      GestureMainModel model = new GestureMainModel(engine, configuration);
      new GestureToolView(model, configuration);
   }

}