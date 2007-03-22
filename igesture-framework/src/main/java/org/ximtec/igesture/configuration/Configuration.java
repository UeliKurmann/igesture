/*
 * @(#)Configuration.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Configuration object used by the algorithms.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ximtec.igesture.core.DefaultDataObject;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.EventManager;
import org.ximtec.igesture.util.GestureTool;


/**
 * Configuration object used by the algorithms. The configuration objects
 * contains information about the used gesture set (1..*), the algorithm(s) to be
 * used (1..*), the parameters for the algorithms, the minimal accuracy as well
 * as the size of the result list in the result set.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
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
    * Constructs a new configuration and initalises the used containers.
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
    * Adds a gesture set to the configuration.
    * 
    * @param gestureSet the gesture set to be added.
    */
   public void addGestureSet(GestureSet gestureSet) {
      gestureSets.add(gestureSet);
   } // addGestureSet


   public void addGestureSets(List<GestureSet> gestureSets) {
      this.gestureSets.addAll(gestureSets);
   } // addGestureSets


   /**
    * Removes a gesture set from the configuration.
    * 
    * @param gestureSet the gesture set to be removed.
    */
   public void removeGestureSet(GestureSet gestureSet) {
      gestureSets.remove(gestureSet);
   } // removeGestureSet


   /**
    * Returns the list of gesture sets.
    * 
    * @return List<GestureSet> the list of gesture sets
    */
   public List<GestureSet> getGestureSets() {
      return gestureSets;
   } // getGestureSets


   /**
    * Returns the combined gesture set
    * 
    * @return the combined gesture set.
    */
   public GestureSet getGestureSet() {
      return GestureTool.combine(getGestureSets());
   } // getGestureSet


   /**
    * Adds an algorithm to the configuration.
    * 
    * @param algorithm the algorithm to be added to teh configuration.
    */
   public void addAlgorithm(String algorithm) {
      algorithms.add(algorithm);
      algorithmParameters.put(algorithm, new HashMap<String, String>());
   } // addAlgorithm


   /**
    * Removes an algorithm from the configuratiion.
    * 
    * @param algorithm the algorithm to be removed.
    */
   public void removeAlgorithm(String algorithm) {
      algorithms.remove(algorithm);
      algorithmParameters.remove(algorithm);
   } // removeAlgorithm


   /**
    * Returns the list with all algorithms.
    * 
    * @return the list with all algorithms.
    */
   public List<String> getAlgorithms() {
      return algorithms;
   } // getAlgorithms


   /**
    * Adds a key/value parameter to the specified algorithm. If the parameter map
    * does not exist it will be created.
    * 
    * @param algorithm the algorithm the parameter has to be added to.
    * @param key the key of the parameter to be added.
    * @param value the parameter value the value of the parameter to be added.
    */
   public void addParameter(String algorithm, String key, String value) {
      final HashMap<String, String> parameters = getParameters(algorithm);
      parameters.put(key, value);
   } // addParameter


   /**
    * Returns the map of parameters for a specific algorithm.
    * 
    * @param classname the classname of the algorithm whose parameters have to be
    *           returned.
    * @return a hasmap with the algorithms parameters.
    */
   public HashMap<String, String> getParameters(String classname) {
      HashMap<String, String> parameters = algorithmParameters.get(classname);

      if (parameters == null) {
         parameters = new HashMap<String, String>();
         algorithmParameters.put(classname, parameters);
      }

      return parameters;
   } // getParameters


   /**
    * Removes all parameter for a specific algorithm.
    * 
    * @param classname the classname of the algorithm whose parameters have to be
    *           removed.
    */
   public void removeParameters(String classname) {
      algorithmParameters.remove(classname);
   } // removeParameters


   /**
    * Sets the event manager.
    * 
    * @param eventManager the event manager to be set.
    */
   public void setEventManager(EventManager eventManager) {
      this.eventManager = eventManager;
   } // setEventManager


   /**
    * Returns the event manager.
    * 
    * @return the event manager.
    */
   public EventManager getEventManager() {
      return eventManager;
   } // getEventManager


   /**
    * Sets the minimal accuracy.
    * 
    * @param minAccuracy the minimal accuracy to be set.
    */
   public void setMinAccuracy(double minAccuracy) {
      this.minAccuracy = minAccuracy;
   } // setMinAccuracy


   /**
    * Returns the minimal accuracy.
    * 
    * @return minimal accuracy.
    */
   public double getMinAccuracy() {
      return minAccuracy;
   } // getMinAccuracy


   /**
    * Sets the maximal result set size.
    * 
    * @param maxResultSetSize the maximal result set size.
    */
   public void setMaxresultSetSize(int maxResultSetSize) {
      this.maxResultSetSize = maxResultSetSize;
   }


   /**
    * Returns the maximal result set size.
    * 
    * @return the maximal result set size.
    */
   public int getMaxResultSetSize() {
      return maxResultSetSize;
   } // getMaxResultSetSize


   /**
    * Clones the Configuration instance. The GestureSet and the EventManager are
    * not cloned.
    */
   @Override
   public Object clone() {
      Configuration clone = null;

      try {
         clone = (Configuration)super.clone();
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
   } // clone

}
