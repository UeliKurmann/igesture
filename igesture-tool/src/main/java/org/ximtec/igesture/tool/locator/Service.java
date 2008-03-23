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


package org.ximtec.igesture.tool.locator;

/**
 * Interface of a Service which can be located by the Locator.
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public interface Service {

   /**
    * Returns the identifier of the service.
    * @return
    */
   String getIdentifier();


   /**
    * Starts the services.
    */
   void start();


   /**
    * Shuts the service down.
    */
   void stop();


   /**
    * Resets the service.
    */
   void reset();

}
