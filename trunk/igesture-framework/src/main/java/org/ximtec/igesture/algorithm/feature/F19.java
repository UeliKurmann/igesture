/*
 * @(#)F19.java   1.0   Dec 26, 2006
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
 * UK Feature F24
 * Cos to x axis midpoint - endpont
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


public class F19 implements Feature {

   public double compute(Note note) {
      final Trace trace = FeatureTools.createTrace(note);

      final double a1 = FeatureTools.getAngle(trace.get(trace.size() / 2), trace
            .getEndPoint());

      return Math.cos(Math.toRadians(a1));
   }

}
