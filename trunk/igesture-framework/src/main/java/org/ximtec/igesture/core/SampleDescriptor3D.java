/*
 * @(#)$Id: SampleDescriptor.java 730 2009-08-05 21:17:30Z kurmannu $
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Describes a gesture by a set of gesture samples.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 * Jan 19, 2009		vogelsar	Made more generic to be able to store GestureSample3D
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.core;

import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

/**
 * Describes a gesture by a set of gesture samples.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SampleDescriptor3D extends DefaultSampleDescriptor<RecordedGesture3D> {
  @Override
  public String getName() {
    return SampleDescriptor3D.class.getSimpleName();
  }
}
