/*
 * @(#)F13.java   1.0   Dec 26, 2006
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
 * Rubine Feature F13
 * The duration of the gesture
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


public class F13 implements Feature {

   public double compute(Note note) {
      final Trace trace = FeatureTools.createTrace(note);
      return trace.getEndPoint().getTimestamp()
            - trace.getStartPoint().getTimestamp();
   }
}
