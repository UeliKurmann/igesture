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

import java.awt.Dimension;
import java.net.URL;

import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.HtmlPanel;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;


public class GestureSetsPanel extends AbstractPanel {

   private static final String HTML_FILE = "html/adminTab.html";
   GestureSetList rootSet;


   public GestureSetsPanel(Controller controller, GestureSetList rootSet) {
     super(controller); 
     this.rootSet = rootSet;
      init();
   }


   private void init() {
      String title = getComponentFactory().getGuiBundle().getName(GestureConstants.GESTURE_SETS_PANEL_TITLE);
      setTitle(TitleFactory.createStaticTitle(title));
      
      URL path = this.getClass().getClassLoader().getResource(HTML_FILE);
      HtmlPanel htmlPanel = new HtmlPanel(path, new Dimension(400,400));
      setCenter(htmlPanel);
      
   }

}
