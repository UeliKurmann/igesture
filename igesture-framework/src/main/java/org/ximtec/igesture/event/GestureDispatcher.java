/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 18.06.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.event;

import org.ximtec.igesture.core.ResultSet;



/**
 * Comment
 * @version 1.0 18.06.2008
 * @author Ueli Kurmann
 */
public interface GestureDispatcher {
   
   /**
    * Dispatcher
    * @param resultSet
    */
   public void dispatch(ResultSet resultSet);
     
}
