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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.util.GestureTool;



/**
 * Comment
 * @version 1.0 16.12.2008
 * @author Ueli Kurmann
 */
public class NoteGesturePanel implements GesturePanel{

   Gesture<?> gesture;
   
   @Override
   public void init(Gesture<?> gesture) {
     this.gesture = gesture;
   }
   
   @Override
   public JPanel getPanel() {
      JLabel label = new JLabel(new ImageIcon(GestureTool
            .createNoteImage(((GestureSample)gesture).getGesture(), 100, 100)));
      
      JPanel panel = new JPanel();
      panel.add(label);
      
      return panel;
   }
}
