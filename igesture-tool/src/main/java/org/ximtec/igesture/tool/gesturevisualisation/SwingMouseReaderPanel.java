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
 * 17.12.2008			ukurmann	Initial Release
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

import java.awt.Dimension;

import javax.swing.JPanel;

import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.io.mouseclient.SwingMouseReader;


/**
 * Comment
 * @version 1.0 17.12.2008
 * @author Ueli Kurmann
 */
public class SwingMouseReaderPanel implements InputPanel {

   SwingMouseReader mouseReader;


   @Override
   public void init(GestureDevice< ? , ? > gestureDevice) {
      if (gestureDevice instanceof SwingMouseReader) {
         this.mouseReader = (SwingMouseReader)gestureDevice;
      }
   }


   @Override
   public JPanel getPanel(Dimension dimension) {
      if (mouseReader != null) {
         return mouseReader.getPanel(dimension);
      }
      return null;
   }

}
