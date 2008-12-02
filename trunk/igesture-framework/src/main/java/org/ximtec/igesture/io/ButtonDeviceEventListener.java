/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :  	Interface for devices with a button.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;

/**
 * Interface for devices with a button.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface ButtonDeviceEventListener {

   /**
    * Adds a listener to the button device.
    * 
    * @param listener the listener to be added.
    */
   void addButtonDeviceEventListener(ButtonDeviceHandler listener);


   /**
    * Removes a listener from the button device.
    * 
    * @param listener the listener to be removed.
    */
   void removeButtonDeviceEventListener(ButtonDeviceHandler listener);

}
