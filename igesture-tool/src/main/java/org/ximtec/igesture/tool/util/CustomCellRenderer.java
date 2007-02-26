/*
 * @(#)CustomCellRenderer.java	1.0   Dec 22, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Custom cell renderer.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 22, 2006		ukurmann	Initial Release
 * Feb 26, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;


/**
 * Custom cell renderer.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class CustomCellRenderer implements ListCellRenderer {

   public Component getListCellRendererComponent(JList list, Object value,
         int index, boolean isSelected, boolean cellHasFocus) {
      final Component component = (Component)value;
      component.setBackground(isSelected ? Color.gray : Color.white);
      component.setForeground(isSelected ? Color.white : Color.gray);
      return component;
   } // getListCellRendererComponent

}
