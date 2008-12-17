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

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ximtec.igesture.io.GestureDevice;



/**
 * Comment
 * @version 1.0 16.12.2008
 * @author Ueli Kurmann
 */
public class NotSupportedInputPanel implements InputPanel{

   GestureDevice<?,?> gestureDevice;
   
   @Override
   public void init(GestureDevice<?,?> gestureDevice) {
     this.gestureDevice = gestureDevice;
   }
   
   @Override
   public JPanel getPanel(Dimension dimension) {
      JLabel label = new JLabel(gestureDevice.getClass().getName());
      JPanel panel = new JPanel();
      panel.setPreferredSize(dimension);
      panel.add(label);
      
      return panel;
   }
}
