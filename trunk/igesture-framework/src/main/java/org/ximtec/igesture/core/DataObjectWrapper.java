/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		:   Interface for components that handle data objects.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 26.03.2008		ukurmann	Initial Release
 * 26.09.2008       bsigner     Cleanup
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

import java.util.List;


/**
 * Interface for components that handle data objects.
 * 
 * @version 1.0 26.03.2008
 * @author Ueli Kurmann
 * @author Beat Signer, bsigner@vub.ac.be
 */
public interface DataObjectWrapper {

   /**
    * Returns the list of data objects managed by this data object wrapper.
    * @return list of data objects.
    */
   List<DataObject> getDataObjects();
}
