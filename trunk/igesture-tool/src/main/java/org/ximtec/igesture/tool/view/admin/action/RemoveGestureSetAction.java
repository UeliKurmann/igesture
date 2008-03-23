

package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.RootSet;


public class RemoveGestureSetAction extends BasicAction {

   private TreePath treePath;


   public RemoveGestureSetAction(TreePath treePath) {
      super(GestureConstants.GESTURE_SET_DEL, Locator.getDefault().getService(
            GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.treePath = treePath;
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      GestureSet gestureSet = (GestureSet)treePath.getLastPathComponent();
      RootSet rootSet = (RootSet)treePath.getParentPath().getLastPathComponent();
      rootSet.removeGestureSet(gestureSet);

   }

}
