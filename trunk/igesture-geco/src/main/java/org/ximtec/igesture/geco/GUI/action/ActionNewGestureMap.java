/*
 * @(#)NewGestureMapAction.java	1.0   Nov 15, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   represent action for creating a new gesture mapping project
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 	Nov 15, 2007	crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.GUI.action;



import java.awt.event.ActionEvent;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.geco.GUI.GestureMappingConstants;
import org.ximtec.igesture.geco.GUI.GestureMappingView;


/**
 * Creates a new data source.
 * 
 * @version 1.0, Nov 2006
 * @author Michele Croci, mcroci@gmail.com
 */
public class ActionNewGestureMap extends BasicAction {

   private GestureMappingView mainView;


   public ActionNewGestureMap(GestureMappingView mainView) {
      super(GestureMappingConstants.COMMON_NEW, GuiTool.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Creates a new mapping
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      

   } // actionPerformed
   
}
