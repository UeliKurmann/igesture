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
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.geco.GestureMappingTable;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GestureMappingView;
import org.ximtec.igesture.geco.dialog.MappingDialog;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;

/**
*  Map gesture to custom action
* 
* @version 1.0, Nov 2006
* @author Michele Croci, mcroci@gmail.com
*/
public class EditMappingAction extends BasicAction {

  private GestureMappingView mainView;
  private GestureToActionMapping currentMapping;
  

  private MappingDialog dialog;
 

  public EditMappingAction(GestureMappingView mainView) {
     super(GecoConstants.EDIT, GuiTool.getGuiBundle());
     this.mainView = mainView;
  }


  /**
   * Show a dialog
   * 
   * @param event the action event.
   */
  public void actionPerformed(ActionEvent event) {
     currentMapping = mainView.getSelectedMapping();
     //dialog.show(true);
     if (dialog==null)
        dialog = new MappingDialog(mainView);
     dialog.showDialog(currentMapping.getGestureClass());

  } // actionPerformed¨
  
     
     
  }
  
  
 