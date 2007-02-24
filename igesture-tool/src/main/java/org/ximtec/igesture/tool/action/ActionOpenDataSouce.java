/*
 * @(#)ActionOpenDataSource.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Opens a data source
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

import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.SwingTool;


public class ActionOpenDataSouce extends BaseAction {

   private GestureToolView mainView;


   public ActionOpenDataSouce(GestureToolView mainView) {
      super(GestureConstants.COMMON_OPEN, SwingTool.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Opens a JFileChooser and reloads the data model
    */
   public void actionPerformed(ActionEvent arg0) {
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.showOpenDialog(mainView);
      final File file = fileChooser.getSelectedFile();
      if (file != null) {
         mainView.reloadModelData(StorageManager.createStorageEngine(file));
      }
   }
}
