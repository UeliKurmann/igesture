package org.ximtec.igesture.tool.view.admin.action;

import hacks.GestureConstants;
import hacks.GuiBundleService;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.locator.Locator;

public class RemoveGestureClassAction extends BasicAction{
  
  private TreePath treePath;
  
  public RemoveGestureClassAction(TreePath treePath){
    super(GestureConstants.GESTURE_CLASS_DEL, Locator.getDefault().getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
    this.treePath = treePath;
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    GestureClass gestureClass = (GestureClass)treePath.getLastPathComponent();
    GestureSet gestureSet = (GestureSet)treePath.getParentPath().getLastPathComponent();
    gestureSet.removeGestureClass(gestureClass);
    
  }

}
