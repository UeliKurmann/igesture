/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Datastructure to handle algorithms in batch process.
 * 					This class also provides XML import functionality.
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


package org.ximtec.igesture.batch.core;

import java.util.ArrayList;
import java.util.List;


/**
 * Datastructure to handle algorithms in batch process. This class aslo provides
 * XML import functionality.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class BatchAlgorithm {

   private List<BatchParameter> parameters;

   private String name;


   public BatchAlgorithm() {
      parameters = new ArrayList<BatchParameter>();
   }


   public void setName(String name) {
      this.name = name;
   } // setName


   public String getName() {
      return name;
   } // getName


   public void addParameter(BatchParameter parameter) {
      parameters.add(parameter);
   } // addParameter


   public List<BatchParameter> getParameters() {
      return parameters;
   } // getParameters


   

}
