/*
 * @(#)$Id: Rubine3DConfiguration.java
 *
 * Author		:	Arthur Vogels, arthur.vogels@gmail.com
 *                  
 *
 * Purpose		:   Configuration class for the Rubine3D algorithm.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 05.01.2009		vogelsar	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.algorithm.rubine3d;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sigtec.util.Constant;
import org.ximtec.igesture.algorithm.AlgorithmTool;
import org.ximtec.igesture.algorithm.feature.F1;
import org.ximtec.igesture.algorithm.feature.F10;
import org.ximtec.igesture.algorithm.feature.F11;
import org.ximtec.igesture.algorithm.feature.F12;
import org.ximtec.igesture.algorithm.feature.F13;
import org.ximtec.igesture.algorithm.feature.F14;
import org.ximtec.igesture.algorithm.feature.F15;
import org.ximtec.igesture.algorithm.feature.F16;
import org.ximtec.igesture.algorithm.feature.F17;
import org.ximtec.igesture.algorithm.feature.F18;
import org.ximtec.igesture.algorithm.feature.F19;
import org.ximtec.igesture.algorithm.feature.F2;
import org.ximtec.igesture.algorithm.feature.F21;
import org.ximtec.igesture.algorithm.feature.F3;
import org.ximtec.igesture.algorithm.feature.F4;
import org.ximtec.igesture.algorithm.feature.F5;
import org.ximtec.igesture.algorithm.feature.F6;
import org.ximtec.igesture.algorithm.feature.F7;
import org.ximtec.igesture.algorithm.feature.F8;
import org.ximtec.igesture.algorithm.feature.F9;
import org.ximtec.igesture.algorithm.rubine.RubineAlgorithm;
import org.ximtec.igesture.algorithm.rubine.RubineConfiguration;
import org.ximtec.igesture.algorithm.rubine.RubineConfiguration.Config;
import org.ximtec.igesture.configuration.Configuration;

public class Rubine3DConfiguration {

	private static final Logger LOGGER = Logger
			.getLogger(Rubine3DConfiguration.class.getName());

	private static final String DEFAULT_XY_WEIGHT = "0.2";
	private static final String DEFAULT_YZ_WEIGHT = "0.6";
	private static final String DEFAULT_ZX_WEIGHT = "0.2";

	private static final String DEFAULT_XY_MIN_DISTANCE = "2.0";
	private static final String DEFAULT_XY_MAHALANOBIS_DISTANCE = "400.0";
	private static final String DEFAULT_XY_PROBABILITY = "0.95";
	public static final String DEFAULT_XY_FEATURE_LIST = F1.class.getName()
			+ Constant.COMMA + F2.class.getName() + Constant.COMMA
			+ F3.class.getName() + Constant.COMMA + F4.class.getName()
			+ Constant.COMMA + F5.class.getName() + Constant.COMMA
			+ F6.class.getName() + Constant.COMMA + F7.class.getName()
			+ Constant.COMMA + F8.class.getName() + Constant.COMMA
			+ F9.class.getName() + Constant.COMMA + F10.class.getName()
			+ Constant.COMMA + F11.class.getName() + Constant.COMMA
			+ F12.class.getName() + Constant.COMMA + F13.class.getName()
			+ Constant.COMMA + F14.class.getName() + Constant.COMMA
			+ F15.class.getName() + Constant.COMMA + F16.class.getName()
			+ Constant.COMMA + F17.class.getName() + Constant.COMMA
			+ F18.class.getName() + Constant.COMMA + F19.class.getName()
			+ Constant.COMMA + F21.class.getName();

	private static final String DEFAULT_YZ_MIN_DISTANCE = "3.0";
	private static final String DEFAULT_YZ_MAHALANOBIS_DISTANCE = "800.0";
	private static final String DEFAULT_YZ_PROBABILITY = "0.95";
	private static final String DEFAULT_YZ_FEATURE_LIST = F1.class.getName()
	+ Constant.COMMA + F2.class.getName() + Constant.COMMA
	+ F3.class.getName() + Constant.COMMA + F4.class.getName()
	+ Constant.COMMA + F5.class.getName() + Constant.COMMA
	+ F6.class.getName() + Constant.COMMA + F7.class.getName()
	+ Constant.COMMA + F8.class.getName() + Constant.COMMA
	+ F9.class.getName() + Constant.COMMA + F10.class.getName()
	+ Constant.COMMA + F11.class.getName() + Constant.COMMA
	+ F12.class.getName() + Constant.COMMA + F13.class.getName()
	+ Constant.COMMA + F14.class.getName() + Constant.COMMA
	+ F15.class.getName() + Constant.COMMA + F16.class.getName()
	+ Constant.COMMA + F17.class.getName() + Constant.COMMA
	+ F18.class.getName() + Constant.COMMA + F19.class.getName()
	+ Constant.COMMA + F21.class.getName();
	
	private static final String DEFAULT_ZX_MIN_DISTANCE = "1.0";
	private static final String DEFAULT_ZX_MAHALANOBIS_DISTANCE = "400.0";
	private static final String DEFAULT_ZX_PROBABILITY = "0.95";
	private static final String DEFAULT_ZX_FEATURE_LIST = F1.class.getName()
	+ Constant.COMMA + F2.class.getName() + Constant.COMMA
	+ F3.class.getName() + Constant.COMMA + F4.class.getName()
	+ Constant.COMMA + F5.class.getName() + Constant.COMMA
	+ F6.class.getName() + Constant.COMMA + F7.class.getName()
	+ Constant.COMMA + F8.class.getName() + Constant.COMMA
	+ F9.class.getName() + Constant.COMMA + F10.class.getName()
	+ Constant.COMMA + F11.class.getName() + Constant.COMMA
	+ F12.class.getName() + Constant.COMMA + F13.class.getName()
	+ Constant.COMMA + F14.class.getName() + Constant.COMMA
	+ F15.class.getName() + Constant.COMMA + F16.class.getName()
	+ Constant.COMMA + F17.class.getName() + Constant.COMMA
	+ F18.class.getName() + Constant.COMMA + F19.class.getName()
	+ Constant.COMMA + F21.class.getName();

	protected static Map<String, String> DEFAULT_CONFIGURATION = new HashMap<String, String>();

	public enum Config {
		XY_WEIGHT, YZ_WEIGHT, ZX_WEIGHT, XY_MIN_DISTANCE, XY_FEATURE_LIST, XY_MAHALANOBIS_DISTANCE, XY_PROBABILITY, YZ_MIN_DISTANCE, YZ_FEATURE_LIST, YZ_MAHALANOBIS_DISTANCE, YZ_PROBABILITY, ZX_MIN_DISTANCE, ZX_FEATURE_LIST, ZX_MAHALANOBIS_DISTANCE, ZX_PROBABILITY
	}

	private double xyWeight;
	private double yzWeight;
	private double zxWeight;
	private RubineConfiguration xyConfiguration;
	private RubineConfiguration yzConfiguration;
	private RubineConfiguration zxConfiguration;

	/**
	 * Set default parameter values
	 */
	static {
		/**
		 * Parameter default values
		 */
		DEFAULT_CONFIGURATION.put(Config.XY_WEIGHT.name(), DEFAULT_XY_WEIGHT);
		DEFAULT_CONFIGURATION.put(Config.YZ_WEIGHT.name(), DEFAULT_YZ_WEIGHT);
		DEFAULT_CONFIGURATION.put(Config.ZX_WEIGHT.name(), DEFAULT_ZX_WEIGHT);

		DEFAULT_CONFIGURATION.put(Config.XY_MIN_DISTANCE.name(),
				DEFAULT_XY_MIN_DISTANCE);
		DEFAULT_CONFIGURATION.put(Config.XY_MAHALANOBIS_DISTANCE.name(),
				DEFAULT_XY_MAHALANOBIS_DISTANCE);
		DEFAULT_CONFIGURATION.put(Config.XY_PROBABILITY.name(),
				DEFAULT_XY_PROBABILITY);
		DEFAULT_CONFIGURATION.put(Config.XY_FEATURE_LIST.name(),
				DEFAULT_XY_FEATURE_LIST);

		DEFAULT_CONFIGURATION.put(Config.YZ_MIN_DISTANCE.name(),
				DEFAULT_YZ_MIN_DISTANCE);
		DEFAULT_CONFIGURATION.put(Config.YZ_MAHALANOBIS_DISTANCE.name(),
				DEFAULT_YZ_MAHALANOBIS_DISTANCE);
		DEFAULT_CONFIGURATION.put(Config.YZ_PROBABILITY.name(),
				DEFAULT_YZ_PROBABILITY);
		DEFAULT_CONFIGURATION.put(Config.YZ_FEATURE_LIST.name(),
				DEFAULT_YZ_FEATURE_LIST);

		DEFAULT_CONFIGURATION.put(Config.ZX_MIN_DISTANCE.name(),
				DEFAULT_ZX_MIN_DISTANCE);
		DEFAULT_CONFIGURATION.put(Config.ZX_MAHALANOBIS_DISTANCE.name(),
				DEFAULT_ZX_MAHALANOBIS_DISTANCE);
		DEFAULT_CONFIGURATION.put(Config.ZX_PROBABILITY.name(),
				DEFAULT_ZX_PROBABILITY);
		DEFAULT_CONFIGURATION.put(Config.ZX_FEATURE_LIST.name(),
				DEFAULT_ZX_FEATURE_LIST);

		LOGGER.setLevel(Level.SEVERE);
	}

	public Rubine3DConfiguration(Configuration config) {
		init(config);
	}

	private void init(Configuration config) {
		Map<String, String> parameters = config
				.getParameters(Rubine3DAlgorithm.class.getCanonicalName());
		// Set Rubine3D parameters
		xyWeight = AlgorithmTool.getDoubleParameterValue(Config.XY_WEIGHT
				.name(), parameters, DEFAULT_CONFIGURATION);
		LOGGER.info(Config.XY_WEIGHT + Constant.COLON_BLANK + xyWeight);

		yzWeight = AlgorithmTool.getDoubleParameterValue(Config.YZ_WEIGHT
				.name(), parameters, DEFAULT_CONFIGURATION);
		LOGGER.info(Config.YZ_WEIGHT + Constant.COLON_BLANK + yzWeight);

		zxWeight = AlgorithmTool.getDoubleParameterValue(Config.ZX_WEIGHT
				.name(), parameters, DEFAULT_CONFIGURATION);
		LOGGER.info(Config.ZX_WEIGHT + Constant.COLON_BLANK + zxWeight);

		// Set Rubine parameters per plane RubineConfiguration

		// XY plane
		Configuration xyConf = new Configuration();
		xyConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"MAHALANOBIS_DISTANCE", String.valueOf(AlgorithmTool
						.getDoubleParameterValue(Config.XY_MAHALANOBIS_DISTANCE
								.name(), parameters, DEFAULT_CONFIGURATION)));
		xyConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"PROBABILITY", String.valueOf(AlgorithmTool
						.getDoubleParameterValue(Config.XY_PROBABILITY.name(),
								parameters, DEFAULT_CONFIGURATION)));
		xyConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"MIN_DISTANCE", String.valueOf(AlgorithmTool
						.getDoubleParameterValue(Config.XY_MIN_DISTANCE.name(),
								parameters, DEFAULT_CONFIGURATION)));
		String xyFeatureNames = AlgorithmTool.getParameterValue(
				Config.XY_FEATURE_LIST.name(), parameters,
				DEFAULT_CONFIGURATION);
		xyConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"FEATURE_LIST", xyFeatureNames);

		xyConfiguration = new RubineConfiguration(xyConf);

		// YZ plane
		Configuration yzConf = new Configuration();
		yzConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"MAHALANOBIS_DISTANCE", String.valueOf(AlgorithmTool
						.getDoubleParameterValue(Config.YZ_MAHALANOBIS_DISTANCE
								.name(), parameters, DEFAULT_CONFIGURATION)));
		yzConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"PROBABILITY", String.valueOf(AlgorithmTool
						.getDoubleParameterValue(Config.YZ_PROBABILITY.name(),
								parameters, DEFAULT_CONFIGURATION)));
		yzConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"MIN_DISTANCE", String.valueOf(AlgorithmTool
						.getDoubleParameterValue(Config.YZ_MIN_DISTANCE.name(),
								parameters, DEFAULT_CONFIGURATION)));
		String yzFeatureNames = AlgorithmTool.getParameterValue(
				Config.YZ_FEATURE_LIST.name(), parameters,
				DEFAULT_CONFIGURATION);
		yzConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"FEATURE_LIST", yzFeatureNames);

		yzConfiguration = new RubineConfiguration(yzConf);

		// ZX plane
		Configuration zxConf = new Configuration();
		zxConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"MAHALANOBIS_DISTANCE", String.valueOf(AlgorithmTool
						.getDoubleParameterValue(Config.ZX_MAHALANOBIS_DISTANCE
								.name(), parameters, DEFAULT_CONFIGURATION)));
		zxConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"PROBABILITY", String.valueOf(AlgorithmTool
						.getDoubleParameterValue(Config.ZX_PROBABILITY.name(),
								parameters, DEFAULT_CONFIGURATION)));
		zxConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"MIN_DISTANCE", String.valueOf(AlgorithmTool
						.getDoubleParameterValue(Config.ZX_MIN_DISTANCE.name(),
								parameters, DEFAULT_CONFIGURATION)));
		String zxFeatureNames = AlgorithmTool.getParameterValue(
				Config.ZX_FEATURE_LIST.name(), parameters,
				DEFAULT_CONFIGURATION);
		zxConf.addParameter(RubineAlgorithm.class.getCanonicalName(),
				"FEATURE_LIST", zxFeatureNames);

		zxConfiguration = new RubineConfiguration(zxConf);

	}

	public double getXyWeight() {
		return xyWeight;
	}

	public void setXyWeight(double xyWeight) {
		this.xyWeight = xyWeight;
	}

	public double getYzWeight() {
		return yzWeight;
	}

	public void setYzWeight(double yzWeight) {
		this.yzWeight = yzWeight;
	}

	public double getZxWeight() {
		return zxWeight;
	}

	public void setZxWeight(double zxWeight) {
		this.zxWeight = zxWeight;
	}

	public RubineConfiguration getXyConfiguration() {
		return xyConfiguration;
	}

	public void setXyConfiguration(RubineConfiguration xyConfiguration) {
		this.xyConfiguration = xyConfiguration;
	}

	public RubineConfiguration getYzConfiguration() {
		return yzConfiguration;
	}

	public void setYzConfiguration(RubineConfiguration yzConfiguration) {
		this.yzConfiguration = yzConfiguration;
	}

	public RubineConfiguration getZxConfiguration() {
		return zxConfiguration;
	}

	public void setZxConfiguration(RubineConfiguration zxConfiguration) {
		this.zxConfiguration = zxConfiguration;
	}

	public static Map<String, String> getDefaultConfiguration() {
		return DEFAULT_CONFIGURATION;
	}

}
