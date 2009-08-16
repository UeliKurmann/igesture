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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.sigtec.util.Constant;
import org.ximtec.igesture.tool.locator.Locator;


/**
 * Default implementation of the controller interface.
 * 
 * @version 1.0 09.04.2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public abstract class DefaultController implements Controller {

   private static final Logger LOGGER = Logger.getLogger(DefaultController.class
         .getName());

   private Map<String, Method> commandMethods;

   private List<Controller> controllers;

   private Controller parent;

   private Map<String, LocateableAction> actions;

   private Locator locator;


   public DefaultController(Controller parentController) {
      controllers = new ArrayList<Controller>();
      actions = new HashMap<String, LocateableAction>();
      this.setParent(parentController);
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
    * @see org.ximtec.igesture.tool.core.Controller#addController(org.ximtec.igesture
    *      .tool.core.Controller)
    */
   @Override
   public void addController(Controller controller) {
      if ((controller.getParent() != null) && (controller.getParent() != this)) {
         throw new RuntimeException("Controller should have only one parent.");
      }
      else {
         controller.setParent(this);
      }

      controllers.add(controller);
   } // addController


   /*
    * (non-Javadoc)
    * 
    * @see org.ximtec.igesture.tool.core.Controller#getControllers()
    */
   @Override
   public List<Controller> getControllers() {
      return controllers;
   } // getControllers


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
   public final void execute(final Command command) {
      if (SwingUtilities.isEventDispatchThread()) {
         invokeInWorkerThread(command);
         return;
      }

      if ((command != null) && (command.getCommand() != null)) {

         if (!invokeCommand(command)) {
            LOGGER.info("Command not handled in " + this.getClass().getName()
                  + ". " + Constant.SINGLE_QUOTE + command.getCommand()
                  + Constant.SINGLE_QUOTE);

            // if command execution fails, try to execute it in the parent
            // controller
            if (parent != null) {
               parent.execute(command);
            }

         }

      }
      else {
         LOGGER.warning("Command " + Constant.SINGLE_QUOTE + command
               + Constant.SINGLE_QUOTE + "not available.");
      }

   } // execute


   /**
    * Invokes a command in the worker thread.
    * 
    * @param command
    */
   private void invokeInWorkerThread(final Command command) {
      LOGGER.info("Dispatch event dispatch thread to a worker thread.");
      Executors.newSingleThreadExecutor().execute(new Runnable() {

         @Override
         public void run() {
            execute(command);
         }

      });
   }


   @Override
   public void propertyChange(final PropertyChangeEvent event) {
      for (final Controller controller : getControllers()) {

         if (SwingUtilities.isEventDispatchThread()) {
            controller.propertyChange(event);
         }
         else {
            SwingUtilities.invokeLater(new Runnable() {

               @Override
               public void run() {
                  controller.propertyChange(event);

               }

            });
         }

      }
   }


   /**
    * Invokes a command
    * 
    * @param object
    * @param command
    * @return
    */
   private boolean invokeCommand(Command command) {
      LOGGER.info("Invoke " + Constant.SINGLE_QUOTE + command
            + Constant.SINGLE_QUOTE);
      Method method = commandMethods.get(command.getCommand());

      if (method != null) {
         try {
            if (!method.isAccessible()) {
               method.setAccessible(true);
            }

            if (method.getParameterTypes().length == 0) {
               method.invoke(this);
            }
            else {
               method.invoke(this, command);
            }

            LOGGER.info("Command invoked. " + command.getCommand());
            return true;
         }
         catch (Exception e) {
            LOGGER.warning("Could not execute command. " + command.getCommand()
                  + e.getMessage());
         }

      }
      else {
         LOGGER.warning("Command not defined. " + command.getCommand());
      }

      return false;
   }


   @Override
   public Controller getParent() {
      return parent;
   }


   @Override
   public void setParent(Controller controller) {
      this.parent = controller;

   }


   @Override
   public LocateableAction getAction(String actionName) {
      return actions.get(actionName);
   }


   public void addAction(String actionName, LocateableAction action) {
      actions.put(actionName, action);
   }


   @Override
   public Locator getLocator() {
      if (locator != null) {
         return locator;
      }
      else if (getParent() != null) {
         return getParent().getLocator();
      }

      return null;
   }


   public void setLocator(Locator locator) {
      this.locator = locator;
   }


   /**
    * Returns true if this is the root controller.
    * 
    * @return
    */
   public boolean isRoot() {
      return (getParent() == null);
   } // isRoot

}
