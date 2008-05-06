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

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;


public class GestureSetsPanel extends AbstractPanel {

   GestureSetList rootSet;


   public GestureSetsPanel(Controller controller, GestureSetList rootSet) {
      this.rootSet = rootSet;
      init();
   }


   private void init() {
      String title = ComponentFactory.getGuiBundle().getName(GestureConstants.GESTURE_SETS_PANEL_TITLE);
      setTitle(TitleFactory.createStaticTitle(title));
      Image image;
      try {
         image = ImageIO.read(GestureSetsPanel.class.getClassLoader().getResourceAsStream("image/dataStructure.png"));
         JLabel label = new JLabel(new ImageIcon(image));
         setCenter(label);
      }
      catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      // add information about available gesture sets
      
   }

}
