/*
 * @(#)$Id$
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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
 * Implements the locator pattern. Services can be located, started and stopped.
 * A default Locator is available through a singleton.
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public class Locator {

   private static Locator defaultLocator;

   private Map<String, Service> services;


   public Locator() {
      services = new HashMap<String, Service>();
   }


   public void addService(Service service) {
      services.put(service.getIdentifier(), service);
   }


   public void removeService(Service service) {
      services.remove(service.getIdentifier());
   }


   public Service getService(String name) {
      return services.get(name);
   }


   @SuppressWarnings("unchecked")
   public <T> T getService(String name, Class<T> type) {
      return (T)services.get(name);
   }


   public List<Service> getServices() {
      return new ArrayList<Service>(services.values());
   }


   public void startAll() {
      for (Service service : getServices()) {
         service.start();
      }
   }


   public void stopAll() {
      for (Service service : getServices()) {
         service.stop();
      }
   }


   public static Locator getDefault() {
      if (defaultLocator == null) {
         defaultLocator = new Locator();
      }
      return defaultLocator;
   }

}
