/*
 * @(#)SaveProjectAction.java   1.0   Nov 15, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :  Save the gesture map project.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2007     crocimi    Initial Release
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
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainModel;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.xml.XMLGeco;



/**
* Save the gesture map project.
* 
* @version 1.0, Nov 2006
* @author Michele Croci, mcroci@gmail.com
*/
public class SaveProjectAction extends BasicAction {

  private GecoMainView mainView;


  public SaveProjectAction(GecoMainView mainView) {
     super(GecoConstants.SAVE_PROJECT_ACTION, GuiTool.getGuiBundle());
     this.mainView = mainView;
  }


  /**
   * Creates a new mapping
   * 
   * @param event the action event.
   */
  public void actionPerformed(ActionEvent event) {
     save();

  } // actionPerformed
  
  public void save(){
     GecoMainModel model = mainView.getModel();
     XMLGeco.exportProject(model.mappingTable.values(), model.getGestureSet(), 
           model.getGestureSetFileName(), model.getProjectFile());
     //update model
     mainView.getModel().setNeedSave(false);
     //update view
     mainView.disableSaveButton();
  }//save
 
  
}

