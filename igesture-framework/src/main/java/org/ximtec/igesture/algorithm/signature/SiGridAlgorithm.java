/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Implementation of the signature algorithm.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.signature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.sigtec.util.Constant;
import org.ximtec.igesture.algorithm.AlgorithmTool;
import org.ximtec.igesture.algorithm.SampleBasedAlgorithm;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.util.GestureTool;


/**
 * Implementation of the signature algorithm.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SiGridAlgorithm extends SampleBasedAlgorithm {

   private static final Logger LOGGER = Logger
         .getLogger(SiGridAlgorithm.class.getName());

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
      
   }


   public SiGridAlgorithm() {
   }


   public void init(Configuration config) {
      final Map<String, String> parameters = config.getParameters(this
            .getClass().getCanonicalName());
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

         for (final Gesture<Note> sample : getSamples(gestureClass)) {
            signatures.add(new GestureSignature((Note)sample.getGesture()
                  .clone(), gestureClass, rasterSize, gridSize));
         }

      }

   } // preprocess


   public ResultSet recognise(Gesture<?> gesture) {
      ResultSet resultSet = new ResultSet(maxResultSetSize);

      if (gesture instanceof GestureSample) {
         Map<String, Result> tmpResults = new HashMap<String, Result>();

         Note note = ((GestureSample)gesture).getGesture();

         GestureSignature input = new GestureSignature((Note)note.clone(), null,
               rasterSize, gridSize);
         
         
         resultSet.setGesture(note);

         final int numOfBits = input.getBitStringLength()
               * input.getNumberOfPoints();

         int minDistance = Integer.MAX_VALUE;

         for (GestureSignature signature : signatures) {
            int d = distanceAlgorithm.computeDistance(input, signature);

            if (d < minDistance) {
               minDistance = d;
            }

            LOGGER.info(signature.getGestureClass().getName() + "[Distance = "
                  + d + "; Accuracy = " + computeAccuracy(d, numOfBits) + "]");

            if (computeAccuracy(d, numOfBits) >= minAccuracy) {
               String gestureName = signature.getGestureClass().getName();
               if (!tmpResults.containsKey(gestureName)) {
                  tmpResults.put(signature.getGestureClass().getName(),
                        new Result(signature.getGestureClass(), computeAccuracy(
                              d, numOfBits)));
               }
               else {
                  Result result = tmpResults.get(signature.getGestureClass()
                        .getName());
                  result.setAccuracy(Math.max(result.getAccuracy(),
                        computeAccuracy(d, numOfBits)));
               }

            }
         }
         resultSet.addResults(tmpResults.values());
      }

      return resultSet;
   } // recognise


   /**
    * Computes the accuracy.
    * 
    * @param distance the distance.
    * @param numOfBits the number of bits the signatures have.
    * @return the accurracy.
    */
   private static double computeAccuracy(int distance, int numOfBits) {
      final double accuracy = (1 - (double)distance / numOfBits);
      return accuracy > 0 ? accuracy : 0;
   } // computeAccuracy


   /**
    * Creates a new distance algorithm instance.
    * 
    * @param className
    * @return the new distance algorithm instance.
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
