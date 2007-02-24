/*
 * @(#)Algorithm.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Interface for Algorithm implementations
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


package org.ximtec.igesture.algorithm;

import org.sigtec.ink.Note;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventManager;


public interface Algorithm {

   /**
    * Initialises the algorithm
    * 
    * @param gestureSet
    */
   public void init(Configuration configuration) throws AlgorithmException;


   /**
    * Runs the recogniser with the given gesture
    * 
    * @param gesture
    * @return
    */
   public ResultSet recognise(Note note);


   /**
    * Registers the Eventmanager. So the algorithm is able to fire results as
    * events.
    * 
    * @param eventManager
    */
   public void addEventManagerListener(EventManager eventManager);


   /**
    * Returns an array of configuration parameters
    * 
    * @return the configuration parameters
    */
   public Enum[] getConfigParameters();


   /**
    * Returns the default value of the given parameter
    * 
    * @param paramaterName the parameter name
    * @return the default value of the parameter
    */
   public String getDefaultParameter(String paramaterName);
}
