/*
 * @(#)CloseApplication.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Example implementation of an event handler. It will
 *                  close the application.
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


package org.ximtec.igesture.event.example;

import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventHandler;


/**
 * Example implementation of an event handler. It will close the application.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class CloseApplication implements EventHandler {

   private static final String SHUTTING_DOWN = "Application is shutting down...";

   private String name;


   public CloseApplication(String name) {
      this.name = name;
   }


   public String getName() {
      return name;
   } // getName


   public void run(ResultSet resultSet) {
      System.out.println(SHUTTING_DOWN);
      System.exit(0);
   } // run

}