/*
 * @(#)$Id$
 *
 * Author		:	Beat Signer, signer@inf.ethz.ch
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					bsigner		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.util;

import org.sigtec.graphix.GuiBundle;
import org.sigtec.graphix.GuiTool;


/**
 * Comment
 * @version 1.0 25.06.2008
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GuiBundleTool {

   /**
    * The key used to retrieve information from the resource bundle.
    */
   public static final String KEY = "Geco";

   private static final String GUI_BUNDLE_FILE = "geco";

   static {
      GuiTool.addBundle(KEY, GUI_BUNDLE_FILE);
   }


   public static final GuiBundle getBundle() {
      return GuiTool.getBundle(KEY);
   } // getBundle

}
