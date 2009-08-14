/*
 * @(#)$Id: Mapping.java 689 2009-07-22 00:10:27Z bsigner $
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   EDT Proxy. Executes all methods in the EDT.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Aug 14, 2008     ukurmann    Initial Release
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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

public class EdtProxy implements InvocationHandler {
  
  private static final Logger LOGGER = Logger.getLogger(EdtProxy.class.getName());

  private Object view;

  private EdtProxy(Object view) {
    this.view = view;
  }

  @Override
  public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

    final Object[] results = new Object[1];

    if (SwingUtilities.isEventDispatchThread()) {
      try {
        results[0] = method.invoke(view, args);
      } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Invocation Error. Already in EDT.", e);
      }
    } else {
      SwingUtilities.invokeAndWait(new Runnable() {

        @Override
        public void run() {
          try {
            results[0] = method.invoke(view, args);
          } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Invocation Error.", e);
          }

        }
      });
    }

    return results[0];
  }

  public static <T> T newInstance(T view, Class<T> type) {

    return type.cast(Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(),
        new EdtProxy(view)));
  }

}
