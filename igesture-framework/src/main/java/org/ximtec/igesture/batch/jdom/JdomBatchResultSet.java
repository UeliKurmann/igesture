/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	XML support for the BatchResultSet class.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch.jdom;

import org.jdom.Element;
import org.ximtec.igesture.batch.BatchResult;
import org.ximtec.igesture.batch.BatchResultSet;


/**
 * XML support for the BatchResultSet class.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class JdomBatchResultSet extends Element {

   public static final String ROOT_TAG = "batchResultSet";


   public JdomBatchResultSet(BatchResultSet resultSet) {
      super(ROOT_TAG);

      for (final BatchResult result : resultSet.getBatchResults()) {
         this.addContent(new JdomBatchResult(result));
      }
   }

}
