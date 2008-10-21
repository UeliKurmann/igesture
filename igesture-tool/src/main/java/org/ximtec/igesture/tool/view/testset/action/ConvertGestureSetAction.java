/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 08.10.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testset.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.testset.wrapper.TestSetList;
import org.ximtec.igesture.util.GestureTool;
import org.ximtec.igesture.util.XMLTool;


/**
 * Comment
 * @version 1.0 08.10.2008
 * @author Ueli Kurmann
 */
public class ConvertGestureSetAction extends BasicAction {

   private TreePath treePath;


   public ConvertGestureSetAction(TreePath treePath) {
      super(GestureConstants.TESTSET_CONVERT_GESTURESET, Locator.getDefault().getService(
            GuiBundleService.IDENTIFIER, GuiBundleService.class));

      this.treePath = treePath;
   }


   @Override
   public void actionPerformed(ActionEvent e) {
      TestSetList testSetList = (TestSetList)treePath.getLastPathComponent();

      JFileChooser fileChooser = new JFileChooser();
      fileChooser.showOpenDialog(null);
      File selectedFile = fileChooser.getSelectedFile();

      if (selectedFile != null) {
         GestureSet gestureSet = XMLTool.importGestureSet(selectedFile);
         TestSet testSet = GestureTool.createTestSet(gestureSet);
         testSetList.addTestSet(testSet);
      }
   }

}
