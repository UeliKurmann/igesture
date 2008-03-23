

package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;


public class RemoveDescriptorAction extends BasicAction {

   private TreePath treePath;


   public RemoveDescriptorAction(TreePath treePath) {
      super(GestureConstants.DESCRIPTOR_DEL, Locator.getDefault().getService(
            GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.treePath = treePath;
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      Descriptor descriptor = (Descriptor)treePath.getLastPathComponent();
      GestureClass gestureClass = (GestureClass)treePath.getParentPath()
            .getLastPathComponent();
      gestureClass.removeDescriptor(descriptor.getClass());
   }

}
