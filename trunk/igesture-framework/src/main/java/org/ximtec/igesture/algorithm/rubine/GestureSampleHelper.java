/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 12.06.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.rubine;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.ximtec.igesture.algorithm.feature.FeatureException;
import org.ximtec.igesture.util.DoubleVector;


/**
 * Comment
 * @version 1.0 12.06.2008
 * @author Ueli Kurmann
 */
public class GestureSampleHelper {

   private static final Logger LOGGER = Logger.getLogger(GestureSampleHelper.class
         .getName());

   private Note note;
   private RubineConfiguration config;
   private DoubleVector featureVector;


   public GestureSampleHelper(Note note, RubineConfiguration config) {
      this.note = note;
      this.config = config;
      LOGGER.setLevel(Level.SEVERE);
   }
   
   /**
    * Computes the feature vector for a given sample. The features to compute are
    * passed as an array. The method returns a double vector representing the
    * feature vector.
    * 
    * @param sample the sample.
    * @param featureList the features to compute.
    * @return the feature vector for the given sample.
    */
   private DoubleVector computeFeatureVector()
         throws FeatureException {

      // clone the note to avoid side effects
      Note clone = (Note)note.clone();
      clone.scaleTo(200, 200);

      // filter the note using the min distance
      clone.filter(config.getMinDistance());

      // create the feature vector
      DoubleVector featureVector = new DoubleVector(config.getNumberOfFeatures());

      for (int i = 0; i < config.getNumberOfFeatures(); i++) {
         featureVector.set(i, config.getFeatureList()[i].compute(clone));
      }

      LOGGER.info(featureVector.toString());
      return featureVector;
   } // computeFeatureVector


   public DoubleVector getFeatureVector() throws FeatureException {
      if(featureVector == null){
         featureVector = computeFeatureVector();
      }
      return featureVector;
   }

}
