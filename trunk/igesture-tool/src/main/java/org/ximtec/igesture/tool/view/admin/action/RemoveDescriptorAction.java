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

import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;


public class RemoveDescriptorAction extends TreePathAction {


   public RemoveDescriptorAction(Controller controller, TreePath treePath) {
      super(GestureConstants.DESCRIPTOR_DEL, controller, treePath);
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      Descriptor descriptor = (Descriptor)getTreePath().getLastPathComponent();
      GestureClass gestureClass = (GestureClass)getTreePath().getParentPath()
            .getLastPathComponent();
      gestureClass.removeDescriptor(descriptor.getClass());
   }

}
