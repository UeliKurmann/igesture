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
 * 09.04.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.ximtec.igesture.tool.core.Command;


/**
 * Comment
 * @version 1.0 09.04.2008
 * @author Ueli Kurmann
 */
public class MainWindowAdapter extends WindowAdapter {

   MainController controller;

   public MainWindowAdapter(MainController mainController){
      this.controller = mainController;
   }

   @Override
   public void windowClosing(WindowEvent e) {
      controller.execute(new Command(MainController.CMD_EXIT, e));
   }

}
