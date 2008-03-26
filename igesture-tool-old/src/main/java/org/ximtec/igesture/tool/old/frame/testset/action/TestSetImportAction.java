/*
 * @(#)TestSetImportAction.java	1.0   Nov 21, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Imports a test set from an XML file.
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


package org.ximtec.igesture.tool.old.frame.testset.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.GestureToolView;
import org.ximtec.igesture.util.XMLTool;


/**
 * Imports a test set from an XML file.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TestSetImportAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "TestSetImportAction";

   private GestureToolView mainView;


   public TestSetImportAction(GestureToolView mainView) {
      super(KEY, GestureToolMain.getGuiBundle());
      this.mainView = mainView;
   }


   public void actionPerformed(ActionEvent event) {
      if (event.getSource() instanceof JMenuItem) {
         final JFileChooser fileChooser = new JFileChooser();
         fileChooser.showOpenDialog((JMenuItem)event.getSource());
         final File selectedFile = fileChooser.getSelectedFile();

         if (selectedFile != null) {
            final List<TestSet> testSets = XMLTool.importTestSet(selectedFile);
            final StorageManager storageManager = mainView.getModel()
                  .getStorageManager();

            for (final TestSet testSet : testSets) {

               if (storageManager.load(TestSet.class, testSet.getId()) == null) {
                  mainView.getModel().addTestSet(testSet);
               }
               else {
                  new JDialog();
               }

            }

         }

      }

   } // actionPerformed

}