/*
 * @(#)CommandExecuter.java	1.0   Dec 6, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  Class encapsulating the exectuion od a command
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 6, 2007		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.UserAction;

import java.io.IOException;

import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventHandler;
import org.ximtec.igesture.io.Win32KeyboardProxy;



/**
 * Comment
 * @version 1.0 Dec 6, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class CommandExecutor implements EventHandler{
   
   
   private String command;
   
   /**
    * Constructor.
    * 
    * @param command the command to be executed
    */
   public CommandExecutor(String command){
      this.command = command;
   }
   
   
   /**
    * Execute the action
    * 
    */
   public void run(ResultSet resultSet) {
      try{
         if ((command!=null)&&(!command.equals("")))
            Runtime.getRuntime().exec(command);
      }
      catch(IOException ioe){
         ioe.printStackTrace();
      }
   } // run
   
   public String getCommand(){
      return command;
   }

   
   /**
    * Returns a description of the action
    * 
    * @return the keys to be pressed
    */
   public String toString(){
      return command;
   }//toString


}
