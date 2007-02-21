/*
 * @(#)GestureTab.java	1.0   Nov 28, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Tab interface
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool;

import javax.swing.JDesktopPane;


/**
 * Comment
 * 
 * @version 1.0 Nov 28, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public abstract class GestureTab {

   JDesktopPane desktopPane;

   GestureToolView mainView;


   public GestureTab() {
      desktopPane = new JDesktopPane();
   }


   /**
    * Returns the JDesktopPane of the tab
    * 
    * @return
    */
   public JDesktopPane getDesktopPane() {
      return desktopPane;
   }


   /**
    * Initialise the component
    * 
    * @param mainView
    */
   public void init(GestureToolView mainView) {
      this.mainView = mainView;
   }


   /**
    * Returns the main view of the iGesture application
    * 
    * @return
    */
   public GestureToolView getMainView() {
      return mainView;
   }


   /**
    * Returns the name of the tab
    * 
    * @return
    */
   public abstract String getName();

}
