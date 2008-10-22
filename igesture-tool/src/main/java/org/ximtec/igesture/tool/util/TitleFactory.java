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
 * 08.04.2008			ukurmann	Initial Release
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

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.ximtec.igesture.core.DataObject;
import org.ximtex.igesture.tool.binding.BindingFactory;



/**
 * Comment
 * @version 1.0 08.04.2008
 * @author Ueli Kurmann
 */
public class TitleFactory {
   
   public static JLabel createStaticTitle(String s){
      JLabel title = new JLabel(s);
      title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      title.setFont(FontFactory.getArialBold(24));
      return title;
   }

   public static JLabel createDynamicTitle(DataObject obj, String property){
      JLabel title = new JLabel();
      BindingFactory.createInstance(title, obj, property);
      title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      title.setFont(FontFactory.getArialBold(24));
      return title;
   }

}
