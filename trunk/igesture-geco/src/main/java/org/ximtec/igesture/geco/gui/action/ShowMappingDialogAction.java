/*
 * @(#)AddMappingAction.java	1.0   Nov 19, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Map gesture to custom action
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 19, 2007		crocimi		Initial Release
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

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.geco.gui.GecoConstants;
import org.ximtec.igesture.geco.gui.GecoMainView;

/**
*  Map gesture to custom action
* 
* @version 1.0, Nov 2006
* @author Michele Croci, mcroci@gmail.com
*/
public class ShowMappingDialogAction extends BasicAction {

  private GecoMainView mainView;
  private GestureClass currentGesture;
 

  public ShowMappingDialogAction(GecoMainView mainView) {
     super(GecoConstants.MAP_GESTURE, GuiTool.getGuiBundle());
     this.mainView = mainView;
  }


  /**
   * Show a dialog
   * 
   * @param event the action event.
   */
  public void actionPerformed(ActionEvent event) {
     currentGesture = mainView.getSelectedClass();
     mainView.getComponentHandler().getMappingDialog().showDialog(currentGesture);

  } // actionPerformed¨
  
     
     
  }
  
  
 