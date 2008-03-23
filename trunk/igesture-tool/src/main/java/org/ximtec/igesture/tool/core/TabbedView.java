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


public interface TabbedView {

   /**
    * Returns the name of the Tab. This name is used as the tab name.
    * @return
    */
   String getName();


   /**
    * Returns the icon of the Tab. This icon is used in the tab browser.
    * @return
    */
   Icon getIcon();


   /**
    * Returns the pane of the Tab. This JComponent represents the content of the pane.
    * @return
    */
   JComponent getPane();
}
