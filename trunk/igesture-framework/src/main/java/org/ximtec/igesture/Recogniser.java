/*
 * @(#)Recogniser.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :	Front-End of the iGesture Framework 
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


package org.ximtec.igesture;

import java.io.File;
import java.util.List;

import org.sigtec.ink.Note;
import org.ximtec.igesture.algorithm.Algorithm;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.AlgorithmFactory;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Result;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.event.EventManager;
import org.ximtec.igesture.util.XMLTools;


public class Recogniser {

   private List<Algorithm> algorithms;


   /**
    * Creates a new recogniser
    * 
    * @param config the configuration object
    */
   public Recogniser(Configuration config) throws AlgorithmException {
      algorithms = AlgorithmFactory.createAlgorithms(config);
   }


   /**
    * Create a new recogniser
    * 
    * @param config the configuration object
    * @param eventManager the event manager
    */
   public Recogniser(Configuration config, EventManager eventManager)
         throws AlgorithmException {
      final Configuration clone = (Configuration) config.clone();
      clone.setEventManager(eventManager);
      algorithms = AlgorithmFactory.createAlgorithms(clone);
   }


   /**
    * Creates a new recogniser
    * 
    * @param config
    * @param gestureSet
    */
   public Recogniser(Configuration config, GestureSet gestureSet)
         throws AlgorithmException {
      config.addGestureSet(gestureSet);
      algorithms = AlgorithmFactory.createAlgorithms(config);
   }


   /**
    * Creates a new recogniser
    * 
    * @param configFile the file containing the XML configuration file
    */
   public Recogniser(File configFile) throws AlgorithmException {
      this(XMLTools.importConfiguration(configFile));
   }


   /**
    * Creates a new recogniser
    * 
    * @param configFile the configuration XML file
    * @param setFile the gesture set XML file
    * @param eventManager the event manager
    */
   public Recogniser(File configFile, File setFile, EventManager eventManager)
         throws AlgorithmException {
      final Configuration config = XMLTools.importConfiguration(configFile);
      config.setEventManager(eventManager);
      config.addGestureSets(XMLTools.importGestureSet(setFile));
      algorithms = AlgorithmFactory.createAlgorithms(config);
   }


   /**
    * Recognies a gesture. The method uses all algorithms and combines the result
    * set of them.
    * 
    * @param note the note to recognise
    * @return the ResultSet containing the recognised gesture classes
    */
   public ResultSet recognise(Note note, boolean recogniseAll) {
      final ResultSet result = new ResultSet();
      for (final Algorithm algorithm : algorithms) {
         for (final Result r : algorithm.recognise(note).getResults()) {
            result.add(r);
         }
         if (!result.isEmpty() && !recogniseAll) {
            break;
         }
      }
      return result;
   }


   /**
    * Recognies a gesture. The method uses the algorithms registred and stops as
    * soon as the first algorithm returns a result.
    * 
    * @param note the note to recognise
    * @return the ResultSet containing the recognised gesture classes
    */
   public ResultSet recognise(Note note) {
      return recognise(note, false);
   }
}
