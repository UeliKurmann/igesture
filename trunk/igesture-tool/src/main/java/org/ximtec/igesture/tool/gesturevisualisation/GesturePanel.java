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

import javax.swing.JPanel;

import org.ximtec.igesture.core.Gesture;


/**
 * Comment
 * @version 1.0 16.12.2008
 * @author Ueli Kurmann
 * 
 *         This is a wrapper interface used to visualize gestures.
 *         Implementations need a public default constructor.
 * 
 */
public interface GesturePanel {

   /**
    * Initializes the gesture panel.
    * @param gesture
    */
   void init(Gesture< ? > gesture);


   /**
    * Returns a new panel visualizing the gesture. 
    * @param dimension
    * @return
    */
   JPanel getPanel(Dimension dimension);

}
