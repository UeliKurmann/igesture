/*
 * @(#)SaveProjectAction.java   1.0   Nov 15, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Saves the gesture mappings.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2007     crocimi     Initial Release
 * Jan 20, 2008     bsigner     Cleanup
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
import org.ximtec.igesture.geco.gui.MainModel;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.xml.XMLGeco;


/**
 * Saves the gesture mappings.
 * 
 * @version 0.9, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SaveProjectAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "SaveProjectAction";

   private MainView mainView;


   public SaveProjectAction(MainView mainView) {
      super(KEY, Geco.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Save the mappings.
    * 
    * @param event the event to be handled.
    */
   public void actionPerformed(ActionEvent event) {
      save();
   } // actionPerformed


   public void save() {
      MainModel model = mainView.getModel();
      XMLGeco.exportProject(model.getMappings().values(), model.getGestureSet(),
            model.getGestureSetFileName(), model.getProjectFile());
      mainView.getModel().setNeedSave(false);
      mainView.disableSaveButton(); // FIXME: work directly on button action
   } // save

}
