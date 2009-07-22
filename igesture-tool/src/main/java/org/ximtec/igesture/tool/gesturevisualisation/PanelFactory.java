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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.mouseclient.SwingMouseReader;


/**
 * Comment
 * @version 1.0 16.12.2008
 * @author Ueli Kurmann
 */
public class PanelFactory {

   private static Map<Class< ? >, Class< ? extends GesturePanel>> gesturePanels;
   private static Map<Class< ? >, Class< ? extends InputPanel>> inputPanels;

   static {
      gesturePanels = new HashMap<Class< ? >, Class< ? extends GesturePanel>>();
      gesturePanels.put(GestureSample.class, NoteGesturePanel.class);

      inputPanels = new HashMap<Class< ? >, Class< ? extends InputPanel>>();
      inputPanels.put(SwingMouseReader.class, SwingMouseReaderPanel.class);
   }


   /**
    * Creates a new gesture panel
    * @param clazz
    * @param gesture
    * @return
    */
   public static GesturePanel createGesturePanel(Gesture< ? > gesture) {

      GesturePanel panel = null;
      if (gesture != null) {
         Class< ? extends GesturePanel> panelClass = gesturePanels.get(gesture
               .getClass());
         if (panelClass == null) {
            panelClass = NotSupportedGesturePanel.class;
         }

         try {
            panel = panelClass.newInstance();
            panel.init(gesture);
         }
         catch (Exception e) {

            e.printStackTrace();
         }
      }

      return panel;
   }


   /**
    * Creates a new input panel.
    * @param gestureDevice
    * @return
    */
   public static InputPanel createInputPanel(GestureDevice< ? , ? > gestureDevice) {

      InputPanel panel = null;

      if (gestureDevice != null) {

         Class< ? extends InputPanel> panelClass = null;
         Class< ? > clazz = gestureDevice.getClass();
         
         // lookup super classes
         while (panelClass == null && !clazz.equals(Object.class)) {
            panelClass = inputPanels.get(clazz);
            clazz = clazz.getSuperclass();
         }

         if (panelClass == null) {
            panelClass = NotSupportedInputPanel.class;
         }

         try {
            panel = panelClass.newInstance();
            panel.init(gestureDevice);
         }
         catch (Exception e) {

            e.printStackTrace();
         }
      }
      return panel;
   }

}
