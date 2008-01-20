/*
 * @(#)AboutAction.java   1.0   Dec 18, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   About dialog.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 18, 2007     crocimi     Initial Release
 * Jan 20, 2008     bsigner     Redesign with externalisation of data
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.gui.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.geco.Geco;
import org.ximtec.igesture.geco.dialog.AboutDialog;


/**
 * About dialog.
 * 
 * @version 0.9, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class AboutAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "AboutAction";

   AboutDialog dialog;


   public AboutAction() {
      super(KEY, Geco.getGuiBundle());
      dialog = new AboutDialog(AboutDialog.KEY, AboutDialog.KEY, Geco
            .getGuiBundle());
   }


   /**
    * Makes the dialogue visible.
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      dialog.setLocation(200, 200);
      dialog.setVisible(true);
   } // actionPerformed

}
