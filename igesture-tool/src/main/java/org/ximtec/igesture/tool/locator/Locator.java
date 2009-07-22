/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose      :   Implementation of the locator pattern. A locator
 *                  provides access to services which can be started and
 *                  stopped. A default locator is available trough a
 *                  singleton.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 23.03.2008       ukurmann    Initial Release
 * 29.10.2008       bsigner     Adapted to new RunnableService
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.locator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Implementation of the locator pattern. A locator provides access to services
 * which can be started and stopped. A default locator is available trough a
 * singleton.
 * 
 * @version 1.0, Mar 2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Locator {

   private static Locator defaultLocator;

   private Map<String, Service> services;


   public Locator() {
      services = new HashMap<String, Service>();
   }


   /**
    * Registers a new service. The service's name can later be used to retrieve a
    * specific service.
    * @param service the service to be registered.
    */
   public void addService(Service service) {
      services.put(service.getIdentifier(), service);
   } // addService


   /**
    * Retrieves a specific service.
    * @param identifier the identifier of the service to be retrieved.
    * @return service for a given specifier.
    */
   public Service getService(String identifier) {
      return services.get(identifier);
   } // getService


   /**
    * Retrieves a specific service a casts it to the provided class type.
    * @param identifier the identifier of the service to be retrieved.
    * @param type the class type the returned service should be automatically
    *            casted to.
    * @return service for a given identifier.
    */
   public <T> T getService(String identifier, Class<T> type) {
      return type.cast(services.get(identifier));
   } // getService


   /**
    * Returns all registered services.
    * @return all registered services.
    */
   public List<Service> getServices() {
      return new ArrayList<Service>(services.values());
   } // getServices


   /**
    * Removes a service based on its unique identifier.
    * @param service the service to be removed.
    */
   public void removeService(Service service) {
      services.remove(service.getIdentifier());
   } // removeService


   /**
    * Starts all registered services.
    */
   public void startAll() {
      for (Service service : getServices()) {

         if (service instanceof RunnableService) {
            ((RunnableService)service).start();
         }

      }

   } // startAll


   /**
    * Stops all registered services.
    */
   public void stopAll() {
      for (Service service : getServices()) {

         if (service instanceof RunnableService) {
            ((RunnableService)service).stop();
         }

      }

   } // stopAll


   /**
    * Returns a default locator (singleton).
    * @return the default locator.
    */
   public static Locator getDefault() {
      if (defaultLocator == null) {
         defaultLocator = new Locator();
      }

      return defaultLocator;
   } // getDefault

}
