/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	The event manager holds a set of event handlers which
 * 					are executed after an event has been fired. The event
 *                  manager is registered by algorithms.
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.event;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.ximtec.igesture.core.ResultSet;


/**
 * The event manager holds a set of event handlers which are executed after an
 * event has been fired. The event manager is registered by algorithms.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureActionManager implements GestureHandler {

   private BlockingQueue<ResultSet> gestureQueue;

   /**
    * the list of registered events.
    */
   private HashMap<String, GestureAction> actions;

   /**
    * the event fired if the resultSet is empty
    */
   private GestureAction rejectAction;


   /**
    * Constructs a new event manager.
    */
   public GestureActionManager() {
      gestureQueue = new LinkedBlockingQueue<ResultSet>();
      actions = new HashMap<String, GestureAction>();
      Executors.newSingleThreadExecutor().execute(new Worker());
   }


   /**
    * Registers an event.
    * 
    * @param className the classname for which the event handler has to be
    *            registered.
    * @param event the event to be registered.
    */
   public void registerEventHandler(String className, GestureAction event) {
      actions.put(className, event);
   } // registerEventHandler


   /**
    * UnRegisters an event.
    * 
    * @param className the classname for which the event handler has to be
    *            registered.
    */
   public void unRegisterEventHandler(String className) {
      actions.remove(className);
   } // registerEventHandler


   /**
    * Register the reject event handler. This event handler is executed if no
    * other handler is used.
    * 
    * @param rejectEventHandler the event handler to be registered.
    */
   public void registerRejectEvent(GestureAction rejectEventHandler) {
      this.rejectAction = rejectEventHandler;
   } // registerRejectEvent


   /**
    * Adds the result set to the queue and starts the handler in an asynchronous
    * mode if it is not running.
    * 
    * @param resultSet the result set to be added to the queue.
    */
   public synchronized void handle(ResultSet resultSet) {
      try {
         gestureQueue.put(resultSet);
      }
      catch (InterruptedException e) {
         e.getStackTrace();
      }
   } // fireEvent

   /**
    * As long as elements are in the queue they are taken and the action is
    * started. The handler works sequentially.
    */
   private class Worker implements Runnable {

      public void run() {
         while (true) {
            try {
               final ResultSet resultSet = gestureQueue.take();

               if (resultSet.isEmpty()) {

                  if (rejectAction != null) {
                     rejectAction.actionPerformed(resultSet);
                  }

               }
               else {
                  GestureAction action;

                  if ((action = actions.get(resultSet.getResult()
                        .getGestureClassName())) != null) {
                     action.actionPerformed(resultSet);
                  }
               }
            }
            catch (Exception e) {
               e.getMessage();
            }
         }

      } // handleEvents
   }

}
