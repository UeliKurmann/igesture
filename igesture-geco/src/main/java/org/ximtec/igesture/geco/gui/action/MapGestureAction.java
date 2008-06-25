/*
 * @(#)MapgestureAction.java	1.0   Nov 19, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Maps a gesture to a custom action.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 19, 2007		crocimi		Initial Release
 * Jan 15, 2008     bsigner     Cleanup
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
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.util.GuiBundleTool;


/**
 * Maps a gesture to a custom action.
 * 
 * @version 1.0, Nov 2006
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class MapGestureAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "MapGestureAction";

   private MainView mainView;
   private GestureClass currentGesture;


   public MapGestureAction(MainView mainView) {
      super(KEY, GuiBundleTool.getBundle());
      this.mainView = mainView;
   }


   /**
    * Shows an add mapping dialog.
    * 
    * @param event the event to be handled.
    */
   public void actionPerformed(ActionEvent event) {
      currentGesture = mainView.getSelectedClass();
      mainView.getComponentHandler().getMappingDialog().showDialog(
            currentGesture);
   } // actionPerformed

}
