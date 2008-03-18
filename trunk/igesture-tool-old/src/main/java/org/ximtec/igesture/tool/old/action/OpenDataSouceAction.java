/*
 * @(#)ActionOpenDataSource.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Opens a data source.
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


package org.ximtec.igesture.tool.old.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.GestureToolView;


/**
 * Opens a data source.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class OpenDataSouceAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "OpenDataSourceAction";

   private GestureToolView mainView;


   public OpenDataSouceAction(GestureToolView mainView) {
      super(KEY, GestureToolMain.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Opens a JFileChooser and reloads the data model.
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.showOpenDialog(mainView);
      final File file = fileChooser.getSelectedFile();

      if (file != null) {
         mainView.reloadModelData(StorageManager.createStorageEngine(file));
      }

   } // actionPerformed

}
