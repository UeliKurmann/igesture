/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm;

import java.util.HashMap;
import java.util.Map;


/**
 * Default implementation of the algorithm interface.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public abstract class DefaultAlgorithm implements Algorithm {

   protected static Map<String, String> DEFAULT_CONFIGURATION = new HashMap<String, String>();

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
