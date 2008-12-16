/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 16.12.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.gesturevisualisation;

import java.util.HashMap;
import java.util.Map;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;



/**
 * Comment
 * @version 1.0 16.12.2008
 * @author Ueli Kurmann
 */
public class GesturePanelFactory {
   
   private static Map<Class<?>, Class<? extends GesturePanel>> map;
   
   static{
      map = new HashMap<Class<?>, Class<? extends GesturePanel>>();
      map.put(GestureSample.class, NoteGesturePanel.class);
   }
   
   public static GesturePanel createGesturePanel(Class<?> clazz, Gesture<?> gesture){
      
      Class<? extends GesturePanel> panelClass = map.get(clazz);
      if(panelClass == null){
         panelClass = NotSupportedGesturePanel.class;
      }
      
      try {
         GesturePanel panel = panelClass.newInstance();
         panel.init(gesture);
         return panel;
      }
      catch (Exception e) {
         
         e.printStackTrace();
      }
      
      
      return null;
   }

}
