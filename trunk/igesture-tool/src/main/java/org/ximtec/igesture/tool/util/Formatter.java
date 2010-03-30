/*
 * @(#)$Id$
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
 * 18.10.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



/**
 * Comment
 * @version 1.0 18.10.2008
 * @author Ueli Kurmann
 */
public class Formatter {
   
   public static void formatButton(JButton button){    
      button.setPreferredSize(new Dimension(160, 25));
//      button.setHorizontalAlignment(SwingConstants.LEFT);
      button.setAlignmentX(Component.LEFT_ALIGNMENT);     
   }
   
   public static void formatTextField(JTextField textField){    
     textField.setPreferredSize(new Dimension(160, 25));
     textField.setHorizontalAlignment(SwingConstants.LEFT);
     textField.setAlignmentX(Component.LEFT_ALIGNMENT);     
  }
}
