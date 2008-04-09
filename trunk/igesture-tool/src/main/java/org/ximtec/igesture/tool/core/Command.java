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
 * 09.04.2008			ukurmann	Initial Release
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
 * Comment
 * @version 1.0 09.04.2008
 * @author Ueli Kurmann
 */
public class Command {
   
   private String command;
   private Object sender;
   
   public Command(String command){
      this(command, null);
   }
   
   public Command(String command, Object sender){
      this.command = command;
      this.sender = sender;
   }
   
   
   public String getCommand(){
      return command;
   }
   
   public Object getSender(){
      return sender;
   }

}
