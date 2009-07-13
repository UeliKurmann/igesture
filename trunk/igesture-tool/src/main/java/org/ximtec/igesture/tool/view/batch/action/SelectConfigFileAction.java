/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.batch.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.LocateableAction;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.util.FileFilterFactory;
import org.ximtec.igesture.tool.view.batch.BatchView;


public class SelectConfigFileAction extends LocateableAction {

   private BatchView view;


   public SelectConfigFileAction(BatchView view) {
      super(GestureConstants.BATCH_BROWSE_CONFIG, Locator.getDefault());

      this.view = view;

   }


   @Override
   public void actionPerformed(ActionEvent action) {
      JFileChooser chooser = new JFileChooser();
      chooser.setFileFilter(FileFilterFactory.getBatchConfig());
      chooser.showOpenDialog(null);
      File file = chooser.getSelectedFile();
      if (file != null) {
         view.setConfigFile(file.getAbsolutePath());
      }
   }

}
