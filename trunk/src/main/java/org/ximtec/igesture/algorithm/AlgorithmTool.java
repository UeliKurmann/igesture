/*
 * @(#)AlgorithmTool.java	1.0   Dec 11, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Static helper methods used by different algorithms
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
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm;

import java.util.HashMap;

import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;


/**
 * Comment
 * 
 * @version 1.0 Dec 11, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class AlgorithmTool {

   /**
    * Returns a string parameter value. given is a parameter name and two maps
    * with name/value parameters. if the parameter is not available in the first
    * set, the default value is returned.
    * 
    * @param parameter the parameter name
    * @param parameters the map with parameter name/value pairs
    * @param defaultParameters the map with default parameter name/value pairs
    * @return the parameter value
    */
   public static String getParameterValue(String parameter,
         HashMap<String, String> parameters,
         HashMap<String, String> defaultParameters) {
      String result = parameters.get(parameter);
      if (result == null) {
         result = defaultParameters.get(parameter);
      }
      return result;
   }


   /**
    * Rutrns a Double parameter
    * 
    * @see AlgorithmTool.getParameterValue
    * @param parameter the parameter name
    * @param parameters the map with parameter name/value pairs
    * @param defaultParameters the map with default parameter name/value pairs
    * @return the parameter value
    */
   public static double getDoubleParameterValue(String parameter,
         HashMap<String, String> parameters,
         HashMap<String, String> defaultParameters) {
      return Double.parseDouble(getParameterValue(parameter, parameters,
            defaultParameters));
   }


   /**
    * Returns an Int parameter
    * 
    * @see AlgorithmTool.getParameterValue
    * @param parameter the parameter name
    * @param parameters the map with parameter name/value pairs
    * @param defaultParameters the map with default parameter name/value pairs
    * @return the parameter value
    */
   public static int getIntParameterValue(String parameter,
         HashMap<String, String> parameters,
         HashMap<String, String> defaultParameters) {
      return Integer.parseInt(getParameterValue(parameter, parameters,
            defaultParameters));
   }


   /**
    * Returns true if all Gesture Classes of the Gesture Set has the required
    * descriptor. As soon that one Class does not fullfill the requirements the
    * result is false.
    * 
    * @param set the gesture set to test
    * @param descriptorType the descriptor type
    * @return
    */
   public static boolean verifyDescriptor(GestureSet set,
         Class< ? extends Descriptor> descriptorType) {
      for (final GestureClass gestureClass : set.getGestureClasses()) {
         if (gestureClass.getDescriptor(descriptorType) == null) {
            return false;
         }
      }
      return true;
   }

}
