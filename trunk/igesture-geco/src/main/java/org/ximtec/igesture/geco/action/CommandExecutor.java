/*
 * @(#)$Id$
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Class encapsulating the execution of a command.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 6, 2007		crocimi		Initial Release
 * Nov 11, 2008     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.action;

import java.io.IOException;

import org.sigtec.util.SystemTool;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.GestureAction;


/**
 * Class encapsulating the execution of a command.
 * @version 0.9, Dec 6, 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class CommandExecutor implements GestureAction {

   private final static String WIN_CONSOLE = "cmd /C ";

   private String command;


   /**
    * Constructor.
    * 
    * @param command the command to be executed
    */
   public CommandExecutor(String command) {
      this.command = command;
   }


   /**
    * Execute the action
    * 
    */
   public void actionPerformed(ResultSet resultSet) {
      try {
         if ((command != null) && (!command.isEmpty())) {

            if (SystemTool.isWindowsPlatform()) {
               Runtime.getRuntime().exec(WIN_CONSOLE + command);
            }

         }
      }
      catch (IOException ioe) {
         ioe.printStackTrace();
      }

   } // run


   public String getCommand() {
      return command;
   }


   /**
    * Returns a string representation of the command executor.
    * 
    * @return string representation of the command executor.
    */
   public String toString() {
      return command;
   }// toString

}
