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

package org.ximtec.igesture.tool.view.admin.panel;

import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;


public class GestureSetsPanel extends AbstractAdminPanel {

   GestureSetList rootSet;


   public GestureSetsPanel(GestureSetList rootSet) {
      this.rootSet = rootSet;
      init();
   }


   private void init() {
      setTitle("Gesture Sets");
   }

}
