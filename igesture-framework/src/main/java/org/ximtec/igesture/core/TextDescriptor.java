/*
 * @(#)TextDescriptor.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Implementation of the text descriptor.
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


package org.ximtec.igesture.core;

import org.sigtec.util.Constant;


/**
 * Implementation of the text descriptor.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TextDescriptor extends DefaultDescriptor {

   private String text;


   /**
    * Constructs a new text descriptor.
    * 
    */
   public TextDescriptor() {
      this(Constant.EMPTY_STRING);
   }


   /**
    * Constructs a new text descriptor with a given text.
    * 
    * @param text the textual description.
    */
   public TextDescriptor(String text) {
      super();
      this.text = text;
   }


   /**
    * Sets the text description.
    * 
    * @param text the textual description to be added.
    */
   public void setText(String text) {
      this.text = text;
   } // setText


   /**
    * Returns the textual description.
    * 
    * @return the textual description.
    */
   public String getText() {
      return this.text;
   } // getText


   @Override
   public String toString() {
      return this.getClass().getSimpleName();
   } // toString

}
