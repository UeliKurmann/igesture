/*
 * @(#)ActionTestSetImport.java	1.0   Nov 21, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Imports a test set from an XML file
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 23.12.2006 		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.testset.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;
import org.ximtec.igesture.util.XMLTools;


/**
 * Comment
 * 
 * @version 1.0 Nov 21, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class ActionTestSetImport extends BaseAction {

   public static final String KEY = "ActionTestSetImport";

   private GestureToolView mainView;


   public ActionTestSetImport(GestureToolView mainView) {
      super(KEY, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.IMPORT));
      this.mainView = mainView;
   }


   public void actionPerformed(ActionEvent event) {
      if (event.getSource() instanceof JMenuItem) {
         final JFileChooser fileChooser = new JFileChooser();
         fileChooser.showOpenDialog((JMenuItem) event.getSource());
         final File selectedFile = fileChooser.getSelectedFile();
         if (selectedFile != null) {
            final List<TestSet> testSets = XMLTools.importTestSet(selectedFile);
            final StorageManager storageManager = mainView.getModel()
                  .getStorageManager();
            for (final TestSet testSet : testSets) {
               if (storageManager.load(TestSet.class, testSet.getID()) == null) {
                  mainView.getModel().addTestSet(testSet);
               }
               else {
                  new JDialog();
               }

            }
         }
      }
   }

}
