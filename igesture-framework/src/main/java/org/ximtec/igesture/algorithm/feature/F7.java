/*
 * @(#)F8.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Rubine Feature F7. The sine of the angle between the
 *                  first and last point.
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
 * Rubine Feature F7. The sine of the angle between the first and last point.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class F7 implements Feature {

   public double compute(Note note) {
      final Trace trace = FeatureTool.createTrace(note);
      final double yn = trace.getEndPoint().getY();
      final double y0 = trace.getStartPoint().getY();
      final F5 f5 = new F5();
      double divisor = f5.compute(note);

      if (divisor == 0) {
         divisor = 1;
      }

      return (yn - y0) / divisor;
   } // compute

}
