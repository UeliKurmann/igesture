/*
 * @(#)ActionNewDataSource.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Creates a new data source.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.storage.Db4oStorageEngine;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;


/**
 * Creates a new data source.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionNewDataSouce extends BasicAction {

   private GestureToolView mainView;


   public ActionNewDataSouce(GestureToolView mainView) {
      super(GestureConstants.COMMON_NEW, SwingTool.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Opens a file save dialog and reloads the data model.
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.showSaveDialog(mainView);
      final File file = fileChooser.getSelectedFile();

      if (file != null) {
         mainView.reloadModelData(new Db4oStorageEngine(file.getAbsolutePath()));
      }

   } // actionPerformed
   
}
