/*
 * @(#)F25.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 
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

/****************************************************************************************
 * UK Feature F19
 * Proportion of stroke duration
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


public class F25 implements Feature {

   public double compute(Note note) {
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
   }
}
