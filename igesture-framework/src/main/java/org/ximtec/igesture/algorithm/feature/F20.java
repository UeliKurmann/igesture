/*
 * @(#)F20.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   UK Feature F25. Proportion Start/Endpoint to
 *                  Diagonal of the bounding box.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 15, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;


/**
 * UK Feature F25. Proportion Start/Endpoint to Diagonal of the bounding box.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F20 implements Feature {

   private static final int MINIMAL_NUMBER_OF_POINTS = 2;


   public double compute(Note note) throws FeatureException {
      if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
         throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
      }
      
      return new F5().compute(note) / new F3().compute(note);
   } // compute


   public int getMinimalNumberOfPoints() {
      return MINIMAL_NUMBER_OF_POINTS;
   } // getMinimalNumberOfPoints

}