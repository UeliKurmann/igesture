/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Interface to be implemented by any gesture recognition
 *                  algorithm.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 * 
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Feb 27, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm;

import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.ResultSet;


/**
 * Interface to be implemented by any gesture recognition algorithm.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public interface Algorithm {

   /**
    * Initialises the algorithm.
    * 
    * @param configuration the configuration to be used for the initialisation of
    *            the algorithm.
    * @throws AlgorithmException if there is a problem in the algorithm's
    *             initialisation phase.
    */
   public void init(Configuration configuration) throws AlgorithmException;


   /**
    * Runs the recogniser on a given set of strokes represented by a gesture.
    * 
    * @param gesture the gesture to be recognised.
    * @return the result set containing the recognised gesture classes.
    * @throws AlgorithmException if there was an exception while recognising the
    *             note.
    */
   public ResultSet recognise(Gesture< ? > gesture) throws AlgorithmException;


   /**
    * Returns an array of containing the configuration parameters.
    * 
    * @return the configuration parameters.
    */
   public Enum< ? >[] getConfigParameters();


   /**
    * Returns the default value for a given parameter.
    * 
    * @param parameterName the name of the parameter whose default value has to
    *            be returned.
    * @return the default value for the specified parameter.
    */
   public String getDefaultParameterValue(String parameterName);
   
   /**
    * Get the type of algorithm
    * @see org.ximtec.igesture.util.Constant
    */
   public int getType();
}
