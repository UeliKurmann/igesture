/*
 * @(#)Configuration.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Configuration Object used by algorithms
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

/**
 * This class represents the configuration of a session. it contains information
 * about:
 * - the used gesture set (1..*)
 * - the algorithms to use (1..*)
 * - parameters for the algorithms
 * - the minimum accuracy
 * - the size of the result list in resultset
 * - 
 */


package org.ximtec.igesture.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ximtec.igesture.core.DefaultDataObject;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.EventManager;
import org.ximtec.igesture.util.GestureTools;


public class Configuration extends DefaultDataObject implements Cloneable {

   /**
    * The list of gesture sets which are selected for the recognition process
    */
   private List<GestureSet> gestureSets;

   /**
    * A list of algorithms. The Input is sent to each algorithm
    */
   private List<String> algorithms;

   /**
    * Algorithm specific parameters. Each algorithm may have arbritary
    * parameters.
    */
   private HashMap<String, HashMap<String, String>> algorithmParameters;

   /**
    * The instance of the eventManager
    */
   private EventManager eventManager;

   /**
    * The maximal size of the result set
    */
   private int maxResultSetSize;

   /**
    * The minimum Accuracy a result must have
    */
   private double minAccuracy;


   /**
    * Constructor initalises the used containers
    * 
    */
   public Configuration() {
      super();
      this.gestureSets = new ArrayList<GestureSet>();
      this.algorithms = new ArrayList<String>();
      this.algorithmParameters = new HashMap<String, HashMap<String, String>>();
      this.maxResultSetSize = Integer.MAX_VALUE;
      this.minAccuracy = 0;
   }


   /**
    * Adds a gesture set to the collection
    * 
    * @param gestureSet the gesture set to add
    */
   public void addGestureSet(GestureSet gestureSet) {
      gestureSets.add(gestureSet);
   }


   public void addGestureSets(List<GestureSet> gestureSets) {
      this.gestureSets.addAll(gestureSets);
   }


   /**
    * Removes a gesture set from the collection
    * 
    * @param gestureSet
    */
   public void removeGestureSet(GestureSet gestureSet) {
      gestureSets.remove(gestureSet);
   }


   /**
    * Returns the collection of gesture sets
    * 
    * @return List<GestureSet> the list of gesture sets
    */
   public List<GestureSet> getGestureSets() {
      return gestureSets;
   }


   /**
    * Returns the combined gesture set
    * 
    * @return
    */
   public GestureSet getGestureSet() {
      return GestureTools.combine(getGestureSets());
   }


   /**
    * Adds an algorithm to the collection
    * 
    * @param algorithm
    */
   public void addAlgorithm(String algorithm) {
      algorithms.add(algorithm);
      algorithmParameters.put(algorithm, new HashMap<String, String>());
   }


   /**
    * Removes an algorithm from the gesture set
    * 
    * @param algorithm the algorithm to remove
    */
   public void removeAlgorithm(String algorithm) {
      algorithms.remove(algorithm);
      algorithmParameters.remove(algorithm);
   }


   /**
    * Returns the collection of all algorithms
    * 
    * @return
    */
   public List<String> getAlgorithms() {
      return algorithms;
   }


   /**
    * Adds a name/value parameter to the specified algorithm. If the parameter
    * map doesn't exist it will be created.
    * 
    * @param algorithm the algorithm to add the parameter
    * @param name the parameter name
    * @param value the parameter value
    */
   public void addAlgorithmParameter(String algorithm, String name, String value) {
      final HashMap<String, String> parameters = getAlgorithmParameters(algorithm);
      parameters.put(name, value);
   }


   /**
    * Returns the map of parameters of a specified algorithm.
    * 
    * @param classname
    * @return HashMap<String,String>
    */
   public HashMap<String, String> getAlgorithmParameters(String classname) {
      HashMap<String, String> parameters = algorithmParameters.get(classname);
      if (parameters == null) {
         parameters = new HashMap<String, String>();
         algorithmParameters.put(classname, parameters);
      }
      return parameters;
   }


   /**
    * Removes all algorithm parameters
    * 
    * @param classname
    */
   public void removeAlgorithmParameter(String classname) {
      algorithmParameters.remove(classname);
   }


   /**
    * Returns the Event Manager
    * 
    * @return
    */
   public EventManager getEventManager() {
      return eventManager;
   }


   /**
    * Sets the Event Manager
    */
   public void setEventManager(EventManager eventManager) {
      this.eventManager = eventManager;
   }


   /**
    * Returns the minimal accuracy
    * 
    * @return minimal accuracy
    */
   public double getMinAccuracy() {
      return minAccuracy;
   }


   /**
    * Sets the minimal accuracy
    * 
    * @param minAccuracy
    */
   public void setMinAccuracy(double minAccuracy) {
      this.minAccuracy = minAccuracy;
   }


   /**
    * Returns the maximal result set size
    * 
    * @return maximal result set size
    */
   public int getMaxResultSetSize() {
      return maxResultSetSize;
   }


   /**
    * Sets the maximal result set size
    * 
    * @param maxResultSetSize
    */
   public void setMaxresultSetSize(int maxResultSetSize) {
      this.maxResultSetSize = maxResultSetSize;
   }


   /**
    * Clones the Configuration instance - GestureSet is not cloned - EventManager
    * is not cloned
    */
   @Override
   public Object clone() {
      Configuration clone = null;

      try {
         clone = (Configuration) super.clone();
         // Clone Algorithm Parameters
         clone.algorithmParameters = new HashMap<String, HashMap<String, String>>();
         for (final String key : algorithmParameters.keySet()) {
            final HashMap<String, String> paramsClone = new HashMap<String, String>();
            final HashMap<String, String> paramsOriginal = algorithmParameters
                  .get(key);
            for (final String paramKey : paramsOriginal.keySet()) {
               paramsClone
                     .put(paramKey, paramsOriginal.get(paramKey).toString());
            }
            clone.algorithmParameters.put(key, paramsClone);
         }

         // Clone Algorithm Names
         clone.algorithms = new ArrayList<String>();
         for (final String s : algorithms) {
            clone.algorithms.add(s.toString());
         }

         // References EventManager
         clone.eventManager = eventManager;

         // References GestureSets
         clone.gestureSets = new ArrayList<GestureSet>();
      }
      catch (final CloneNotSupportedException e) {
         e.printStackTrace();
      }
      return clone;
   }
}
