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

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.batch.BatchView;

public class SelectOutputDirAction extends BasicAction {

  private BatchView view;

  public SelectOutputDirAction(Controller controller, BatchView view) {
    super(GestureConstants.BATCH_BROWSE_OUTPUT, controller.getLocator().getService(GuiBundleService.IDENTIFIER,
        GuiBundleService.class));

    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent action) {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.showOpenDialog(null);
    File file = chooser.getSelectedFile();
    if (file != null) {
      view.setOutputDir(file.getAbsolutePath());
    }
  }

}
