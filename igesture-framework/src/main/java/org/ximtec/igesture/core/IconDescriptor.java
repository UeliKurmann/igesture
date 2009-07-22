/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 22.10.2008			ukurmann	Initial Release
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

import java.awt.Image;


/**
 * Comment
 * @version 1.0 22.10.2008
 * @author Ueli Kurmann
 */
public class IconDescriptor extends DefaultDescriptor {

   private String path;
   
   public IconDescriptor(){
      
   }


   public Image getIcon() {
      return null;
   }


   public String getPath() {
      return path;
   }


   public void setPath(String path) {
      this.path = path;
   }
}
