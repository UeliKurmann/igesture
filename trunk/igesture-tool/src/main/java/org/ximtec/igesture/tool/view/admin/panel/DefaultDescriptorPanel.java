package org.ximtec.igesture.tool.view.admin.panel;

import javax.swing.tree.TreePath;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.DefaultDescriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.explorer.ExplorerTreeController;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;

public abstract class DefaultDescriptorPanel<T extends DefaultDescriptor> extends AbstractPanel {
  
  private T descriptor;

  public DefaultDescriptorPanel(Controller controller, T descriptor) {
    super(controller);
    this.descriptor = descriptor;
  }

  /**
   * Sets the title of the form
   */
  protected void initTitle() {
  
    StringBuilder sb = new StringBuilder();
  
    if (getController() instanceof ExplorerTreeController) {
      try {
        ExplorerTreeController ec = (ExplorerTreeController) getController();
        sb.append(descriptor.getName());
        sb.append(Constant.COLON_BLANK);
  
        TreePath treePath = ec.getExplorerTree().getSelectionPath();
        GestureClass gc = (GestureClass) treePath.getParentPath().getLastPathComponent();
  
        sb.append(gc.getName());
      } catch (Exception e) {
        sb.append(descriptor.getName());
      }
    } else {
      sb.append(descriptor.getName());
    }
  
    setTitle(TitleFactory.createStaticTitle(sb.toString()));
  }
  
  /**
   * Returns the descriptor
   * @return
   */
  protected T getDescriptor(){
    return descriptor;
  }

}