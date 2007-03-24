/*
 * @(#)ActionExportGestureSet.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Exports a gesture set to an XML file.
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
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.util.IconLoader;
import org.ximtec.igesture.tool.util.SwingTool;
import org.ximtec.igesture.util.XMLTool;


/**
 * Exports a gesture set to an XML file.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionExportGestureSet extends BasicAction {

   private GestureSet gestureSet;


   public ActionExportGestureSet(GestureSet gestureSet) {
      super(GestureConstants.GESTURE_CLASS_VIEW_EXPORT_SET_XML, SwingTool
            .getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.EXPORT));
      this.gestureSet = gestureSet;
   }


   public void actionPerformed(ActionEvent event) {
      if (event.getSource() instanceof JMenuItem) {
         final JFileChooser fileChooser = new JFileChooser();
         fileChooser.showSaveDialog((JMenuItem)event.getSource());
         final File selectedFile = fileChooser.getSelectedFile();

         if (selectedFile != null) {
            XMLTool.exportGestureSet(gestureSet, selectedFile);
         }

      }

   } // actionPerformed

}
