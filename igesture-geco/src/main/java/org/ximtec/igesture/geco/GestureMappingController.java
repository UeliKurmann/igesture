/*
 * @(#)GestureMappingController.java	1.0   Nov 20, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Controller for the GestureappingView class
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 20, 2007		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco;

import java.util.EventObject;

import org.ximtec.igesture.geco.GUI.GestureMappingModel;
import org.ximtec.igesture.geco.GUI.GestureMappingView;
import org.ximtec.igesture.geco.GUI.action.ActionExitApplication;
import org.ximtec.igesture.geco.GUI.action.ActionLoadGestureSet;
import org.ximtec.igesture.geco.GUI.action.ActionMapGesture;
import org.ximtec.igesture.geco.GUI.action.ActionNewGestureMap;
import org.ximtec.igesture.geco.GUI.action.ActionOpenGestureMap;
import org.ximtec.igesture.geco.event.GestureSetLoadListener;



/**
 * Comment
 * @version 1.0 Nov 20, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class GestureMappingController{
   
   
   
   private GestureMappingView view;
   
   private GestureMappingModel model;
   
   private GestureMappingTable mappingTable = new GestureMappingTable();
   
   private ActionExitApplication actionExitApplication = new ActionExitApplication(view);
   
   private ActionLoadGestureSet actionLoadGestureSet = new ActionLoadGestureSet(view);
   
   private ActionMapGesture actionMapGesture =  new ActionMapGesture(view, mappingTable);
   
   private ActionNewGestureMap actionNewGestureMap= new ActionNewGestureMap(view);
   
   private ActionOpenGestureMap actionOpenGestureMap = new ActionOpenGestureMap(view);
   
   
   public GestureMappingController(GestureMappingView view, GestureMappingModel model){
      this.view = view;
      this.model = model;
      
      view.addActionExitApplication(actionExitApplication);
      view.addActionLoadGestureSet(actionLoadGestureSet);
      view.addActionMapGesture(actionMapGesture);
      view.addActionNewGestureMap(actionNewGestureMap);
      view.addActionOpenGestureMap(actionOpenGestureMap);
   }
   


}
