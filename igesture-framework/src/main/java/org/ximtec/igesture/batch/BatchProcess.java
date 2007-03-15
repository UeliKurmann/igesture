/*
 * @(#)BatchProcess.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	This class provides the logic of the batch process 
 * 					which tests algorithm configuration				
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

package org.ximtec.igesture.batch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.batch.core.BatchAlgorithm;
import org.ximtec.igesture.batch.core.BatchForValue;
import org.ximtec.igesture.batch.core.BatchParameter;
import org.ximtec.igesture.batch.core.BatchPowerSetValue;
import org.ximtec.igesture.batch.core.BatchSequenceValue;
import org.ximtec.igesture.batch.core.BatchValue;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.util.XMLTool;

public class BatchProcess {

	private static final Logger LOGGER = Logger
			.getLogger(AlgorithmFactory.class.getName());
	private BatchProcessContainer batchProcessContainer;

	private List<Configuration> configurations;

	TestSet testSet;

	List<GestureSet> sets;

	/**
	 * Constructor
	 * 
	 * @param file
	 *            the XML file with the configration
	 */
	public BatchProcess(File file) {
		this.batchProcessContainer = XMLTool.importBatchProcessContainer(file);
		this.configurations = createConfigurations(batchProcessContainer
				.getAlgorithms());

		this.sets = batchProcessContainer.getGestureSets();
	}

	/**
	 * Adds a test set
	 * 
	 * @param testSet
	 *            the test set to be added
	 */
	public void setTestSet(TestSet testSet) {
		this.testSet = testSet;
	}

	/**
	 * Adds a gesture set
	 * 
	 * @param set
	 *            the gesture set to be added
	 */
	public void addGestureSet(GestureSet set) {
		this.sets.add(set);
	}

	/**
	 * Adds a gesture set list
	 * 
	 * @param sets
	 *            the gesture set to be added
	 */
	public void addGestureSets(List<GestureSet> sets) {
		this.sets.addAll(sets);
	}

	/**
	 * Runs the batch process
	 * 
	 * @return
	 */
	public BatchResultSet run() {
		final BatchResultSet batchResultSet = new BatchResultSet();
		System.out
				.println("Number of Configurations: " + configurations.size());
		int counter = 1;
		for (final Configuration config : configurations) {
			config.addGestureSets(sets);
			final BatchResult batchResult = new BatchResult(testSet, config);

			try {
				batchResult.setStartTime();
				Algorithm algorithm;
				algorithm = AlgorithmFactory.createAlgorithm(config);
				for (final GestureSample sample : testSet.getSamples()) {
					final ResultSet resultSet = algorithm.recognise(sample
							.getNote());

					if (resultSet.isEmpty()) {
						if (sample.getName().equals(TestSet.NOISE)) {
							batchResult.incRejectCorrect(sample.getName());
						} else {
							batchResult.incRejectError(sample.getName());
						}
					} else {
						if (resultSet.getResult().getGestureClassName().equals(
								sample.getName())) {
							batchResult.incCorrect(sample.getName());
						} else {
							batchResult.incError(sample.getName());
						}
					}
				}
				batchResult.setEndTime();
				batchResultSet.addResult(batchResult);
			} catch (final AlgorithmException e) {
				e.printStackTrace();
			}

			LOGGER.info((double) counter / configurations.size() * 100 + "%");
			counter++;
		}
		return batchResultSet;
	}

	/**
	 * Creates a list of configuration. this method permutes all possible
	 * parameters and creates configuration instances. For the permutation part
	 * a recursive method is invoked. For each permutation a configuration
	 * instance is created.
	 * 
	 * @param algorithms
	 *            the BatchAlgorithm instances
	 * @return a list of configurations
	 */
	public static List<Configuration> createConfigurations(
			List<BatchAlgorithm> algorithms) {
		final List<Configuration> result = new ArrayList<Configuration>();
		for (final BatchAlgorithm algorithm : algorithms) {
			final Configuration config = new Configuration();
			config.addAlgorithm(algorithm.getName());
			permuteParameters(algorithm.getName(), algorithm.getParameters(),
					0, config, result);
		}
		return result;
	}

	/**
	 * Creates a list of configurations
	 * 
	 * @param file
	 * @return
	 */
	public static List<Configuration> createConfigurations(File file) {
		final BatchProcessContainer container = XMLTool
				.importBatchProcessContainer(file);
		final List<Configuration> configurations = createConfigurations(container
				.getAlgorithms());

		// postprocess gesture sets
		for (final Configuration config : configurations) {
			for (final GestureSet set : container.getGestureSets()) {
				config.addGestureSet(set);
			}
		}

		return configurations;
	}

	/**
	 * A recusive method which iteratates through all possible permutations.
	 * 
	 * @param algorithm
	 *            the name of the algorithm
	 * @param parameters
	 *            the BatchParameter instance
	 * @param index
	 *            the index of the parameter (ith Parameter)
	 * @param configuration
	 *            the current configuration
	 * @param configurations
	 *            the list of all generated configurations
	 */
	private static void permuteParameters(String algorithm,
			List<BatchParameter> parameters, int index,
			Configuration configuration, List<Configuration> configurations) {

		/**
		 * Abort Condition. The last paramter is reached so one configuration is
		 * complete and can be added to the list.
		 */
		if (index == parameters.size()) {
			configurations.add((Configuration) configuration.clone());
			return;
		}

		final BatchParameter param = parameters.get(index);
		processSimpleParameter(param, configuration, algorithm, parameters,
				index, configurations);
		processPowerSetParameter(param, configuration, algorithm, parameters,
				index, configurations);
		processSequenceParameter(param, configuration, algorithm, parameters,
				index, configurations);
		processForLoopParameter(param, configuration, algorithm, parameters,
				index, configurations);
	}

	/**
	 * Process simple value parameters
	 * 
	 * @param param
	 * @param configuration
	 * @param algorithm
	 * @param parameters
	 * @param index
	 */
	private static void processSimpleParameter(BatchParameter param,
			Configuration configuration, String algorithm,
			List<BatchParameter> parameters, int index,
			List<Configuration> configurations) {
		if (param.getValue() != null) {
			final BatchValue value = param.getValue();
			final Configuration conf = (Configuration) configuration.clone();
			conf.addAlgorithmParameter(algorithm, param.getName(), value
					.getValue());
			permuteParameters(algorithm, parameters, index + 1, conf,
					configurations);
		}
	}

	/**
	 * process power set parameters
	 * 
	 * @param param
	 * @param configuration
	 * @param algorithm
	 * @param parameters
	 * @param index
	 * @param configurations
	 */
	private static void processPowerSetParameter(BatchParameter param,
			Configuration configuration, String algorithm,
			List<BatchParameter> parameters, int index,
			List<Configuration> configurations) {
		if (param.getPermutationValue() != null) {
			final BatchPowerSetValue values = param.getPermutationValue();
			for (final String value : values.getValues()) {
				final Configuration conf = (Configuration) configuration
						.clone();
				conf.addAlgorithmParameter(algorithm, param.getName(), value);
				permuteParameters(algorithm, parameters, index + 1, conf,
						configurations);
			}
		}
	}

	/**
	 * process sequence parameters
	 * 
	 * @param param
	 * @param configuration
	 * @param algorithm
	 * @param parameters
	 * @param index
	 * @param configurations
	 */
	private static void processSequenceParameter(BatchParameter param,
			Configuration configuration, String algorithm,
			List<BatchParameter> parameters, int index,
			List<Configuration> configurations) {
		if (param.getSequenceValue() != null) {
			final BatchSequenceValue values = param.getSequenceValue();
			for (final String value : values.getValues()) {
				final Configuration conf = (Configuration) configuration
						.clone();
				conf.addAlgorithmParameter(algorithm, param.getName(), value);
				permuteParameters(algorithm, parameters, index + 1, conf,
						configurations);
			}
		}
	}

	/**
	 * process for loop parameters
	 * 
	 * @param param
	 * @param configuration
	 * @param algorithm
	 * @param parameters
	 * @param index
	 * @param configurations
	 */
	private static void processForLoopParameter(BatchParameter param,
			Configuration configuration, String algorithm,
			List<BatchParameter> parameters, int index,
			List<Configuration> configurations) {
		if (param.getIncrementalValue() != null) {
			final BatchForValue values = param.getIncrementalValue();
			for (final String value : values.getValues()) {
				final Configuration conf = (Configuration) configuration
						.clone();
				conf.addAlgorithmParameter(algorithm, param.getName(), value);
				permuteParameters(algorithm, parameters, index + 1, conf,
						configurations);
			}
		}
	}

}
