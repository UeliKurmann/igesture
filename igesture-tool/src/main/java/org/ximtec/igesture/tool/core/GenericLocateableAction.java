/*
 * @(#)$Id: ExitAction.java 689 2009-07-22 00:10:27Z bsigner $
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.core;

import java.awt.event.ActionEvent;

/**
 * A generic locateable action implementation. It executes the command given in
 * a string on the given controller.
 * 
 * @author Ueli Kurmann
 * 
 */
public class GenericLocateableAction extends LocateableAction {

  private Controller controller;
  private String commandString;

  /**
   * Creates a generic locate able action
   * 
   * @param controller
   *          the controller handling the action
   * @param key
   *          the key referencing the resources attached to this action
   * @param commandString
   *          the command string which is executed on the controller
   */
  public GenericLocateableAction(Controller controller, String key, String commandString) {
    super(key, controller.getLocator());
    this.commandString = commandString;
    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    controller.execute(new Command(commandString, event));
  }
}
