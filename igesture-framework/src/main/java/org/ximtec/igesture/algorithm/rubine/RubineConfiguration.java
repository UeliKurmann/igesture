/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		:   Configuration for the Rubine algorithm.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 11.06.2008		ukurmann	Initial Release
 * 29.09.2008       bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.rubine;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.util.Constant;
import org.ximtec.igesture.algorithm.AlgorithmTool;
import org.ximtec.igesture.algorithm.feature.F1;
import org.ximtec.igesture.algorithm.feature.F10;
import org.ximtec.igesture.algorithm.feature.F2;
import org.ximtec.igesture.algorithm.feature.F3;
import org.ximtec.igesture.algorithm.feature.F4;
import org.ximtec.igesture.algorithm.feature.F5;
import org.ximtec.igesture.algorithm.feature.F6;
import org.ximtec.igesture.algorithm.feature.F7;
import org.ximtec.igesture.algorithm.feature.F8;
import org.ximtec.igesture.algorithm.feature.F9;
import org.ximtec.igesture.algorithm.feature.Feature;
import org.ximtec.igesture.algorithm.feature.FeatureTool;
import org.ximtec.igesture.configuration.Configuration;


/**
 * Comment
 * @version 1.0 11.06.2008
 * @author Ueli Kurmann
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class RubineConfiguration {

   private static final Logger LOGGER = Logger
         .getLogger(RubineConfiguration.class.getName());

   private static final String DEFAULT_MAHALANOBIS_DISTANCE = "100000";

   private static final String DEFAULT_PROBABILITY = "0.95";

   private static final String DEFAULT_MIN_DISTANCE = "1";

   protected static Map<String, String> DEFAULT_CONFIGURATION = new HashMap<String, String>();

   public enum Config {
      MIN_DISTANCE, FEATURE_LIST, MAHALANOBIS_DISTANCE, PROBABILITY
   }

   /**
    * Set default parameter values
    */
   static {
      /**
       * Parameter default values
       */
      DEFAULT_CONFIGURATION
            .put(Config.MIN_DISTANCE.name(), DEFAULT_MIN_DISTANCE);
      DEFAULT_CONFIGURATION.put(Config.MAHALANOBIS_DISTANCE.name(),
            DEFAULT_MAHALANOBIS_DISTANCE);
      DEFAULT_CONFIGURATION.put(Config.PROBABILITY.name(), DEFAULT_PROBABILITY);
      DEFAULT_CONFIGURATION.put(Config.FEATURE_LIST.name(), F1.class.getName()
            + Constant.COMMA + F2.class.getName() + Constant.COMMA
            + F3.class.getName() + Constant.COMMA + F4.class.getName()
            + Constant.COMMA + F5.class.getName() + Constant.COMMA
            + F6.class.getName() + Constant.COMMA + F7.class.getName()
            + Constant.COMMA + F8.class.getName() + Constant.COMMA
            + F9.class.getName() + Constant.COMMA + F10.class.getName());
      LOGGER.setLevel(Level.SEVERE);
   }

   protected Feature[] featureList;

   protected double minDistance;

   protected double mahalanobisDistance;

   protected double probability;

   protected int minimalNumberOfPoints;


   public RubineConfiguration(Configuration config) {
      init(config);
   }


   private void init(Configuration config) {
      Map<String, String> parameters = config
            .getParameters(RubineAlgorithm.class.getCanonicalName());
      minDistance = AlgorithmTool.getDoubleParameterValue(Config.MIN_DISTANCE
            .name(), parameters, DEFAULT_CONFIGURATION);
      LOGGER.info(Config.MIN_DISTANCE + Constant.COLON_BLANK + minDistance);

      mahalanobisDistance = AlgorithmTool.getDoubleParameterValue(
            Config.MAHALANOBIS_DISTANCE.name(), parameters,
            DEFAULT_CONFIGURATION);
      LOGGER.info(Config.MAHALANOBIS_DISTANCE + Constant.COLON_BLANK
            + mahalanobisDistance);

      String featureNames = AlgorithmTool.getParameterValue(Config.FEATURE_LIST
            .name(), parameters, DEFAULT_CONFIGURATION);
      LOGGER.info(Config.FEATURE_LIST + Constant.COLON_BLANK + featureNames);

      featureList = FeatureTool.createFeatureList(featureNames).toArray(
            new Feature[0]);

      minimalNumberOfPoints = FeatureTool
            .computeMinimalNumberOfRequiredPoints(featureList);
      LOGGER.info("Minimal required points: " + minimalNumberOfPoints);

      probability = AlgorithmTool.getDoubleParameterValue(Config.PROBABILITY
            .name(), parameters, DEFAULT_CONFIGURATION);
      LOGGER.info(Config.PROBABILITY + Constant.COLON_BLANK + probability);
   } // init


   public Feature[] getFeatureList() {
      return featureList;
   } // getFeatureList


   public double getMinDistance() {
      return minDistance;
   } // getMinDistance


   public double getMahalanobisDistance() {
      return mahalanobisDistance;
   } // getMahalanobisDistance


   public double getProbability() {
      return probability;
   } // getProbability


   public int getMinimalNumberOfPoints() {
      return minimalNumberOfPoints;
   } // getMinimalNumberOfPoints


   public int getNumberOfFeatures() {
      return featureList.length;
   } // getNumberOfFeatures


   public static Map<String, String> getDefaultConfiguration() {
      return DEFAULT_CONFIGURATION;
   } // getDefaultConfiguration

}
