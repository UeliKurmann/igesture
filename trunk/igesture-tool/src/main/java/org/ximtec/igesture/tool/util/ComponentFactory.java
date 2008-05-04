/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 04.05.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sigtec.graphix.GuiBundle;
import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicLabel;
import org.sigtec.graphix.widget.BasicTextField;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;


/**
 * Comment
 * @version 1.0 04.05.2008
 * @author Ueli Kurmann
 */
public class ComponentFactory {

   public static JLabel createLabel(String key) {
      return new BasicLabel(key, getGuiBundle());
   }


   public static JButton createButton(String key, Action action) {
      return new BasicButton(action, key, getGuiBundle());
   }


   public static JTextField createTextField(String key) {
      return new BasicTextField(key, getGuiBundle());
   }


   public static JTextArea createTextArea(String key) {
      return new JTextArea();
   }


   public static GuiBundle getGuiBundle() {
      return Locator.getDefault().getService(GuiBundleService.IDENTIFIER,
            GuiBundleService.class);
   }

}