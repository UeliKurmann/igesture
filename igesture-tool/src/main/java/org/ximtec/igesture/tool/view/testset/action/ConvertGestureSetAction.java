/*
 * @(#)$Id$
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testset.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;
import org.ximtec.igesture.tool.util.FileFilterFactory;
import org.ximtec.igesture.tool.view.testset.wrapper.TestSetList;
import org.ximtec.igesture.util.GestureTool;
import org.ximtec.igesture.util.XMLTool;


/**
 * Comment
 * @version 1.0 08.10.2008
 * @author Ueli Kurmann
 */
public class ConvertGestureSetAction extends TreePathAction {


   public ConvertGestureSetAction(Controller controller, TreePath treePath) {
      super(GestureConstants.TESTSET_CONVERT_GESTURESET, controller, treePath);
   }


   @Override
   public void actionPerformed(ActionEvent e) {
      TestSetList testSetList = (TestSetList)getTreePath().getLastPathComponent();

      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(FileFilterFactory.getGestureSet());
      fileChooser.showOpenDialog(null);
      File selectedFile = fileChooser.getSelectedFile();

      if (selectedFile != null) {
         GestureSet gestureSet = XMLTool.importGestureSet(selectedFile);
         TestSet testSet = GestureTool.createTestSet(gestureSet);
         testSetList.addTestSet(testSet);
      }
   }

}
