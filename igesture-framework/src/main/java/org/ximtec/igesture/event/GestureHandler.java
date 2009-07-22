/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		:   Handles and dispatches gesture result sets.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 18.06.2008		ukurmann	Initial Release
 * 26.09.2008       bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.event;

import org.ximtec.igesture.core.ResultSet;


/**
 * Handles and dispatches gesture result sets.
 * 
 * @version 1.0 18.06.2008
 * @author Ueli Kurmann
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface GestureHandler {

   /**
    * Handles and dispatches the given gesture result set.
    * @param resultSet the gesture result set to be handled.
    */
   public void handle(ResultSet resultSet);

}
