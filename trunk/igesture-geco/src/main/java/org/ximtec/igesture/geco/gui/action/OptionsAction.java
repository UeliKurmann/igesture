/*
 * @(#)OptionsAction.java	1.0   Jan 10, 2008
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


package org.ximtec.igesture.geco.gui.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.geco.Geco;
import org.ximtec.igesture.geco.gui.Constant;
import org.ximtec.igesture.geco.gui.MainView;


/**
 * Action for showing preferences
 * @version 0.9, Jan 10, 2008
 * @author Michele Croci, mcroci@gmail.com
 */

public class OptionsAction extends BasicAction {

   private MainView mainView;


   public OptionsAction(MainView mainView) {
      super(Constant.OPTIONS_ACTION, Geco.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Creates a new mapping
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      // display save dialog, if needed

      // display new project dialog
      mainView.getComponentHandler().getOptionsDialog().showDialog();
   } // actionPerformed

}
