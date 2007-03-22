/*
 * @(#)EventHandler.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Interface for event handlers. The event manager
 *                  executes the run method when an event is fired. 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.event;

import org.ximtec.igesture.core.ResultSet;


/**
 * Interface for event handlers. The event manager executes the run method when
 * an event is fired.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface EventHandler {

   /**
    * Runs the event handler.
    * 
    * @param resultSet the result set.
    */
   public void run(ResultSet resultSet);
}
