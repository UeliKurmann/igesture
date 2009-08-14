/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Implements methods used by sample based algorithms.
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

package org.ximtec.igesture.algorithm;

import java.util.List;

import org.ximtec.igesture.core.DefaultSampleDescriptor;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;

/**
 * This is an abstract base class for sample based recognition algorithms. All
 * implementations of this class use samples during the training or recognition
 * process.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public abstract class SampleBasedAlgorithm extends DefaultAlgorithm {

  /**
   * Returns the list of samples of a given gesture class. The samples are
   * extracted from the sample class descriptor.
   * 
   * @param gestureClass
   *          the gesture class whose samples have to be returned.
   * @return the samples for the given gesture class.
   */
  public <T> List<Gesture<T>> getSamples(GestureClass gestureClass, Class<? extends DefaultSampleDescriptor<T>> type) {
    final DefaultSampleDescriptor<T> descriptor = gestureClass.getDescriptor(type);
    return descriptor.getSamples();
  } // getSamples

}
