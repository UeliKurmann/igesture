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

package org.ximtec.igesture.tool.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.Visitor;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.storage.IStorageManager;


public class StorageManagerProxy implements InvocationHandler {

   private StorageManager storageManager;

   private Visitor visitor;


   private StorageManagerProxy(StorageManager manager, Visitor visitor) {
      this.storageManager = manager;
      this.visitor = visitor;
   }


   @Override
   public Object invoke(Object proxy, Method method, Object[] args)
         throws Throwable {

      Object result = null;

      // FIXME
      if (args != null && args.length > 0) {
         if (args[0] instanceof DataObject) {
            ((DataObject)args[0]).accept(visitor);
         }
      }

      try {
         result = method.invoke(storageManager, args);
      }
      catch (InvocationTargetException e) {

      }
      catch (Exception e) {

      }

      if (result instanceof DataObject) {
         ((DataObject)result).accept(visitor);
      }
      else if (result instanceof List) {
         for (Object listElement : (List< ? >)result) {
            if (listElement instanceof DataObject) {
               ((DataObject)result).accept(visitor);
            }
         }
      }

      return result;

   }


   public static IStorageManager newInstance(StorageManager storageManager,
         Visitor visitor) {
      return (IStorageManager)Proxy.newProxyInstance(StorageManager.class
            .getClassLoader(), StorageManager.class.getInterfaces(),
            new StorageManagerProxy(storageManager, visitor));
   }

}
