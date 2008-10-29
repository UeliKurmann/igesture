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


package org.ximtec.igesture.tool.service;

import org.sigtec.graphix.GuiBundle;
import org.ximtec.igesture.tool.locator.Service;


/**
 * Services Wrapper for GuiBundle.
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public class GuiBundleService extends GuiBundle implements Service {

   public static final String IDENTIFIER = "guiBundle";


   public GuiBundleService(String resourceBundleName) {
      super(resourceBundleName);
   }


   @Override
   public String getIdentifier() {
      return IDENTIFIER;
   }

}