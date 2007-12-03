/*
 * @(#)GecoComponentHandler.java   1.0   Nov 22, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :  Provides access to available actions. Each action is
 *                  instantiated only once.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 22, 2007     crocimi     Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.GUI;

import javax.swing.Action;

import org.sigtec.graphix.widget.BasicDialog;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.dialog.MappingDialog;
import org.ximtec.igesture.geco.dialog.NewProjectDialog;





/**
 * Provides access to available actions. Each action is instantiated only once.
 * @version 1.0 Nov 22, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class GecoComponentHandler {

   private GecoMainView view;

   private MappingDialog mappingDialog;
   private NewProjectDialog newProjectDialog;



   public GecoComponentHandler(GecoMainView view) {
      this.view = view;
   } // GecoActionHandler


   public MappingDialog getMappingDialog() {
      if (mappingDialog == null) {
         mappingDialog = new MappingDialog(view);
      }

      return mappingDialog;
   } // getOpenProjectAction


   public NewProjectDialog getNewProjectDialog() {
      if (newProjectDialog == null) {
         newProjectDialog = new NewProjectDialog(view);
      }

      return newProjectDialog;
   } // getExitApplicationAction
  

   
}
