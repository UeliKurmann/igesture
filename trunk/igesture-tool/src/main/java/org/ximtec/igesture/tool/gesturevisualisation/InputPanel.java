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

import java.awt.Dimension;

import javax.swing.JPanel;

import org.ximtec.igesture.io.GestureDevice;


/**
 * Comment
 * @version 1.0 16.12.2008
 * @author Ueli Kurmann
 * 
 *         This is a wrapper interface used to visualize gestures in real time.
 *         Implementations need a public default constructor.
 * 
 */
public interface InputPanel {

   /**
    * Initializes the input panel with a gesture device. 
    * @param gesture
    */
   void init(GestureDevice< ?,? > gestureDevice);


   /**
    * Returns a new panel visualizing the gesture. 
    * @param dimension
    * @return
    */
   JPanel getPanel(Dimension dimension);

}
