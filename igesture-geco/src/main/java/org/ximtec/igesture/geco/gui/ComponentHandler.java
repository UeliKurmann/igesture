/*
 * @(#)$Id$
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.gui;

import org.ximtec.igesture.geco.dialog.MappingDialog;
import org.ximtec.igesture.geco.dialog.NewProjectDialog;
import org.ximtec.igesture.geco.dialog.OptionsDialog;





/**
 * Provides access to available actions. Each action is instantiated only once.
 * @version 0.9, Nov 22, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class ComponentHandler {

   private MainView view;
   private MappingDialog mappingDialog;
   private NewProjectDialog newProjectDialog;
   private OptionsDialog optionsDialog;



   /**
    * Constructor.
    */
   public ComponentHandler(MainView view) {
      this.view = view;
   } // GecoActionHandler


   /**
    * Returns the dialog for mapping a gesture
    */
   public MappingDialog getMappingDialog() {
      if (mappingDialog == null) {
         mappingDialog = new MappingDialog(view);
      }

      return mappingDialog;
   } // getOpenProjectAction


   /**
    * Get the dialog for creating a new project
    */
   public NewProjectDialog getNewProjectDialog() {
      if (newProjectDialog == null) {
         newProjectDialog = new NewProjectDialog(view);
      }

      return newProjectDialog;
   } // getExitApplicationAction
   
   /**
    * Get the dialog for creating a new project
    */
   public OptionsDialog getOptionsDialog() {
      if (optionsDialog == null) {
         optionsDialog = new OptionsDialog(view);
      }

      return optionsDialog;
   } // getExitApplicationAction
  
  

   
}
