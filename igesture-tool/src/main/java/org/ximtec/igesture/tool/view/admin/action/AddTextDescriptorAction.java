

package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;


public class AddTextDescriptorAction extends BasicAction {

   private TreePath treePath;


   public AddTextDescriptorAction(TreePath treePath) {
      super(GestureConstants.TEXT_DESCRIPTOR_ADD, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.treePath = treePath;
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      GestureClass gestureClass = (GestureClass)treePath.getLastPathComponent();
      gestureClass.addDescriptor(new TextDescriptor());
   }

}
