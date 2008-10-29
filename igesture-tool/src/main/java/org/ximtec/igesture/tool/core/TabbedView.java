/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.core;

import javax.swing.Icon;
import javax.swing.JComponent;


/**
 * Tabbed view interface to be implemented by the different tab views.
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface TabbedView {

   /**
    * Returns the name of the tab. This name is used as the tab name.
    * @return the name of the tab. This name is used as the tab name.
    */
   String getName();


   /**
    * Returns the icon of the tab. This icon is used in the tab browser.
    * @return the icon of the tab. This icon is used in the tab browser.
    */
   Icon getIcon();


   /**
    * Returns the pane of the tab. This JComponent represents the content of the
    * pane.
    * @return the pane of the tab. This JComponent represents the content of the
    *         pane.
    */
   JComponent getPane();
}
