/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Textual descriptor.
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


package org.ximtec.igesture.core;

import org.sigtec.util.Constant;


/**
 * Textual descriptor.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TextDescriptor extends DefaultDescriptor {

   public static final String PROPERTY_TEXT = "text";

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
      setText(text);
   }


   /**
    * Sets the text description.
    * 
    * @param text the textual description to be added.
    */
   public void setText(String text) {
      String oldValue = this.text;
      this.text = text;
      propertyChangeSupport.firePropertyChange(PROPERTY_TEXT, oldValue, text);
   } // setText


   /**
    * Returns the textual description.
    * 
    * @return the textual description.
    */
   public String getText() {
      return text;
   } // getText


   /**
    * {@inheritDoc}
    */
   @Override
   public void accept(Visitor visitor) {
      visitor.visit(this);
   }


   @Override
   public String toString() {
      return this.getClass().getSimpleName();
   } // toString

}
