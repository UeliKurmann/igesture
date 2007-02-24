/*
 * @(#)Recogniser.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :	Front-end of the iGesture framework 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Feb 24, 2007     bsigner     Cleanup
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


/**
 * Front-end of the iGesture framework.
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Recogniser {

   private List<Algorithm> algorithms;


   /**
    * Creates a new recogniser.
    * 
    * @param config the configuration object.
    */
   public Recogniser(Configuration config) throws AlgorithmException {
      algorithms = AlgorithmFactory.createAlgorithms(config);
   }


   /**
    * Creates a new recogniser.
    * 
    * @param config the configuration object.
    * @param eventManager the event manager.
    */
   public Recogniser(Configuration config, EventManager eventManager)
         throws AlgorithmException {
      final Configuration clone = (Configuration)config.clone();
      clone.setEventManager(eventManager);
      algorithms = AlgorithmFactory.createAlgorithms(clone);
   }


   /**
    * Creates a new recogniser.
    * 
    * @param config the configuration object.
    * @param gestureSet the gesture set to be used.
    */
   public Recogniser(Configuration config, GestureSet gestureSet)
         throws AlgorithmException {
      config.addGestureSet(gestureSet);
      algorithms = AlgorithmFactory.createAlgorithms(config);
   }


   /**
    * Creates a new recogniser.
    * 
    * @param configFile the file containing the XML configuration.
    */
   public Recogniser(File configFile) throws AlgorithmException {
      this(XMLTools.importConfiguration(configFile));
   }


   /**
    * Creates a new recogniser.
    * 
    * @param configFile the file containing the XML configuration.
    * @param setFile the file containing the gesture set in XML format.
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
    * Recognies a gesture. The method uses all algorithms and retunrs a
    * comination of the results returned by the different algorithms if
    * 'recogniseAll' is set to true or the result of the first algorithm that
    * recognised the given note if 'recogniseAll' is set to false.
    * 
    * @param note the note to be recognised.
    * @param recogniseAll true if the combination of all algorithms has to be
    *           returned, false if the result of the first algorithm that
    *           recognises the result has to be returned only.
    * @return the result set containing the recognised gesture classes.
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
   } // recognise


   /**
    * Recognies a gesture. The method uses all registered algorithms and stops as
    * soon as the first algorithm returns a result.
    * 
    * @param note the note to be recognised.
    * @return the result set containing the recognised gesture classes.
    */
   public ResultSet recognise(Note note) {
      return recognise(note, false);
   } // recognise

}
