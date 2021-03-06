/*
 * @(#)$Id$
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Some constants for the geco application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2007     crocimi     Initial Release
 * Jan 20, 2008     bsigner     Externalised all strings.
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.util;

/**
 * Some constants for the geco application.
 * 
 * @version 0.9, Nov 15, 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class Constant extends org.sigtec.util.Constant {

   public static final String INITIALISING = GuiBundleTool.getBundle()
         .getString("StringInitialising");

   public static final String INITIALISED = GuiBundleTool.getBundle().getString(
         "StringInitialised");

   public static final String PROJECT_PROPERTIES_TEXT_FIELD = "ProjectPropertiesTextField";

   public static final String PROJECT_PROPERTIES_STRING = "ProjectPropertiesString";

   public static final String CLOSE_ABOUT_BUTTON = "CloseAboutButton";
   public static final String CREATE_PROJECT_BUTTON = "CreateProjectButton";

   public static final String HELP_MENU = "HelpMenu";

   public static final String SHOW_APPLICATION_MENU_ITEM = "ShowApplicationMenuItem";

   public static final String SETTINGS = "Settings";

   public static final String SETTINGS_ATTRIBUTE_MAPPINGS = "MappingsLocation";

   /*
    * GUI
    */

   // public static final String GECO_TITLE = "Geco";
   public static final String FILE_MENU = "FileMenu";

   public static final String GESTURE_MAPPING_TITLE = "GestureMappingTitle";

   public static final String USER_DEFINED_MAPPING = "User-defined mappings";

   public static final String GESTURE_SET = "Gesture set";

   public static final String GESTURE = "Gesture";

   public static final String HOTKEY = "Hotkey";

   public static final String COMMAND = "Command";

   public static final String OPEN_FILE = "OpenFile";

   public static final String SAVE_DIALOG_TITLE = "Would you like to save the changes before closing?";

   public static final String OVERWRITE_FILE = "File already exists. Do you want to overwrite it?";

   public static final String LOAD_GESTURE_SET_CONFIRMATION = "Loading another gesture set will delete all existing mapping. Do you want to continue?";

   public static final String SELECT_INPUT_DEVICE = "Select input device";

   public static final String PROJECT_NAME = "ProjectName";

   public static final String PROJECT_LOCATION = "ProjectLocation";

   public static final String PROJECT_FILE_TEXT_FIELD = "ProjectNameTextField";

   public static final String INPUT_DEVICE = "Input Device";

   public static final String STARTUP = "Startup";

   public static final String MINIMIZE_STARTUP = "Minimize at Startup";

   public static final String GESTURE_SET_NOT_FOUND = "Gesture set file not found! \n Project could not be loaded";

   /*
    * Commands
    */

   public static final String SAVE = "SAVE";

   public static final String ADD = "ADD";

   public static final String CANCEL = "CANCEL";

   public static final String EXIT = "EXIT";

   public static final String EDIT = "EDIT";

   public static final String BROWSE = "BROWSE";

}
