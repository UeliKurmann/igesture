/*
 * @(#)$Id$
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Minimises the application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2006     crocimi     Initial Release
 * Jan 15, 2008     bsigner     Cleanup
 * 
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.gui.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.util.GuiBundleTool;


/**
 * Minimises the application.
 * 
 * @version 0.9, Nov 15, 2006
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, bsigner@vub.ac.be
 */

public class MinimizeAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "MinimizeAction";

   private MainView mainView;


   public MinimizeAction(MainView mainView) {
      super(KEY, GuiBundleTool.getBundle());
      this.mainView = mainView;
   }


   /**
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      minimizeWindow();
   } // actionPerformed
   
   public void minimizeWindow(){
      mainView.setVisible(false);
   }

}
