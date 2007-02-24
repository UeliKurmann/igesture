/*
 * @(#)TextDescriptor.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Implementation of the TextDescriptor
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


package org.ximtec.igesture.core;

public class TextDescriptor extends DefaultDescriptor {

   private String text;


   /**
    * Constructor
    * 
    */
   public TextDescriptor() {
      this("");
   }


   /**
    * Constructr
    * 
    * @param text the textual description
    */
   public TextDescriptor(String text) {
      super();
      this.text = text;

   }


   /**
    * Sets the text description
    * 
    * @param text
    */
   public void setText(String text) {
      this.text = text;
   }


   /**
    * Returns the text description
    * 
    * @return the text description
    */
   public String getText() {
      return this.text;
   }


   @Override
   public String toString() {
      return this.getClass().getSimpleName();
   }
}
