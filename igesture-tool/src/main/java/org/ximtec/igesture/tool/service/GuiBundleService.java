/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose      :   Service wrapper for a GuiBundle.          
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date         Who             Reason
 *
 * 23.03.2008   ukurmann        Initial Release
 * 29.10.2008   bsigner         Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.service;

import org.sigtec.graphix.GuiBundle;
import org.ximtec.igesture.tool.locator.Service;


/**
 * Service wrapper for a GuiBundle.
 * 
 * @version 1.0, Mar 2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class GuiBundleService extends GuiBundle implements Service {

   public static final String IDENTIFIER = "guiBundle";


   public GuiBundleService(String resourceBundleName) {
      super(resourceBundleName);
   }


   @Override
   public String getIdentifier() {
      return IDENTIFIER;
   } // getIdentifier

}