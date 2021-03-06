/*
 * @(#)$Id: WorkspaceTool.java 456 2008-11-11 09:54:06Z D\signerb $
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
 * 26.10.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;



/**
 * Comment
 * @version 1.0 26.10.2008
 * @author Ueli Kurmann
 */
public class ExtensionFileFilter extends FileFilter{
   
   
   private static final String DOT = ".";
   private String extension;
   private String description;
   
   public ExtensionFileFilter(String extension, String description){
      this.extension = extension;
      this.description = description;
   }
   
   
   
   @Override
   public boolean accept(File f) {
     if(f != null){
      return f.isDirectory() || f.getName().endsWith(DOT+extension);
     }else {
       return false;
     }
   }

   @Override
   public String getDescription() {
      return description;
   }
   
   public String getExtension(){
	   return extension;
   }

   
}
