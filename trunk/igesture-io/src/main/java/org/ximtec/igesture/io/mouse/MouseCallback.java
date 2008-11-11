/*
 * @(#)$Id$
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Signature for a mouse callback function
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Jan 17, 2008      crocimi     Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.mouse;


/**
 * Signature for a mouse callback function
 * @version 1.0 Jan 17, 2008
 * @author Michele Croci, mcroci@gmail.com
 */
public interface MouseCallback {
   
   public void callbackFunction(int x, int y, boolean buttonPressed);
   
   public int getMouseButton();
   
}
