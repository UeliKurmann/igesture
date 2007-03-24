/*
 * @(#)AlgoirthmFactory.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :	The factory used to create algorithms.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 14, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.util.XMLTool;


/**
 * The factory used to create algorithms.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class AlgorithmFactory {

   private static final Logger LOGGER = Logger.getLogger(AlgorithmFactory.class
         .getName());


   /**
    * Creates a list of algorithms and inistialises them with the parameters
    * provided in the configuration object.
    * 
    * @param file the xml file that contains the configuration.
    * @return the list of algorithms.
    * @throws AlgorithmException if the algorithms could not be created.
    */
   public static List<Algorithm> createAlgorithms(File file)
         throws AlgorithmException {
      return createAlgorithms(XMLTool.importConfiguration(file));
   } // createAlgorithms


   /**
    * Creates a list of algorithms and inistialises them with the parameters
    * provided in the configuration object.
    * 
    * @param config the configuration object.
    * @return the list of algorithms.
    * @throws AlgorithmException if the algorithms could not be created.
    */
   public static List<Algorithm> createAlgorithms(Configuration config)
         throws AlgorithmException {
      final List<Algorithm> algorithms = new ArrayList<Algorithm>();
      for (final String algorithmName : config.getAlgorithms()) {
         try {
            final Algorithm algorithm = createAlgorithmInstance(algorithmName);
            algorithm.init(config);
            algorithm.addEventManagerListener(config.getEventManager());
            algorithms.add(algorithm);
         }
         catch (final NullPointerException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new AlgorithmException(
                  AlgorithmException.ExceptionType.Initialisation);
         }
      }
      return algorithms;
   } // createAlgorithms


   /**
    * Instantiates an algorithm with a specific configuration object.
    * 
    * @param file the XML file with the configuration.
    * @return the initialised algorithm.
    * @throws AlgorithmException if the algorithm could not be created.
    */
   public static Algorithm createAlgorithm(File file) throws AlgorithmException {
      return createAlgorithm(XMLTool.importConfiguration(file));
   } // createAlgorithm


   /**
    * Creates a new Algorithm instance.
    * 
    * @param config the XML file that contains the configuration.
    * @param set the XML file with the gesture set.
    * @return the initialised algorithm.
    * @throws AlgorithmException if the algorithm could not be created.
    */
   public static Algorithm createAlgorithm(File config, File set)
         throws AlgorithmException {
      final Configuration configuration = XMLTool.importConfiguration(config);
      final List<GestureSet> sets = XMLTool.importGestureSet(set);
      configuration.addGestureSets(sets);
      return createAlgorithm(configuration);
   } // createAlgorithm


   /**
    * Instantiates an algorithm with a given configuration object.
    * 
    * @param config the configuration of the algorithm.
    * @return the initialised algorithm.
    * @throws AlgorithmException if the algorithm could not be created.
    */
   public static Algorithm createAlgorithm(Configuration config)
         throws AlgorithmException {
      Algorithm algorithm = null;

      try {
         final String algorithmName = config.getAlgorithms().get(0);
         algorithm = createAlgorithmInstance(algorithmName);
         algorithm.init(config);
      }
      catch (final NullPointerException e) {
         LOGGER.log(Level.SEVERE, null, e);
         throw new AlgorithmException(
               AlgorithmException.ExceptionType.Initialisation);
      }
      catch (final IndexOutOfBoundsException e) {
         LOGGER.log(Level.SEVERE, null, e);
         throw new AlgorithmException(
               AlgorithmException.ExceptionType.Initialisation);
      }

      return algorithm;
   } // createAlgorithm


   /**
    * Creates an algorithm instance.
    * 
    * @param className the full qualified name of the algorithm.
    * @return the instance of the algorithm.
    */
   public static Algorithm createAlgorithmInstance(String className) {
      Algorithm algorithm = null;
      try {
         final Class< ? extends Algorithm> clazz = (Class< ? extends Algorithm>)Class
               .forName(className);
         algorithm = (Algorithm)clazz.newInstance();
      }
      catch (final ClassNotFoundException e) {
         LOGGER.log(Level.SEVERE, null, e);
      }
      catch (final InstantiationException e) {
         LOGGER.log(Level.SEVERE, null, e);
      }
      catch (final IllegalAccessException e) {
         LOGGER.log(Level.SEVERE, null, e);
      }
      return algorithm;
   } // createAlgorithmInstance

}