/*
 * @(#)EventHandler.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Interface for EventHandler. The EventManager executes
 *  				the run method if an event is fired. 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.event;

import org.ximtec.igesture.core.ResultSet;


public interface EventHandler {

   /**
    * Runs the event handler
    * 
    * @param resultSet the result set
    */
   public void run(ResultSet resultSet);
}
