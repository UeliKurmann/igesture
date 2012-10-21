/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	XML support for the BatchResult class.
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.batch.jdom;

import org.jdom.Element;
import org.sigtec.jdom.element.JdomDoubleElement;
import org.sigtec.jdom.element.JdomIntegerElement;
import org.ximtec.igesture.batch.BatchResult;
import org.ximtec.igesture.batch.Statistic;
import org.ximtec.igesture.configuration.jdom.JdomConfiguration;


/**
 * XML support for the BatchResult class.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class JdomBatchResult extends Element {

   public static final String RUNNING_TIME = "runningTime";

   private static final String PRECISION = "precision";

   public static final String RECALL = "recall";

   public static final String NUMBER_OF_SAMPLES = "numberOfSamples";

   public static final String NUMBER_OF_NOISE = "numberOfNoise";

   public static final String NUMBER_OF_REJECT_CORRECT = "numberOfRejectCorrect";

   public static final String NUMBER_OF_REJECT_ERROR = "numberOfRejectError";

   public static final String NUMBER_OF_ERRORS = "numberOfErrors";

   public static final String NUMBER_OF_CORRECTS = "numberOfCorrects";

   public static final String ROOT_TAG = "batchResult";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String UUID_ATTRIBUTE = "id";


   public JdomBatchResult(BatchResult result) {
      super(ROOT_TAG);
      this.addContent(new JdomIntegerElement(NUMBER_OF_CORRECTS, result
            .getNumberOfCorrects()));
      this.addContent(new JdomIntegerElement(NUMBER_OF_ERRORS, result
            .getNumberOfErrors()));
      this.addContent(new JdomIntegerElement(NUMBER_OF_REJECT_CORRECT, result
            .getNumberOfRejectCorrect()));
      this.addContent(new JdomIntegerElement(NUMBER_OF_REJECT_ERROR, result
            .getNumberOfRejectError()));
      this.addContent(new JdomIntegerElement(NUMBER_OF_SAMPLES, result
            .getNumberOfSamples()));
      this.addContent(new JdomIntegerElement(NUMBER_OF_NOISE, result
            .getNumberOfNoise()));
      this.addContent(new JdomDoubleElement(RECALL, result.getRecall()));
      this.addContent(new JdomDoubleElement(PRECISION, result.getPrecision()));
      this.addContent(new JdomDoubleElement(RUNNING_TIME, result
            .getRunningTime()));
      this.addContent(new JdomConfiguration(result.getConfiguration()));

      for (final Statistic statistic : result.getStatistics()) {
         this.addContent(new JdomClassStatistic(statistic));
      }

   }

}
