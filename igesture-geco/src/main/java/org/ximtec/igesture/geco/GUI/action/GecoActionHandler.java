/*
 * @(#)GecoActionHandler.java	1.0   Nov 22, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  Provides access to available actions. Each action is
 *                  instantiated only once.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 22, 2007	    crocimi		Initial Release
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

import javax.swing.Action;

import org.ximtec.igesture.geco.GUI.GestureMappingView;





/**
 * Provides access to available actions. Each action is instantiated only once.
 * @version 1.0 Nov 22, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class GecoActionHandler {

   private GestureMappingView view;

   private OpenProjectAction openGestureMapAction;
   private ExitApplicationAction exitApplicationAction;
   private LoadGestureSetAction loadGestureSetAction;
   private AddMappingAction addMappingAction;
   private NewProjectAction newProjectAction;
   private EditMappingAction editMappingAction;
   private RemoveMappingAction removeMappingAction;
   private SaveProjectAction saveProjectAction;


   public GecoActionHandler(GestureMappingView view) {
      this.view = view;
   } // GecoActionHandler


   public Action getOpenProjectAction() {
      if (openGestureMapAction == null) {
         openGestureMapAction = new OpenProjectAction(view);
      }

      return openGestureMapAction;
   } // getOpenProjectAction


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
   
   
   public Action getAddMappingAction() {
      if (addMappingAction == null) {
         addMappingAction = new AddMappingAction(view);
      }

      return addMappingAction;
   } // getMapGestureAction
   
   
   public Action getNewProjectAction() {
      if (newProjectAction == null) {
         newProjectAction = new NewProjectAction(view);
      }

      return newProjectAction;
   } // getNewGestureMapAction
   
   
   public Action getEditMappingAction() {
      if (editMappingAction == null) {
         editMappingAction = new EditMappingAction(view);
      }

      return editMappingAction;
   } // getNewGestureMapAction
   
   
   public Action getRemoveMappingAction() {
      if (removeMappingAction == null) {
         removeMappingAction = new RemoveMappingAction(view);
      }

      return removeMappingAction;
   } // getNewGestureMapAction
   
   
   public Action getSaveProjectAction() {
      if (saveProjectAction == null) {
         saveProjectAction = new SaveProjectAction(view);
      }

      return saveProjectAction;
   } // getNewGestureMapAction
   
}
