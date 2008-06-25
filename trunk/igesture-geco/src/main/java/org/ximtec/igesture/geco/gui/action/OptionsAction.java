/*
 * @(#)OptionsAction.java	1.0   Jan 10, 2008
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Action to show options.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Jan 10, 2008		crocimi		Initial Release
 * Jan 20, 2008     bsigner     Redesign
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
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.util.GuiBundleTool;


/**
 * Action to show options.
 * @version 0.9, Dec 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */

public class OptionsAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "OptionsAction";

   private MainView mainView;


   public OptionsAction(MainView mainView) {
      super(KEY, GuiBundleTool.getBundle());
      this.mainView = mainView;
   }


   /**
    * Shows the options dialog.
    * @param event the event to be handled.
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      mainView.getComponentHandler().getOptionsDialog().showDialog();
   } // actionPerformed

}
