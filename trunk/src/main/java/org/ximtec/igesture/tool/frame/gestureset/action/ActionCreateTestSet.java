/*
 * @(#)ActionCreateTestSet.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   creates a test set out of a gesture set
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
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

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;
import org.ximtec.igesture.util.GestureTools;
import org.ximtec.igesture.util.XMLTools;


public class ActionCreateTestSet extends BaseAction {

   private GestureSet set;


   public ActionCreateTestSet(GestureSet set) {
      super(GestureConstants.GESTURE_CLASS_VIEW_EXPORT_TEST_SET, SwingTool
            .getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_NEW));
      this.set = set;
   }


   public void actionPerformed(ActionEvent event) {

      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.showSaveDialog((JMenuItem) event.getSource());
      final File selectedFile = fileChooser.getSelectedFile();
      if (selectedFile != null) {
         final TestSet testSet = GestureTools.createTestSet(set);
         XMLTools.exportTestSet(testSet, selectedFile);
      }
   }
}
