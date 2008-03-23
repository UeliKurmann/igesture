/*
 * @(#)$Id: Main.java 454 2008-03-23 17:06:49Z kurmannu $
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

package org.ximtec.igesture.tool.view.admin.panel;

import org.ximtec.igesture.tool.view.RootSet;


public class GestureSetsPanel extends AbstractAdminPanel {

   RootSet rootSet;


   public GestureSetsPanel(RootSet rootSet) {
      this.rootSet = rootSet;
      init();
   }


   private void init() {
      setTitle("Gesture Sets");
   }

}
