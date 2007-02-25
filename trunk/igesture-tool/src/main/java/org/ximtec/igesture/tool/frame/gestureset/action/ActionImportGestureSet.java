/*
 * @(#)ActionImportGestureSet.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Imports a gesture set
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.gestureset.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.util.IconLoader;
import org.ximtec.igesture.tool.util.SwingTool;
import org.ximtec.igesture.util.XMLTools;


public class ActionImportGestureSet extends BasicAction {

   public static final String KEY = "ImportGestureSet";

   private GestureToolView mainView;


   public ActionImportGestureSet(GestureToolView frame) {
      super(KEY, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.IMPORT));
      this.mainView = frame;
   }


   public void actionPerformed(ActionEvent event) {

      if (event.getSource() instanceof JMenuItem) {
         final JFileChooser fileChooser = new JFileChooser();
         fileChooser.showOpenDialog((JMenuItem) event.getSource());
         final File selectedFile = fileChooser.getSelectedFile();
         if (selectedFile != null) {
            for (final GestureSet gestureSet : XMLTools
                  .importGestureSet(selectedFile)) {
               mainView.getModel().addGestureSet(gestureSet);
               for (final GestureClass gestureClass : gestureSet
                     .getGestureClasses()) {
                  mainView.getModel().addGestureClass(gestureClass);
               }
            }
         }
      }
   }

}
