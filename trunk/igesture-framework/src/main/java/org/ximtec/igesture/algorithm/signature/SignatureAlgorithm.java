/*
 * @(#)SignatureAlgoirthm.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Implemenation of the signature algorithm.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 18, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.signature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.sigtec.util.Constant;
import org.ximtec.igesture.algorithm.AlgorithmTool;
import org.ximtec.igesture.algorithm.SampleBasedAlgorithm;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.util.GestureTool;


/**
 * Implemenation of the signature algorithm.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SignatureAlgorithm extends SampleBasedAlgorithm {

   private static final Logger LOGGER = Logger
         .getLogger(SignatureAlgorithm.class.getName());

   private List<GestureSignature> signatures;

   private int gridSize;

   private int rasterSize;

   private DistanceFunction distanceAlgorithm;

   private int maxResultSetSize;

   private double minAccuracy;

   private static final String DEFAULT_GRID_SIZE = "8";

   private static final String DEFAULT_RASTER_SIZE = "120";

   public enum Config {
      GRID_SIZE, RASTER_SIZE, DISTANCE_FUNCTION, MIN_ACCURACY
   }

   static {
      /**
       * Parameter Default Values
       */
      DEFAULT_CONFIGURATION.put(Config.GRID_SIZE.name(), DEFAULT_GRID_SIZE);
      DEFAULT_CONFIGURATION.put(Config.RASTER_SIZE.name(), DEFAULT_RASTER_SIZE);
      DEFAULT_CONFIGURATION.put(Config.DISTANCE_FUNCTION.name(),
            HammingDistance.class.getName());
      DEFAULT_CONFIGURATION.put(Config.MIN_ACCURACY.name(), "0.5");
      LOGGER.setLevel(Level.WARNING);
   }


   public SignatureAlgorithm() {
   }


   public void init(Configuration config) {
      final HashMap<String, String> parameters = config
            .getParameters(this.getClass().getCanonicalName());
      gridSize = (int)AlgorithmTool.getDoubleParameterValue(Config.GRID_SIZE
            .name(), parameters, DEFAULT_CONFIGURATION);
      LOGGER.info(Config.GRID_SIZE.name() + Constant.COLON_BLANK + gridSize);
      rasterSize = (int)AlgorithmTool.getDoubleParameterValue(Config.RASTER_SIZE
            .name(), parameters, DEFAULT_CONFIGURATION);
      LOGGER.info(Config.RASTER_SIZE.name() + Constant.COLON_BLANK + rasterSize);
      distanceAlgorithm = createDistanceAlgorithm(AlgorithmTool
            .getParameterValue(Config.DISTANCE_FUNCTION.name(), parameters,
                  DEFAULT_CONFIGURATION));
      minAccuracy = AlgorithmTool.getDoubleParameterValue(Config.MIN_ACCURACY
            .name(), parameters, DEFAULT_CONFIGURATION);

      // minAccuracy = config.getMinAccuracy();
      maxResultSetSize = config.getMaxResultSetSize();
      preprocess(GestureTool.combine(config.getGestureSets()));
   } // init


   /**
    * Creates sample signatures.
    * 
    * @param gestureSet the gesture set.
    */
   public void preprocess(GestureSet gestureSet) {
      signatures = new ArrayList<GestureSignature>();

      for (final GestureClass gestureClass : gestureSet.getGestureClasses()) {

         for (final GestureSample sample : getSamples(gestureClass)) {
            signatures.add(new GestureSignature((Note)sample.getNote().clone(),
                  gestureClass, rasterSize, gridSize));
         }

      }

   } // preprocess


   public ResultSet recognise(Note note) {
      final GestureSignature input = new GestureSignature((Note)note.clone(),
            null, rasterSize, gridSize);
      final ResultSet resultSet = new ResultSet(maxResultSetSize);
      resultSet.setNote(note);
      final int numOfBits = input.getBitStringLength()
            * input.getNumberOfPoints();
      int minDistance = Integer.MAX_VALUE;

      for (final GestureSignature signature : signatures) {
         final int d = distanceAlgorithm.computeDistance(input, signature);

         if (d < minDistance) {
            minDistance = d;
         }

         LOGGER.info(signature.getGestureClass().getName() + "[Distance = " + d
               + "; Accuracy = " + computeAccuracy(d, numOfBits) + "]");

         if (computeAccuracy(d, numOfBits) >= minAccuracy) {

            if (!resultSet.contains(signature.getGestureClass())) {
               resultSet.addResult(new Result(signature.getGestureClass(),
                     computeAccuracy(d, numOfBits)));
            }
            else {
               for (final Result result : resultSet.getResults()) {

                  if (result.getGestureClass() == signature.getGestureClass()) {
                     result.setAccuracy(Math.max(computeAccuracy(d, numOfBits),
                           result.getAccuracy()));
                     break;
                  }

               }

            }

         }

      }

      fireEvent(resultSet);
      return resultSet;
   } // recognise


   /**
    * Computes the accuracy.
    * 
    * @param distance the distance.
    * @param numOfBits the number bits the signatures have.
    * @return
    */
   private static double computeAccuracy(int distance, int numOfBits) {
      final double accuracy = (1 - (double)distance / numOfBits);
      return accuracy > 0 ? accuracy : 0;
   } // computeAccuracy


   /**
    * Creates a new distance algorithm instance.
    * 
    * @param className
    * @return
    */
   private static DistanceFunction createDistanceAlgorithm(String className) {
      try {
         return (DistanceFunction)Class.forName(className.trim()).newInstance();
      }
      catch (final InstantiationException e) {
         LOGGER.throwing(Constant.EMPTY_STRING, Constant.EMPTY_STRING, e);
      }
      catch (final IllegalAccessException e) {
         LOGGER.throwing(Constant.EMPTY_STRING, Constant.EMPTY_STRING, e);
      }
      catch (final ClassNotFoundException e) {
         LOGGER.throwing(Constant.EMPTY_STRING, Constant.EMPTY_STRING, e);
      }
      return null;
   } // createDistanceAlgorithm


   @SuppressWarnings("unchecked")
   public Enum[] getConfigParameters() {
      return Config.values();
   } // getConfigParameters

}