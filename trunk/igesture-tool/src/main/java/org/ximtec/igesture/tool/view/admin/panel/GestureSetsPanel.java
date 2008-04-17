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

import javax.swing.JLabel;

import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;


public class GestureSetsPanel extends AbstractPanel {

   GestureSetList rootSet;


   public GestureSetsPanel(GestureSetList rootSet) {
      this.rootSet = rootSet;
      init();
   }


   private void init() {
      // FIXME Resource File...
      setTitle(TitleFactory.createStaticTitle("Gesture Sets"));
      
      // add information about available gesture sets
      
   }

}