

package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;


public class AddSampleDescriptorAction extends BasicAction {

   private TreePath treePath;


   public AddSampleDescriptorAction(TreePath treePath) {
      super(GestureConstants.SAMPLE_DESCRIPTOR_ADD, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.treePath = treePath;
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      GestureClass gestureClass = (GestureClass)treePath.getLastPathComponent();
      gestureClass.addDescriptor(new SampleDescriptor());
   }

}
