/*
 * @(#)GestureTab.java	1.0   Nov 28, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Tab interface.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 28, 2006		ukurmann	Initial Release
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


package org.ximtec.igesture.tool;

import java.awt.Color;

import javax.swing.JDesktopPane;


/**
 * Tab interface.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public abstract class GestureTab {

   private static Color DARK_BLUE = new Color(0, 92, 233);
   
   JDesktopPane desktopPane;

   GestureToolView mainView;


   public GestureTab() {
      desktopPane = new JDesktopPane();
      desktopPane.setBackground(DARK_BLUE);
      desktopPane.setForeground(DARK_BLUE);
   }


   /**
    * Returns the tab's JDesktopPane.
    * 
    * @return the tab's JDesktopPane.
    */
   public JDesktopPane getDesktopPane() {
      return desktopPane;
   } // getDesktopPane


   /**
    * Initialises the component.
    * 
    * @param mainView the main view.
    */
   public void init(GestureToolView mainView) {
      this.mainView = mainView;
   } // init


   /**
    * Returns the main view of the iGesture application.
    * 
    * @return the main view of the iGesture application.
    */
   public GestureToolView getMainView() {
      return mainView;
   } // getMainView


   /**
    * Returns the name of the tab.
    * 
    * @return the tab's name.
    */
   public abstract String getName();

}
