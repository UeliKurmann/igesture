/*
 * @(#)JdomBatchResultSet.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	XML support for BatchResultSet
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


package org.ximtec.igesture.batch.jdom;

import org.jdom.Element;
import org.ximtec.igesture.batch.BatchResult;
import org.ximtec.igesture.batch.BatchResultSet;


public class JdomBatchResultSet extends Element {

   public static final String ROOT_TAG = "batchResultSet";


   public JdomBatchResultSet(BatchResultSet resultSet) {
      super(ROOT_TAG);

      for (final BatchResult result : resultSet.getBatchResults()) {
         this.addContent(new JdomBatchResult(result));
      }
   }
}
