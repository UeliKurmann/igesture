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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;
import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;


public class AddGestureSetAction extends TreePathAction {

   private static final String NEW_GESTURE_SET = "New Gesture Set";


   public AddGestureSetAction(Controller controller, TreePath treePath) {
      super(GestureConstants.GESTURE_SET_ADD, controller, treePath);
   }


   @Override
   public void actionPerformed(ActionEvent event) {
      GestureSetList gestureSets = (GestureSetList)getTreePath().getLastPathComponent();
      GestureSet gestureSet = new GestureSet();
      gestureSet.setName(NEW_GESTURE_SET);
      gestureSets.addGestureSet(gestureSet);
   }

}
