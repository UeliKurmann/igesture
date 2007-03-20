/*
 * @(#)JdomConfiguration.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	XML support for Configuration
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


package org.ximtec.igesture.configuration.jdom;

import java.util.HashMap;
import java.util.List;

import org.jdom.Element;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.jdom.JdomGestureSet;


public class JdomConfiguration extends Element {

   public static final String ROOT_TAG = "configuration";

   public static final String ALGORITHM_TAG = "algorithm";

   public static final String PARAMETER_TAG = "parameter";

   public static final String SETS_TAG = "sets";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String PARAMETER_ACCURACY = "accuracy";

   public static final String PARAMETER_RESULT_SET_SIZE = "resultSetSize";


   public JdomConfiguration(Configuration configuration) {
      super(ROOT_TAG);

      for (final String algorithmName : configuration.getAlgorithms()) {
         final Element element = new Element(ALGORITHM_TAG);
         element.setAttribute(NAME_ATTRIBUTE, algorithmName);
         final HashMap<String, String> parameters = configuration
               .getAlgorithmParameters(algorithmName);

         for (final String parameterName : parameters.keySet()) {
            element.addContent(new JdomConfigurationParameter(parameterName,
                  parameters.get(parameterName)));
         }

         this.addContent(element);
      }

      this.addContent(new JdomConfigurationParameter(PARAMETER_ACCURACY, String
            .valueOf(configuration.getMinAccuracy())));
      this.addContent(new JdomConfigurationParameter(PARAMETER_RESULT_SET_SIZE,
            String.valueOf(configuration.getMaxResultSetSize())));

      // sets
      if (!configuration.getGestureSets().isEmpty()) {
         final Element sets = new Element(SETS_TAG);

         for (final GestureSet set : configuration.getGestureSets()) {
            sets.addContent(new JdomGestureSet(set));
         }

         this.addContent(sets);
      }

   }


   @SuppressWarnings("unchecked")
   public static Object unmarshal(Element configurationElement) {
      final Configuration configuration = new Configuration();

      for (final Element algorithmElem : (List<Element>)configurationElement
            .getChildren(ALGORITHM_TAG)) {
         /**
          * set the algorithm name
          */
         final String algorithmName = algorithmElem
               .getAttributeValue(NAME_ATTRIBUTE);
         configuration.addAlgorithm(algorithmName);
         /**
          * add parameters
          */
         for (final Element parameter : (List<Element>)algorithmElem
               .getChildren(JdomConfigurationParameter.ROOT_TAG)) {
            final String[] parameterList = JdomConfigurationParameter
                  .unmarshal(parameter);
            configuration.addAlgorithmParameter(algorithmName, parameterList[0],
                  parameterList[1]);
         }

      }

      for (final Element parameter : (List<Element>)configurationElement
            .getChildren(JdomConfigurationParameter.ROOT_TAG)) {
         final String[] tuple = JdomConfigurationParameter.unmarshal(parameter);

         if (tuple[0].equals(PARAMETER_ACCURACY)) {
            configuration.setMinAccuracy(Double.parseDouble(tuple[1]));
         }
         else if (tuple[0].equals(PARAMETER_RESULT_SET_SIZE)) {
            configuration.setMaxresultSetSize(Integer.parseInt(tuple[1]));
         }

      }

      for (final Element set : (List<Element>)configurationElement
            .getChildren(JdomGestureSet.ROOT_TAG)) {
         configuration.addGestureSet((GestureSet)JdomGestureSet.unmarshal(set));
      }

      return configuration;
   } // unmarshal

}
