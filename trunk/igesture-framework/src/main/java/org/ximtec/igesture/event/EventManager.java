/*
 * @(#)EventManager.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	The EventManager holds a set of EventHandler which
 * 					are executed after an Event was fired. 
 * 					The EventManager is registred by Algorithms.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
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
    * Constructor
    */
   public EventManager() {
      eventQueue = new LinkedList<ResultSet>();
      events = new HashMap<String, EventHandler>();
   }


   /**
    * Registers an event
    * 
    * @param event
    */
   public void registerEventHandler(String className, EventHandler event) {
      events.put(className, event);
   }


   /**
    * Register the reject event handler. This event handler is executed if no
    * other handler is used.
    * 
    * @param rejectEventHandler
    */
   public void registerRejectEvent(EventHandler rejectEventHandler) {
      this.rejectEventHandler = rejectEventHandler;
   }


   /**
    * Adds the ResultSet to the queue and starts the handler in an asynchronous
    * mode if it isn't running.
    * 
    * @param resultSet
    */
   public synchronized void fireEvent(ResultSet resultSet) {
      eventQueue.add(resultSet);
      handleEvents();
   }


   /**
    * As long as elements are in the queue they are taken and the action is
    * started. The handler works sequential.
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
            if ((eventHandler = events.get(resultSet.getResult().getName())) != null) {
               eventHandler.run(resultSet);
            }
         }
      }
   }
}
