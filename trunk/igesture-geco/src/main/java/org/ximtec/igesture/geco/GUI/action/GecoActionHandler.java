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

import org.ximtec.igesture.geco.GestureMappingTable;
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
   private MapGestureAction mapGestureAction;
   private NewGestureMapAction newGestureMapAction;


   public GecoActionHandler(GestureMappingView view) {
      this.view = view;
   } // GecoActionHandler


   public Action getNewDataSourceAction() {
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
   
   
   public Action getMapGestureAction(GestureMappingTable mappingTable) {
      if (mapGestureAction == null) {
         mapGestureAction = new MapGestureAction(view, mappingTable);
      }

      return mapGestureAction;
   } // getMapGestureAction
   
   
   public Action getNewGestureMapAction() {
      if (newGestureMapAction == null) {
         newGestureMapAction = new NewGestureMapAction(view);
      }

      return newGestureMapAction;
   } // getNewGestureMapAction
}
