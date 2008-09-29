/*
 * @(#)DefaultAlgorithm.java	1.0   Dec 11, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Default implementation of the algorithm interface.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 11, 2006     ukurmann	Initial Release
 * Mar 15, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.GestureHandler;


/**
 * Default implementation of the algorithm interface.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public abstract class DefaultAlgorithm implements Algorithm {

   Set<GestureHandler> gestureHandlers = new HashSet<GestureHandler>();

   protected static Map<String, String> DEFAULT_CONFIGURATION = new HashMap<String, String>();


   public void addGestureHandler(GestureHandler gestureHandler) {
      gestureHandlers.add(gestureHandler);
   } // addGestureHandler


   public void removeGestureHandler(GestureHandler gestureHandler) {
      gestureHandlers.remove(gestureHandler);
   } // removeGestureHandler


   /**
    * Fires an event.
    * 
    * @param resultSet the result set to be used as an argument for the fire
    *            event.
    */
   protected void fireEvent(ResultSet resultSet) {
      for (GestureHandler gestureHandler : gestureHandlers) {
         gestureHandler.handle(resultSet);
      }

   } // fireEvent


   /**
    * Returns the value of the default parameter.
    * 
    * @param parameterName the name of the parameter whose default value has to
    *            be returned.
    * @return the value of a specific default parameter.
    */
   public String getDefaultParameterValue(String parameterName) {
      return DEFAULT_CONFIGURATION.get(parameterName);
   } // getDefaultParameterValue

}
