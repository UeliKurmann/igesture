/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	XML support for the ClassStatistic class.
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
import org.sigtec.jdom.element.JdomIntegerElement;
import org.ximtec.igesture.batch.Statistic;


/**
 * XML support for the ClassStatistic class.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class JdomClassStatistic extends Element {

   public static final String ROOT_TAG = "classStatistic";

   public static final String NAME_ATTRIBUTE = "name";

   public static final String NUMBER_OF_REJECT_CORRECT = "numberOfRejectCorrect";

   public static final String NUMBER_OF_REJECT_ERROR = "numberOfRejectError";

   public static final String NUMBER_OF_ERRORS = "numberOfErrors";

   public static final String NUMBER_OF_CORRECTS = "numberOfCorrects";


   public JdomClassStatistic(Statistic statistic) {
      super(ROOT_TAG);
      this.setAttribute(NAME_ATTRIBUTE, statistic.getClassName());
      this.addContent(new JdomIntegerElement(NUMBER_OF_CORRECTS, statistic
            .getCorrect()));
      this.addContent(new JdomIntegerElement(NUMBER_OF_ERRORS, statistic
            .getError()));
      this.addContent(new JdomIntegerElement(NUMBER_OF_REJECT_ERROR, statistic
            .getRejectError()));
      this.addContent(new JdomIntegerElement(NUMBER_OF_REJECT_CORRECT, statistic
            .getRejectCorrect()));
   }

}
