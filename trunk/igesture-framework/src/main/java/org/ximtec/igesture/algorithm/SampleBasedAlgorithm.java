/*
 * @(#)SampleBasedAlgorithm.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Implements methods used by sample based algorithms
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


package org.ximtec.igesture.algorithm;

import java.util.List;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.SampleDescriptor;


public abstract class SampleBasedAlgorithm extends DefaultAlgorithm {

   /**
    * this is an abstract base class for sample based recognition algorithms.
    * all implementations of this class use samples during the training or
    * recognition process.
    */

   /**
    * Returns the list of samples of a given gesture class. the samples are
    * extracted out of the sampleclass descriptor.
    * 
    * @return the samples of the gesture class
    */
   public List<GestureSample> getSamples(GestureClass gestureClass) {
      final SampleDescriptor descriptor = gestureClass
            .getDescriptor(SampleDescriptor.class);
      return descriptor.getSamples();
   }

}
