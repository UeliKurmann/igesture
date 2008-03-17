/*
 * @(#)ActionHandler.java	1.0   Nov 21, 2007
 *
 * Author		:	Beat Signer, signer@inf.ethz.ch
 *
 * Purpose		:   Provides access to available actions. Each action is
 *                  instantiated only once.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 21, 2007		bsigner		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.action;

import javax.swing.Action;

import org.ximtec.igesture.tool.GestureToolView;


/**
 * Provides access to available actions. Each action is instantiated only once.
 * @version 1.0 Nov 21, 2007
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionHandler {

   private GestureToolView view;

   private NewDataSouceAction newDataSourceAction;
   private OpenDataSouceAction openDataSourceAction;


   public ActionHandler(GestureToolView view) {
      this.view = view;
   } // ActionHandler


   public Action getNewDataSourceAction() {
      if (newDataSourceAction == null) {
         newDataSourceAction = new NewDataSouceAction(view);
      }

      return newDataSourceAction;
   } // getNewDataSourceAction


   public Action getOpenDataSourceAction() {
      if (openDataSourceAction == null) {
         openDataSourceAction = new OpenDataSouceAction(view);
      }

      return openDataSourceAction;
   } // getOpenDataSourceAction

}
