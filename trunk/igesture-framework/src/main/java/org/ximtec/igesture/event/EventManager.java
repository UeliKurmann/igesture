/*
 * @(#)EventManager.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	The event manager holds a set of event handlers which
 * 					are executed after an event has been fired. The event
 *                  manager is registred by algorithms.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.event;

import java.util.HashMap;
import java.util.LinkedList;

import org.ximtec.igesture.core.ResultSet;


/**
 * The event manager holds a set of event handlers which are executed after an
 * event has been fired. The event manager is registred by algorithms.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class EventManager {

   Thread thread = null;

   private LinkedList<ResultSet> eventQueue;

   /**
    * the list of registred events.
    */
   private HashMap<String, EventHandler> events;

   /**
    * the event fired if the resultSet is empty
    */
   private EventHandler rejectEventHandler;


   /**
    * Constructs a new event manager.
    */
   public EventManager() {
      eventQueue = new LinkedList<ResultSet>();
      events = new HashMap<String, EventHandler>();
   }


   /**
    * Registers an event.
    * 
    * @param className the classname for which the event handler has to be
    *           registered.
    * @param event the event to be registered.
    */
   public void registerEventHandler(String className, EventHandler event) {
      events.put(className, event);
   } // registerEventHandler


   /**
    * Register the reject event handler. This event handler is executed if no
    * other handler is used.
    * 
    * @param rejectEventHandler the event handler to be registered.
    */
   public void registerRejectEvent(EventHandler rejectEventHandler) {
      this.rejectEventHandler = rejectEventHandler;
   } // registerRejectEvent


   /**
    * Adds the result set to the queue and starts the handler in an asynchronous
    * mode if it is not running.
    * 
    * @param resultSet the result set to be added to the queue.
    */
   public synchronized void fireEvent(ResultSet resultSet) {
      eventQueue.add(resultSet);
      handleEvents();
   } // fireEvent


   /**
    * As long as elements are in the queue they are taken and the action is
    * started. The handler works sequentially.
    */
   private synchronized void handleEvents() {
      while (!eventQueue.isEmpty()) {
         final ResultSet resultSet = eventQueue.poll();

         if (resultSet.isEmpty()) {

            if (rejectEventHandler != null) {
               rejectEventHandler.run(resultSet);
            }

         }
         else {
            EventHandler eventHandler;

            if ((eventHandler = events.get(resultSet.getResult()
                  .getGestureClassName())) != null) {
               eventHandler.run(resultSet);
            }

         }

      }

   } // handleEvents

}
