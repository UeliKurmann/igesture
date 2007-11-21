/*
 * @(#)ExtensionFileFilter.java 1.0   Nov 15, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Main class for the mapping gesture application
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2007     crocimi     Initial Release
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

import org.sigtec.util.Constant;
import org.sigtec.util.SystemProperty;
import org.ximtec.igesture.geco.GUI.GestureMappingModel;
import org.ximtec.igesture.geco.GUI.GestureMappingView;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.GestureConfiguration;
import org.ximtec.igesture.tool.GestureMainModel;
import org.ximtec.igesture.tool.GestureToolMain;
import org.ximtec.igesture.tool.GestureToolView;



/**
 *  Main class for the mapping gesture application
 * 
 * @version 1.0 Nov 15, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class GestureMappingMain {
   
   private static final Logger LOGGER = Logger.getLogger(GestureMappingMain.class
         .getName());

   private static final String DEFAULT_CONFIGURATION = "config.xml";


	/**
	 * @param args
	 */
	public static void main(String[] args) {
	   
	        Logger.getAnonymousLogger().setLevel(Level.ALL);
	        Logger.getAnonymousLogger().setLevel(Level.ALL);
	        GestureConfiguration configuration;


	      if (args.length > 0) {
	         configuration = new GestureConfiguration(args[0]);
	      }
	      else {
	         configuration = new GestureConfiguration(DEFAULT_CONFIGURATION);
	      }

	      String file = System.getProperty(SystemProperty.USER_DIR) + Constant.SLASH
	            + configuration.getDatabase();
	            
	      StorageEngine engine = StorageManager.createStorageEngine(new File(file));
	      GestureMappingModel model = new GestureMappingModel(engine);
	      GestureMappingView view = new GestureMappingView(model);

	}
	


}
