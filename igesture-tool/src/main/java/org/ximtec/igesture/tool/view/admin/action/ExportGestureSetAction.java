package org.ximtec.igesture.tool.view.admin.action;

import hacks.GestureConstants;
import hacks.GuiBundleService;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.util.XMLTool;

public class ExportGestureSetAction extends BasicAction {

  private TreePath treePath;

  public ExportGestureSetAction(TreePath treePath) {
    super(GestureConstants.GESTURE_SET_EXPORT, Locator.getDefault().getService(
        GuiBundleService.IDENTIFIER, GuiBundleService.class));
    this.treePath = treePath;
  }

  public void actionPerformed(ActionEvent event) {
    GestureSet gestureSet = (GestureSet) treePath.getLastPathComponent();

    final JFileChooser fileChooser = new JFileChooser();
    fileChooser.showSaveDialog((JMenuItem) event.getSource());
    final File selectedFile = fileChooser.getSelectedFile();

    if (selectedFile != null) {
      XMLTool.exportGestureSet(gestureSet, selectedFile);
    }

  } 
}
