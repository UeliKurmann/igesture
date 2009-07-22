/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   UK Feature F19. Proportion of stroke duration.
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.feature;

import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


/**
 * UK Feature F19. Proportion of stroke duration.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F25 implements Feature {

   private static final int MINIMAL_NUMBER_OF_POINTS = 2;


   public double compute(Note note) throws FeatureException {
      if (note.getPoints().size() < MINIMAL_NUMBER_OF_POINTS) {
         throw new FeatureException(FeatureException.NOT_ENOUGH_POINTS);
      }

      double proportion = 0;
      final List<Trace> traces = note.getTraces();

      if (traces.size() > 1) {

         for (final Trace trace : traces) {
            final double divisor = trace.getDuration();

            if (proportion == 0) {
               proportion = divisor;
            }
            else if (divisor != 0) {
               proportion /= divisor;
            }
            else {
               proportion = 1;
            }

         }

      }
      else {
         proportion = 1;
      }

      return proportion;
   } // compute


   public int getMinimalNumberOfPoints() {
      return MINIMAL_NUMBER_OF_POINTS;
   } // getMinimalNumberOfPoints

}
