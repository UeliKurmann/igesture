/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		:   Command to be executed by the responsible controller.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 09.04.2008		ukurmann	Initial Release
 * 29.10.2008       bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.core;

/**
 * Command to be executed by the responsible controller.
 * @version 1.0 09.04.2008
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Command {

   private String command;
   private Object sender;


   public Command(String command) {
      this(command, null);
   }


   public Command(String command, Object sender) {
      this.command = command;
      this.sender = sender;
   }


   /**
    * Returns the command.
    * @return the command.
    */
   public String getCommand() {
      return command;
   } // getCommand


   /**
    * Returns the sender.
    * @return the sender.
    */
   public Object getSender() {
      return sender;
   } // getSender

}
