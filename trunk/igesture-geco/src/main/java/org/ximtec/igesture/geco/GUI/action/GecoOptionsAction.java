/*
 * @(#)GecoOptionsAction.java	1.0   Jan 10, 2008
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Action for showing preferences
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Jan 10, 2008		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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
import org.ximtec.igesture.geco.GUI.GecoMainView;



/**
 * Action for showing preferences
 * @version 1.0 Jan 10, 2008
 * @author Michele Croci, mcroci@gmail.com
 */

public class GecoOptionsAction extends BasicAction {

   private GecoMainView mainView;


   public GecoOptionsAction(GecoMainView mainView) {
      super(GecoConstants.OPTIONS_ACTION, GuiTool.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Creates a new mapping
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      //display save dialog, if needed
     
      
      //display new project dialog
      mainView.getComponentHandler().getOptionsDialog().showDialog();
   } // actionPerformed
   

   
}

