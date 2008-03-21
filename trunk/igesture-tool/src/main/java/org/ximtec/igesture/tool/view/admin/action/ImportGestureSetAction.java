package org.ximtec.igesture.tool.view.admin.action;

import hacks.GestureConstants;
import hacks.GuiBundleService;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.view.RootSet;
import org.ximtec.igesture.util.XMLTool;

public class ImportGestureSetAction extends BasicAction {

  private static final Logger LOG = Logger
      .getLogger(ExportPDFGestureSetAction.class);

  private TreePath treePath;

  public ImportGestureSetAction(TreePath treePath) {
    super(GestureConstants.GESTURE_SET_IMPORT, Locator.getDefault().getService(
        GuiBundleService.IDENTIFIER, GuiBundleService.class));
    this.treePath = treePath;
  }

  public void actionPerformed(ActionEvent event) {

    RootSet rootSet = (RootSet) treePath.getLastPathComponent();

    final JFileChooser fileChooser = new JFileChooser();
    fileChooser.showOpenDialog((JMenuItem) event.getSource());
    final File selectedFile = fileChooser.getSelectedFile();

    if (selectedFile != null) {
      for (final GestureSet gestureSet : XMLTool.importGestureSet(selectedFile)) {
        rootSet.addGestureSet(gestureSet);
      }
    }
  } // actionPerformed

}
