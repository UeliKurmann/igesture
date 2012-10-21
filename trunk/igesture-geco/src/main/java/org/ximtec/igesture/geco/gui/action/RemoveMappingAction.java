/*
 * @(#)$Id$
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Removes gesture-action mapping.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 26, 2007		crocimi		Initial Release
 * Jan 15, 2008     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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
 * Removes gesture-action mapping.
 * @version 0.9, Nov 26, 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class RemoveMappingAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "RemoveMappingAction";

   private MainView mainView;


   public RemoveMappingAction(MainView mainView) {
      super(KEY, GuiBundleTool.getBundle());
      this.mainView = mainView;
   }


   /**
    * Removes the mapping.
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      // update model
      mainView.getModel().removeMapping(mainView.getSelectedMapping());
      // update view
      mainView.updateLists();
      mainView.enableSaveButton();
   } // actionPerformed

}
