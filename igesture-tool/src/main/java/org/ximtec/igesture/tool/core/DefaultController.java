/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		:   Default implementation of the controller interface.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 09.04.2008       ukurmann	Initial Release
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

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.sigtec.util.Constant;


/**
 * Default implementation of the controller interface.
 * @version 1.0 09.04.2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public abstract class DefaultController implements Controller {

   private static final Logger LOGGER = Logger.getLogger(DefaultController.class
         .getName());

   private Map<String, Method> commandMethods;

   private List<Controller> controllers;


   public DefaultController() {
      controllers = new ArrayList<Controller>();

      commandMethods = new HashMap<String, Method>();
      for (Method method : this.getClass().getDeclaredMethods()) {
         if (method.isAnnotationPresent(ExecCmd.class)) {
            commandMethods.put(method.getAnnotation(ExecCmd.class).name(),
                  method);
         }
      }
   }


   /*
    * (non-Javadoc)
    * 
    * @see
    * org.ximtec.igesture.tool.core.Controller#addController(org.ximtec.igesture
    * .tool.core.Controller)
    */
   @Override
   public void addController(Controller controller) {
      controllers.add(controller);
   }


   /*
    * (non-Javadoc)
    * 
    * @see org.ximtec.igesture.tool.core.Controller#getControllers()
    */
   @Override
   public List<Controller> getControllers() {
      return controllers;
   }


   /*
    * (non-Javadoc)
    * 
    * @see org.ximtec.igesture.tool.core.Controller#removeAllController()
    */
   @Override
   public void removeAllControllers() {
      controllers.clear();
   }


   @Override
   public void removeController(Controller controller) {
      controllers.remove(controller);
   }


   @Override
   public void execute(Command command) {

      if (command != null && command.getCommand() != null) {

         if (!invokeCommand(command)) {
            LOGGER.warning("Command not handled. '" + command.getCommand()
                  + Constant.SINGLE_QUOTE);

            for (Controller controller : controllers) {
               controller.execute(command);
            }
         }
      }
      else {
         LOGGER.warning("Command not set.");
      }
   }


   @Override
   public void propertyChange(PropertyChangeEvent event) {
      for (Controller controller : getControllers()) {
         controller.propertyChange(event);
      }

   }


   /**
    * Invokes a command
    * @param object
    * @param cmd
    * @return
    */
   protected boolean invokeCommand(Command cmd) {
      LOGGER.info("Invoke " + cmd);
      Method method = commandMethods.get(cmd.getCommand());
      if (method != null) {
         try {
            if (!method.isAccessible()) {
               method.setAccessible(true);
            }
            if(method.getParameterTypes().length == 0){
               method.invoke(this);
            }else{
               method.invoke(this, cmd);
            }
            LOGGER.info("Command invoked. " + cmd.getCommand());
            return true;
         }
         catch (Exception e) {
            LOGGER.warning("Could not execute command. " + cmd.getCommand());
         }
      }else{
         LOGGER.warning("Command not defined. " + cmd.getCommand());
      }

      return false;
   }

}
