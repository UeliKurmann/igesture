/*
 * @(#)EditMappingAction.java    1.0   Nov 19, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Map gesture to custom action
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 19, 2007     crocimi     Initial Release
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
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.geco.util.Constant;
import org.ximtec.igesture.geco.util.GuiBundleTool;


/**
 * Map gesture to custom action
 * 
 * @version 1.0, Nov 2006
 * @author Michele Croci, mcroci@gmail.com
 */
public class EditMappingAction extends BasicAction {

   private MainView mainView;
   private GestureToActionMapping currentMapping;


   public EditMappingAction(MainView mainView) {
      super(Constant.EDIT, GuiBundleTool.getBundle());
      this.mainView = mainView;
   }


   /**
    * Show a dialog
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      currentMapping = mainView.getSelectedMapping();
      mainView.getComponentHandler().getMappingDialog().showDialog(
            currentMapping.getGestureClass());
   } // actionPerformed

}
