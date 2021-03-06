/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose		: 	Exception thrown by algorithms.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 23, 2006		ukurmann	Initial Release
 * Mar 14, 2007     bsigner     Cleanup
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

/**
 * Exception thrown by algorithms.
 * 
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class AlgorithmException extends Exception {

   public enum ExceptionType {
      Initialisation, Recognition
   }

   private ExceptionType exceptionType;


   public AlgorithmException(ExceptionType exceptionType, Throwable throwable) {
      super(throwable);
      this.exceptionType = exceptionType;
   }


   public AlgorithmException(ExceptionType exceptionType) {
      super();
      this.exceptionType = exceptionType;
   }


   @Override
   public String getMessage() {
      switch (exceptionType) {
         case Initialisation:
            break;
         case Recognition:
            break;
      }

      return super.getMessage();
   } // getMessage

}
