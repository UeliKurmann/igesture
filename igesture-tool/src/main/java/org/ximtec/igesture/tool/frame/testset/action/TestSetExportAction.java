/*
 * @(#)TestSetExportAction.java	1.0   Nov 21, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Exports the test set to an XML file.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 21, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.testset.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenuItem;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.frame.testset.TestSetListModel;
import org.ximtec.igesture.util.XMLTool;


/**
 * Exports the test set to an XML file.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TestSetExportAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "TestSetExportAction";

   private JList list;


   public TestSetExportAction(JList list) {
      super(KEY, GuiTool.getGuiBundle());
      this.list = list;
   }


   public void actionPerformed(ActionEvent event) {
      if (event.getSource() instanceof JMenuItem) {
         final JFileChooser fileChooser = new JFileChooser();
         fileChooser.showSaveDialog((JMenuItem)event.getSource());
         final File selectedFile = fileChooser.getSelectedFile();

         if (selectedFile != null) {
            final TestSet testSet = ((TestSetListModel)list.getModel())
                  .getTestSet((String)list.getSelectedValue());
            XMLTool.exportTestSet(testSet, selectedFile);
         }

      }

   } // actionPerformed

}
