/*
 * @(#)F3.java   1.0   Dec 26, 2006
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
 * Rubine Feature F3
 * Length of the bounding box diagonal
 ****************************************************************************************/


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;


public class F3 implements Feature {

   public double compute(Note note) {
      final Trace trace = FeatureTools.createTrace(note);

      final double minX = trace.getBounds2D().getMinX();
      final double minY = trace.getBounds2D().getMinY();
      final double maxX = trace.getBounds2D().getMaxX();
      final double maxY = trace.getBounds2D().getMaxY();

      return Math.sqrt(Math.pow(maxX - minX, 2) + Math.pow(maxY - minY, 2));
   }
}
