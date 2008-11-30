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

import java.util.EnumSet;

import org.ximtec.igesture.io.mouse.MouseUtils.MouseButton;


/**
 * Signature for a mouse callback function
 * @version 1.0 Jan 17, 2008
 * @author Michele Croci, mcroci@gmail.com
 * @author Ueli Kurmann, ueli.kurmann@bbv.ch
 */
public interface MouseEventListener {

   
   /**
    * Receives mouse events
    * @param x
    * @param y
    * @param buttons
    */
   public void mouseEvent(int x, int y, EnumSet<MouseButton> buttons);
      
}
