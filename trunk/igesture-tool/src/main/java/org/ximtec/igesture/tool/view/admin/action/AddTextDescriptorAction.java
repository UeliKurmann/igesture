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

package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;


public class AddTextDescriptorAction extends TreePathAction {


   public AddTextDescriptorAction(Controller controller, TreePath treePath) {
      super(GestureConstants.TEXT_DESCRIPTOR_ADD, controller, treePath);
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      GestureClass gestureClass = (GestureClass)getTreePath().getLastPathComponent();
      gestureClass.addDescriptor(new TextDescriptor());
   }

}
