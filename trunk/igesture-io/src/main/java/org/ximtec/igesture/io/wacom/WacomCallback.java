/*
 * @(#)$Id$
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  Signature for a wacom callback function
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Jan 8, 2008		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.wacom;

import org.ximtec.igesture.io.wacom.Wintab32.ORIENTATION;
import org.ximtec.igesture.io.wacom.Wintab32.ROTATION;



/**
 * Signature for a wacom callback function
 * @version 1.0 Jan 8, 2008
 * @author Michele Croci, mcroci@gmail.com
 */
public interface WacomCallback {
   
   public void callbackFunction(int x, int y, int z, int pkStatus, int npress, int tpress, long timeStamp,
         ORIENTATION orientation, ROTATION rotation, int button);

 //  public void callbackFunction();
}
