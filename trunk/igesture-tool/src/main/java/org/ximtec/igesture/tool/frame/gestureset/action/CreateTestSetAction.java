/*
 * @(#)CreateTestSetAction.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Creates a test set out of a gesture set.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.tool.frame.gestureset.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.util.GestureTool;
import org.ximtec.igesture.util.XMLTool;


/**
 * Creates a test set out of a gesture set.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class CreateTestSetAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "ExportCreateTestSetAction";

   private GestureSet set;


   public CreateTestSetAction(GestureSet set) {
      super(KEY, GuiTool.getGuiBundle());
      this.set = set;
   }


   public void actionPerformed(ActionEvent event) {
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.showSaveDialog((JMenuItem)event.getSource());
      final File selectedFile = fileChooser.getSelectedFile();

      if (selectedFile != null) {
         final TestSet testSet = GestureTool.createTestSet(set);
         XMLTool.exportTestSet(testSet, selectedFile);
      }

   } // actionPerformed

}
