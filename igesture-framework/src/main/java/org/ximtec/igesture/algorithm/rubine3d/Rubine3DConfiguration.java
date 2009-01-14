/*
 * @(#)$Id: RubineConfiguration.java 588 2008-10-25 13:09:33Z kurmannu $
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

package org.ximtec.igesture.algorithm.rubine3d;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.util.Constant;
import org.ximtec.igesture.algorithm.AlgorithmTool;
import org.ximtec.igesture.algorithm.rubine.RubineConfiguration;
import org.ximtec.igesture.configuration.Configuration;

public class Rubine3DConfiguration {

	private static final Logger LOGGER = Logger
			.getLogger(Rubine3DConfiguration.class.getName());

	private static final String DEFAULT_XY_WEIGHT = "0.3";
	private static final String DEFAULT_YZ_WEIGHT = "0.4";
	private static final String DEFAULT_ZX_WEIGHT = "0.3";

	protected static Map<String, String> DEFAULT_CONFIGURATION = new HashMap<String, String>();

	public enum Config {
		XY_WEIGHT, YZ_WEIGHT, ZX_WEIGHT
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
		LOGGER.setLevel(Level.SEVERE);
	}

	public Rubine3DConfiguration(Configuration config) {
		init(config);
	}

	private void init(Configuration config) {
		Map<String, String> parameters = config.getParameters(Rubine3DAlgorithm.class.getCanonicalName());
		
	    xyWeight = AlgorithmTool.getDoubleParameterValue(Config.XY_WEIGHT.name(), parameters, DEFAULT_CONFIGURATION);
	    LOGGER.info(Config.XY_WEIGHT + Constant.COLON_BLANK + xyWeight);

	    yzWeight = AlgorithmTool.getDoubleParameterValue(Config.YZ_WEIGHT.name(), parameters, DEFAULT_CONFIGURATION);
	    LOGGER.info(Config.YZ_WEIGHT + Constant.COLON_BLANK + yzWeight);
	    
	    zxWeight = AlgorithmTool.getDoubleParameterValue(Config.ZX_WEIGHT.name(), parameters, DEFAULT_CONFIGURATION);
	    LOGGER.info(Config.ZX_WEIGHT + Constant.COLON_BLANK + zxWeight);		
		
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

}
