/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.action;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.LocateableAction;
import org.ximtec.igesture.tool.view.MainController;

public class StoreWorkspaceAction extends LocateableAction {

  private Controller controller;

  public StoreWorkspaceAction(Controller controller) {
    super(GestureConstants.APPLICATION_SAVE, controller.getLocator());
    this.controller = controller;

  }

  public StoreWorkspaceAction(TreePath treePath, Controller controller) {
    super(GestureConstants.GESTURE_CLASS_ADD, controller.getLocator());
    this.controller = controller;

  }

  @Override
  public void actionPerformed(ActionEvent event) {
    controller.execute(new Command(MainController.CMD_SAVE, event));

  }
}
