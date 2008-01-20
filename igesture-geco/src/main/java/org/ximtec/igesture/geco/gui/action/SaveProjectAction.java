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
import org.ximtec.igesture.geco.gui.Constant;
import org.ximtec.igesture.geco.gui.MainModel;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.xml.XMLGeco;



/**
* Save the gesture map project.
* 
* @version 1.0, Nov 2006
* @author Michele Croci, mcroci@gmail.com
*/
public class SaveProjectAction extends BasicAction {

  private MainView mainView;


  public SaveProjectAction(MainView mainView) {
     super(Constant.SAVE_PROJECT_ACTION, Geco.getGuiBundle());
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
     MainModel model = mainView.getModel();
     XMLGeco.exportProject(model.getMappings().values(), model.getGestureSet(), 
           model.getGestureSetFileName(), model.getProjectFile());
     //update model
     mainView.getModel().setNeedSave(false);
     //update view
     mainView.disableSaveButton();
  }//save
 
  
}

