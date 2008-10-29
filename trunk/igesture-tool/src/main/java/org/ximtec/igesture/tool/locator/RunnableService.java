/*
 * @(#)$Id: Service.java 590 2008-10-29 14:58:31Z bsigner $
 *
 * Author		:	Beat Signer, signer@inf.ethz.ch
 *
 * Purpose		:   Special type of service which can be started and
 *                  stopped.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 29.10.2008		bsigner		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.locator;

/**
 * Special type of service which can be started and stopped.
 * @version 1.0 Oct 29, 2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface RunnableService extends Service {

   /**
    * Starts the service.
    */
   void start();


   /**
    * Stops the service.
    */
   void stop();


   /**
    * Resets the service.
    */
   void reset();

}
