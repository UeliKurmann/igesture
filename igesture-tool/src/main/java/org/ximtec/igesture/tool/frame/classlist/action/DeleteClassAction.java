/*
 * @(#)DeleteClassAction.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Deletes a gesture class.
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


package org.ximtec.igesture.tool.frame.classlist.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.tool.GestureToolView;


/**
 * Deletes a gesture class.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class DeleteClassAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "DeleteClassAction";

   private GestureToolView mainView;

   private GestureClass gestureClass;


   public DeleteClassAction(GestureToolView mainModel, GestureClass gestureClass) {
      super(KEY, GuiTool.getGuiBundle());
      this.mainView = mainModel;
      this.gestureClass = gestureClass;
   }


   public void actionPerformed(ActionEvent event) {
      mainView.getModel().removeGestureClass(gestureClass);
   } // actionPerformed

}
