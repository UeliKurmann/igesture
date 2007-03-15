/*
 * @(#)CloseApplication.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Example Implementation of an EventHanlder. It will close
 *                  the application.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.event.example;

import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventHandler;


public class CloseApplication implements EventHandler {

   private String name;


   public String getName() {
      return name;
   }


   public CloseApplication(String name) {
      this.name = name;
   }


   public void run(ResultSet resultSet) {
      System.out.println("Application is shutting down...");
      System.exit(0);
   }

}
