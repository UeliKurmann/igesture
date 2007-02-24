/*
 * @(#)ApplicationException.java	1.0   Nov 23, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Exception thrown by algorithms
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					ukurmann	Initial Release
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

/**
 * Comment
 * 
 * @version 1.0 Nov 23, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class AlgorithmException extends Exception {

   public enum ExceptionType {
      Initialisation, Recognition
   }

   private ExceptionType exceptionType;


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
   }

}
