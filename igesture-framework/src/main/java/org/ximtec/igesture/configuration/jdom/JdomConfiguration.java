/*
 * @(#)JdomConfiguration.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	XML support for the Configuration class.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.configuration.jdom;

import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.jdom.JdomGestureSet;


/**
 * XML support for the Configuration class.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class JdomConfiguration extends Element {

   public static final String ROOT_TAG = "configuration";

   public static final String ALGORITHM_TAG = "algorithm";

   public static final String PARAMETER_TAG = "parameter";

   public static final String SETS_TAG = "sets";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String PARAMETER_ACCURACY = "accuracy";

   public static final String PARAMETER_RESULT_SET_SIZE = "resultSetSize";
   public static final String UUID_ATTRIBUTE = "id";

   public JdomConfiguration(Configuration configuration) {
      super(ROOT_TAG);
      
      setAttribute(NAME_ATTRIBUTE, configuration.getName());
      setAttribute(UUID_ATTRIBUTE, configuration.getId());


      for (String algorithmName : configuration.getAlgorithms()) {
         Element element = new Element(ALGORITHM_TAG);
         element.setAttribute(NAME_ATTRIBUTE, algorithmName);
         
         Map<String, String> parameters = configuration
               .getParameters(algorithmName);

         for (String parameterName : parameters.keySet()) {
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
      
      final String name = configurationElement.getAttributeValue(NAME_ATTRIBUTE);
      final String uuid = configurationElement.getAttributeValue(UUID_ATTRIBUTE);
      configuration.setName(name);
      configuration.setId(uuid);
    

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
            configuration.addParameter(algorithmName, parameterList[0],
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
