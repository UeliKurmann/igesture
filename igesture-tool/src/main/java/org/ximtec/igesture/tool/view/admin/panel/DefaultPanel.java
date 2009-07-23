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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.admin.panel;

import java.awt.Dimension;
import java.net.URL;

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.HtmlPanel;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;

/**
 * This Panel is used if no panel is set for the given object.
 *
 * @author  UeliKurmann
 * @version 1.0
 * @since   igesture
 */
public class DefaultPanel extends AbstractPanel {

   private static final String HTML_FILE = "html/adminTab.html";


   public DefaultPanel(Controller controller, Object obj) {
      super(controller);
      init(obj);
   }

   /**
    * Init the panel
    * @param obj
    */
   private void init(Object obj) {
      setTitle(TitleFactory.createStaticTitle(obj.toString()));

      URL path = this.getClass().getClassLoader().getResource(HTML_FILE);

      HtmlPanel htmlPanel = new HtmlPanel(path, new Dimension(400, 400));

      setCenter(htmlPanel);
   }


   @Override
   public void refreshUILogic() {

   }
}
