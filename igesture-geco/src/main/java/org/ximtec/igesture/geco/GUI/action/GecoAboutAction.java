/*
 * @(#)GecoAboutAction.java   1.0   Dec 18, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   About dialog.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 18, 2007     crocimi    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.GUI.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.tool.util.JAboutDialog;


/**
 * About dialog.
 * 
 * @version 1.0, Nov 2006
 * @author Michele Croci, mcroci@gmail.com
 */
public class GecoAboutAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "GecoAboutAction";

   JAboutDialog dialog;


   public GecoAboutAction(String path) {
      super(GecoConstants.GECO_ABOUT_ACTION, GuiTool.getGuiBundle());
      dialog = new JAboutDialog(300, 500, GecoAboutAction.class
            .getClassLoader().getResource(path));
   }


   /**
    * Makes the dialogue visible.
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      dialog.setLocation(200,200);
      dialog.setVisible(true);
   } // actionPerformed

}
