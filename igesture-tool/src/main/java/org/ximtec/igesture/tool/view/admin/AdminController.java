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

package org.ximtec.igesture.tool.view.admin;

import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import javax.swing.tree.TreePath;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.ExecCmd;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.core.TreePathAction;
import org.ximtec.igesture.tool.explorer.ExplorerTreeController;
import org.ximtec.igesture.tool.explorer.ExplorerTreeModel;
import org.ximtec.igesture.tool.util.NodeInfoFactory;
import org.ximtec.igesture.tool.view.MainModel;

public class AdminController extends DefaultController {

  

  private static final Logger LOGGER = Logger.getLogger(AdminController.class.getName());

  private static final String NEW_GESTURE_CLASS = "New Gesture Class";
  
  public static final String ADD_GESTURE_CLASS_CMD = "addGestureClassCmd";

  /**
   * the view of this controller
   */
  private AdminView adminView;

  /**
   * Reference to the main model.
   */
  private MainModel mainModel;

  /**
   * Explorer Tree Controller
   */
  private ExplorerTreeController explorerTreeController;

  public AdminController(Controller parentController) {
    super(parentController);
    mainModel = getLocator().getService(MainModel.IDENTIFIER, MainModel.class);
    initController();

  }

  /**
   * Initializes the controller.
   */
  private void initController() {
    adminView = new AdminView(this);

    ExplorerTreeModel explorerModel = new ExplorerTreeModel(mainModel.getGestureSetList(), NodeInfoFactory
        .createAdminNodeInfo(this));
    explorerTreeController = new ExplorerTreeController(this, adminView, explorerModel);

    addController(explorerTreeController);
  }

  @Override
  public TabbedView getView() {
    return adminView;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    LOGGER.info("PropertyChange");
    super.propertyChange(evt);

    explorerTreeController.getExplorerTreeView().refresh();
  }

  @ExecCmd(name = ADD_GESTURE_CLASS_CMD)
  public void cmdAddGestureClass(Command command) {
    GestureSet gestureSet = (GestureSet) getTreePath(command).getLastPathComponent();

    // find a unique name for the new gesture class
    String gestureClassName = NEW_GESTURE_CLASS;
    int counter = 2;
    while (gestureSet.getGestureClass(gestureClassName) != null) {
      gestureClassName = NEW_GESTURE_CLASS + counter;
      counter++;
    }

    // create the gesture class and add it to the gesture set
    GestureClass gestureClass = new GestureClass(gestureClassName);
    gestureSet.addGestureClass(gestureClass);
  }

  private TreePath getTreePath(Command command) {
    // FIXME null pointer checks, throw exceptions
    TreePathAction tpa = (TreePathAction) command.getSender();
    return tpa.getTreePath();
  }
}
