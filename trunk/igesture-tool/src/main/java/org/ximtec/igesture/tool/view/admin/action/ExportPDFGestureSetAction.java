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
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.old.util.PDFTool;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;


public class ExportPDFGestureSetAction extends BasicAction {

   private static final Logger LOG = Logger
         .getLogger(ExportPDFGestureSetAction.class);

   private TreePath treePath;
   
   public ExportPDFGestureSetAction(TreePath treePath) {
     super(GestureConstants.GESTURE_SET_PDF, Locator.getDefault().getService(
         GuiBundleService.IDENTIFIER, GuiBundleService.class));
     this.treePath = treePath;
   }

   public void actionPerformed(ActionEvent event) {
     
     GestureSet gestureSet = (GestureSet) treePath.getLastPathComponent();
     
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
            LOG.error(Constant.EMPTY_STRING, e);
         }

         document.close();
      }

   } // actionPerformed

}
