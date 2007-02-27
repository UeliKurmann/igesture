/*
 * @(#)DefaultAlgorithm.java	1.0   Dec 11, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Default implementation of the Algorithm interface
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 2006/12/11		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm;

import java.util.HashMap;

import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventManager;


/**
 * Comment
 * 
 * @version 1.0 Dec 7, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public abstract class DefaultAlgorithm implements Algorithm {

   EventManager eventManager;

   protected static HashMap<String, String> DEFAULT_CONFIGURATION = new HashMap<String, String>();


   public void addEventManagerListener(EventManager eventManager) {
      this.eventManager = eventManager;
   }


   /**
    * Fires an event
    * 
    * @param resultSet
    */
   protected void fireEvent(ResultSet resultSet) {
      if (eventManager != null) {
         eventManager.fireEvent(resultSet);
      }
   }


   /**
    * Returns the value of the default parameter
    */
   public String getDefaultParameterValue(String parameterName) {
      return DEFAULT_CONFIGURATION.get(parameterName);
   }

}
