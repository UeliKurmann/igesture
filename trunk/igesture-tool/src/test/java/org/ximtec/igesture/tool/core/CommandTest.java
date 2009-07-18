package org.ximtec.igesture.tool.core;

import junit.framework.Assert;

import org.junit.Test;

public class CommandTest {
  
  @Test
  public void testSimple(){
    String commandString = "cmd1";
    Command command = new Command(commandString);
    Assert.assertEquals(commandString, command.getCommand());
    
  }
  
  @Test
  public void testComplex(){
    String commandString = "cmd1";
    String commandSender = "sender1";
    Command command = new Command(commandString, commandSender);
    Assert.assertEquals(commandString, command.getCommand());
    Assert.assertEquals(commandSender, command.getSender());
  }

}
