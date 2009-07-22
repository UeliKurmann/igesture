/*
 * @(#)$Id$
 *
 * Author		:	Michele Croci, mcroci@gmail.com
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
 * Nov 22, 2007	    crocimi		Initial Release
 * Jun 26, 2008     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.gui.action;

import javax.swing.Action;

import org.ximtec.igesture.geco.gui.MainView;


/**
 * Provides access to available actions. Each action is instantiated only once.
 * @version 0.9, Nov 22, 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionHandler {

   private MainView view;

   private AboutAction aboutAction;
   private AddMappingAction addMappingAction;
   private EditMappingAction editMappingAction;
   private ExitApplicationAction exitApplicationAction;
   private LoadGestureSetAction loadGestureSetAction;
   private MinimizeAction minimizeAction;
   private NewProjectAction newProjectAction;
   private OpenProjectAction openGestureMapAction;
   private OptionsAction optionsAction;
   private RemoveMappingAction removeMappingAction;
   private SaveProjectAction saveProjectAction;
   private SaveProjectAsAction saveProjectAsAction;


   public ActionHandler(MainView view) {
      this.view = view;
   } // GecoActionHandler


   public Action getAboutAction() {
      if (aboutAction == null) {
         aboutAction = new AboutAction();
      }

      return aboutAction;
   } // getAboutAction


   public Action getAddMappingAction() {
      if (addMappingAction == null) {
         addMappingAction = new AddMappingAction(view);
      }

      return addMappingAction;
   } // getAddMappingAction


   public Action getEditMappingAction() {
      if (editMappingAction == null) {
         editMappingAction = new EditMappingAction(view);
      }

      return editMappingAction;
   } // getEditMappingAction


   public Action getExitApplicationAction() {
      if (exitApplicationAction == null) {
         exitApplicationAction = new ExitApplicationAction(view);
      }

      return exitApplicationAction;
   } // getExitApplicationAction


   public Action getLoadGestureSetAction() {
      if (loadGestureSetAction == null) {
         loadGestureSetAction = new LoadGestureSetAction(view);
      }

      return loadGestureSetAction;
   } // getLoadGestureSetAction


   public Action getMinimizeAction() {
      if (minimizeAction == null) {
         minimizeAction = new MinimizeAction(view);
      }

      return minimizeAction;
   } // getMinimizeAction


   public Action getNewProjectAction() {
      if (newProjectAction == null) {
         newProjectAction = new NewProjectAction(view);
      }

      return newProjectAction;
   } // getNewProjectAction


   public Action getOpenProjectAction() {
      if (openGestureMapAction == null) {
         openGestureMapAction = new OpenProjectAction(view);
      }

      return openGestureMapAction;
   } // getOpenProjectAction


   public Action getOptionsAction() {
      if (optionsAction == null) {
         optionsAction = new OptionsAction(view);
      }

      return optionsAction;
   } // getOptionsAction


   public Action getRemoveMappingAction() {
      if (removeMappingAction == null) {
         removeMappingAction = new RemoveMappingAction(view);
      }

      return removeMappingAction;
   } // getRemoveMappingAction


   public Action getSaveProjectAction() {
      if (saveProjectAction == null) {
         saveProjectAction = new SaveProjectAction(view);
      }

      return saveProjectAction;
   } // getSaveProjectAction


   public Action getSaveProjectAsAction() {
      if (saveProjectAsAction == null) {
         saveProjectAsAction = new SaveProjectAsAction(view);
      }

      return saveProjectAsAction;
   } // getSaveProjectAsAction

}
