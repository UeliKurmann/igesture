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

package org.ximtec.igesture.tool.util;

import java.awt.Font;


public class FontFactory {

   private static final String HELVETICA = "Helvetica";
   private static final String ARIAL = "Arial";


   public static Font getHelveticaPlain(int size) {
      return new Font(HELVETICA, Font.PLAIN, size);
   }


   public static Font getArialPlain(int size) {
      return new Font(ARIAL, Font.PLAIN, size);
   }


   public static Font getArialBold(int size) {
      return new Font(ARIAL, Font.BOLD, size);
   }

}
