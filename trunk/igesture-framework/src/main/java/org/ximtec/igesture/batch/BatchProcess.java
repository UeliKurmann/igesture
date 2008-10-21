/*
 * @(#)BatchProcess.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	This class provides the logic of the batch process 
 * 					for testing algorithm configurations.		
 *	
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 20, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.util.Constant;
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
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.util.XMLTool;


/**
 * This class provides the logic of the batch process for testing algorithm
 * configurations.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchProcess implements Callable<BatchResultSet>{

   private static final Logger LOGGER = Logger.getLogger(AlgorithmFactory.class
         .getName());

   private static final String NUMBER_CONFIGURATIONS = "Number of configurations: ";

   private BatchProcessContainer batchProcessContainer;

   private List<Configuration> configurations;

   TestSet testSet;

   List<GestureSet> sets;


   /**
    * Constructs a new batch process.
    * 
    * @param file the XML file with the configuration.
    */
   public BatchProcess(BatchProcessContainer container) {
      this.batchProcessContainer = container;
      this.configurations = createConfigurations(batchProcessContainer
            .getAlgorithms());

      this.sets = batchProcessContainer.getGestureSets();
   }


   /**
    * Adds a test set.
    * 
    * @param testSet the test set to be added.
    */
   public void setTestSet(TestSet testSet) {
      this.testSet = testSet;
   } // setTestSet


   /**
    * Adds a gesture set.
    * 
    * @param set the gesture set to be added.
    */
   public void addGestureSet(GestureSet set) {
      this.sets.add(set);
   } // addGestureSet


   /**
    * Adds a list of gesture sets.
    * 
    * @param sets the list of gesture sets to be added.
    */
   public void addGestureSets(List<GestureSet> sets) {
      this.sets.addAll(sets);
   } // addGestureSets

  
   /**
    * Runs the batch process.
    * 
    * @return the batch result set.
    */
   public BatchResultSet run() {
      BatchResultSet batchResultSet = new BatchResultSet();
      LOGGER.log(Level.INFO, NUMBER_CONFIGURATIONS + configurations.size());
      int counter = 1;

      for (Configuration config : configurations) {
         config.addGestureSets(sets);
         BatchResult batchResult = new BatchResult(testSet, config);

         try {
            batchResult.setStartTime();
            Algorithm algorithm;
            algorithm = AlgorithmFactory.createAlgorithm(config);

            for (Gesture<?> sample : testSet.getSamples()) {
               ResultSet resultSet = algorithm.recognise(sample);

               if (resultSet.isEmpty()) {

                  if (sample.getName().equals(TestSet.NOISE)) {
                     batchResult.incRejectCorrect(sample.getName());
                  }
                  else {
                     batchResult.incRejectError(sample.getName());
                  }

               }
               else {

                  if (resultSet.getResult().getGestureClassName().equals(
                        sample.getName())) {
                     batchResult.incCorrect(sample.getName());
                  }
                  else {
                     batchResult.incError(sample.getName());
                  }

               }

            }

            batchResult.setEndTime();
            batchResultSet.addResult(batchResult);
         }
         catch (final AlgorithmException e) {
            LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         }

         LOGGER.info((double)counter / configurations.size() * 100
               + Constant.PERCENTAGE);
         counter++;
      }

      return batchResultSet;
   } // run


   /**
    * Creates a list of configurations. This method permutes all possible
    * parameters and creates configuration instances. For the permutation part a
    * recursive method is invoked. For each permutation a configuration instance
    * is created.
    * 
    * @param algorithms the BatchAlgorithm instances.
    * @return a list of configurations.
    */
   public static List<Configuration> createConfigurations(
         List<BatchAlgorithm> algorithms) {
      final List<Configuration> result = new ArrayList<Configuration>();

      for (final BatchAlgorithm algorithm : algorithms) {
         final Configuration config = new Configuration();
         config.addAlgorithm(algorithm.getName());
         permuteParameters(algorithm.getName(), algorithm.getParameters(), 0,
               config, result);
      }

      return result;
   } // createConfigurations


   /**
    * Creates a list of configurations.
    * 
    * @param file the file containing the configurations.
    * @return the newly created configurations.
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
   } // createConfigurations


   /**
    * A recursive method which iterates through all possible permutations.
    * 
    * @param algorithm the name of the algorithm.
    * @param parameters the BatchParameter instance.
    * @param index the index (position) of the parameter.
    * @param configuration the current configuration.
    * @param configurations the list of all generated configurations.
    */
   private static void permuteParameters(String algorithm,
         List<BatchParameter> parameters, int index,
         Configuration configuration, List<Configuration> configurations) {

      /**
       * Abort Condition. The last parameter is reached so one configuration is
       * complete and can be added to the list.
       */
      if (index == parameters.size()) {
         configurations.add((Configuration)configuration.clone());
         return;
      }

      final BatchParameter param = parameters.get(index);
      processSimpleParameter(param, configuration, algorithm, parameters, index,
            configurations);
      processPowerSetParameter(param, configuration, algorithm, parameters,
            index, configurations);
      processSequenceParameter(param, configuration, algorithm, parameters,
            index, configurations);
      processForLoopParameter(param, configuration, algorithm, parameters,
            index, configurations);
   } // permuteParameters


   /**
    * Processes simple value parameters.
    * 
    * @param param the batch parameter.
    * @param configuration the configuration.
    * @param algorithm the algorithm to be used.
    * @param parameters the batch parameters to be used.
    * @param index
    */
   private static void processSimpleParameter(BatchParameter param,
         Configuration configuration, String algorithm,
         List<BatchParameter> parameters, int index,
         List<Configuration> configurations) {

      if (param.getValue() != null) {
         final BatchValue value = param.getValue();
         final Configuration conf = (Configuration)configuration.clone();
         conf
               .addParameter(algorithm, param.getName(), value
                     .getValue());
         permuteParameters(algorithm, parameters, index + 1, conf,
               configurations);
      }

   } // processSimpleParameter


   /**
    * Processes power set parameters.
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
            final Configuration conf = (Configuration)configuration.clone();
            conf.addParameter(algorithm, param.getName(), value);
            permuteParameters(algorithm, parameters, index + 1, conf,
                  configurations);
         }

      }

   } // processPowerSetParameter


   /**
    * Processes sequence parameters.
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
            final Configuration conf = (Configuration)configuration.clone();
            conf.addParameter(algorithm, param.getName(), value);
            permuteParameters(algorithm, parameters, index + 1, conf,
                  configurations);
         }

      }

   } // processPowerSetParameter


   /**
    * Processes for loop parameters.
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
            final Configuration conf = (Configuration)configuration.clone();
            conf.addParameter(algorithm, param.getName(), value);
            permuteParameters(algorithm, parameters, index + 1, conf,
                  configurations);
         }

      }

   } // processForLoopParameter


   @Override
   public BatchResultSet call() throws Exception {
      return run();
   }

}
