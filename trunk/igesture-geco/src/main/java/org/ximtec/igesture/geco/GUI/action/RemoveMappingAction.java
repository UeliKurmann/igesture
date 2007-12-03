/*
 * @(#)RemoveMappingAction.java	1.0   Nov 26, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Remove gesture-action mapping
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 26, 2007		crocimi		Initial Release
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
import org.ximtec.igesture.geco.GestureMappingTable;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;



/**
 * Comment
 * @version 1.0 Nov 26, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class RemoveMappingAction extends BasicAction {
   

   private GecoMainView mainView;
   private GestureMappingTable mappingTable;

   
   public RemoveMappingAction(GecoMainView mainView) {
      super(GecoConstants.REMOVE_MAPPING, GuiTool.getGuiBundle());
      this.mainView = mainView;
      this.mappingTable =  mainView.getModel().mappingTable;
   }
   
   

   /**
    * Remove the mapping
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      //update model
       mainView.getModel().removeMapping(mainView.getSelectedMapping());
       //updateview
       mainView.updateLists();
   } // actionPerformed


}
