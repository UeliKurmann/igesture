/*
 * @(#)$Id: GestureSampleHelper.java 689 2009-07-22 00:10:27Z bsigner $
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.rubinebd;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.ximtec.igesture.algorithm.feature.FeatureException;
import org.ximtec.igesture.util.BigDecimalVector;


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
   private BigDecimalVector featureVector;


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
   private BigDecimalVector computeFeatureVector()
         throws FeatureException {
     
    

      // clone the note to avoid side effects
      Note clonedNote = (Note)note.clone();
      clonedNote.scaleTo(200, 200);

      // filter the note using the min distance
      clonedNote.filter(config.getMinDistance());

      // create the feature vector
      BigDecimalVector featureVector = new BigDecimalVector(config.getNumberOfFeatures());

      for (int i = 0; i < config.getNumberOfFeatures(); i++) {
      
        
         featureVector.set(i, new BigDecimal(config.getFeatureList()[i].compute(clonedNote)));
      }
      System.out.println(featureVector.toString());
      LOGGER.info(featureVector.toString());
      return featureVector;
   } // computeFeatureVector


   public BigDecimalVector getFeatureVector() throws FeatureException {
      if(featureVector == null){
         featureVector = computeFeatureVector();
      }
      return featureVector;
   }

}
