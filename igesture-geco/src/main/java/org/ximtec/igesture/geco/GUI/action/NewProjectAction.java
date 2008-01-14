/*
 * @(#)NewProjectAction.java	1.0   Nov 15, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Action for creating a new gesture mapping project.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 	Nov 15, 2007	crocimi		Initial Release
 *  Jan 14, 2008    bsigner     Cleanup
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

import javax.swing.JOptionPane;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.Constant;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;


/**
 * Action for creating a new gesture mapping project.
 * 
 * @version 0.9, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class NewProjectAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "NewProjectAction";

   private GecoMainView mainView;


   public NewProjectAction(GecoMainView mainView) {
      super(KEY, GuiTool.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Creates a new mapping.
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      // display save dialog, if needed

      if (mainView.getModel().needSave()) {
         int n = JOptionPane.showConfirmDialog(mainView,
               GecoConstants.SAVE_DIALOG_TITLE, Constant.EMPTY_STRING,
               JOptionPane.YES_NO_OPTION);

         if (n == 0) {
            (new SaveProjectAction(mainView)).save();
         }

      }

      // display new project dialog
      mainView.getComponentHandler().getNewProjectDialog().showDialog();
   } // actionPerformed

}
