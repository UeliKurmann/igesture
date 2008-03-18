/*
 * @(#)ImportGestureSetAction.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Imports a gesture set.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.old.frame.gestureset.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.GestureToolView;
import org.ximtec.igesture.util.XMLTool;


/**
 * Imports a gesture set.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ImportGestureSetAction extends BasicAction {

   public static final String KEY = "ImportGestureSetAction";

   private GestureToolView mainView;


   public ImportGestureSetAction(GestureToolView frame) {
      super(KEY, GestureToolMain.getGuiBundle());
      this.mainView = frame;
   }


   public void actionPerformed(ActionEvent event) {
      if (event.getSource() instanceof JMenuItem) {
         final JFileChooser fileChooser = new JFileChooser();
         fileChooser.showOpenDialog((JMenuItem)event.getSource());
         final File selectedFile = fileChooser.getSelectedFile();

         if (selectedFile != null) {

            for (final GestureSet gestureSet : XMLTool
                  .importGestureSet(selectedFile)) {
               mainView.getModel().addGestureSet(gestureSet);

               for (final GestureClass gestureClass : gestureSet
                     .getGestureClasses()) {
                  mainView.getModel().addGestureClass(gestureClass);
               }

            }

         }

      }

   } // actionPerformed

}
