/*
 * @(#)F4.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Rubine Feature F4. The angle of the bounding box
 *                  diagonal.
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
import org.sigtec.ink.Trace;


/**
 * Rubine Feature F4. The angle of the bounding box diagonal.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F4 implements Feature {

   public double compute(Note note) {
      final Trace trace = FeatureTool.createTrace(note);
      final double minX = trace.getBounds2D().getMinX();
      final double minY = trace.getBounds2D().getMinY();
      final double maxX = trace.getBounds2D().getMaxX();
      final double maxY = trace.getBounds2D().getMaxY();
      return Math.atan((maxY - minY) / (maxX - minX));
   } // compute

}