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
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;


public class AddGestureClassAction extends TreePathAction {

   private static final String NEW_GESTURE_CLASS = "New Gesture Class";


   public AddGestureClassAction(Controller controller, TreePath treePath) {
      super(GestureConstants.GESTURE_CLASS_ADD, controller, treePath);
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      GestureSet gestureSet = (GestureSet)getTreePath().getLastPathComponent();
      
      // find a unique name for the new gesture class
      String gestureClassName = NEW_GESTURE_CLASS;
      int counter = 2;
      while(gestureSet.getGestureClass(gestureClassName) != null){
         gestureClassName = NEW_GESTURE_CLASS+counter;
         counter++;
      }
      
      // create the gesture class and add it to the gesture set
      GestureClass gestureClass = new GestureClass(gestureClassName);
      gestureSet.addGestureClass(gestureClass);
      
   }

}
