

package org.ximtec.igesture.tool.view.admin.action;

import hacks.PDFTool;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;


public class ExportPDFGestureSetAction extends BasicAction {

   private static final Logger LOG = Logger
         .getLogger(ExportPDFGestureSetAction.class.getName());

   private TreePath treePath;


   public ExportPDFGestureSetAction(TreePath treePath) {
      super(GestureConstants.GESTURE_SET_PDF, Locator.getDefault().getService(
            GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.treePath = treePath;
   }


   public void actionPerformed(ActionEvent event) {

      GestureSet gestureSet = (GestureSet)treePath.getLastPathComponent();

      Document.compress = false;
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.showSaveDialog((JMenuItem)event.getSource());
      final File selectedFile = fileChooser.getSelectedFile();

      if (selectedFile != null) {
         final Document document = PDFTool.createDocument(selectedFile);
         document.open();

         try {
            document.add(PDFTool.createGestureSetTable(gestureSet));
         }
         catch (final DocumentException e) {
            LOG.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         }

         document.close();
      }

   } // actionPerformed

}
