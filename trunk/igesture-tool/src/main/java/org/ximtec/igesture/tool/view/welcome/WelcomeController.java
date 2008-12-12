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
 * 17.04.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.welcome;

import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;

import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.DefaultController;


/**
 * Comment
 * @version 1.0 17.04.2008
 * @author Ueli Kurmann
 */
public class WelcomeController extends DefaultController {

   private static final Logger LOGGER = Logger.getLogger(WelcomeController.class
         .getName());

   private WelcomeView view;


   public WelcomeController() {
      initialize();
   }


   private void initialize() {
      LOGGER.log(Level.FINE, "Initialize the Welcome Controller.");
      this.view = new WelcomeView();
   }


   @Override
   public JComponent getView() {
      return view;
   }


   @Override
   public void execute(Command command) {
      super.execute(command);
   }


   @Override
   public void propertyChange(PropertyChangeEvent event) {
      super.propertyChange(event);
      view.refresh();
   }

}
