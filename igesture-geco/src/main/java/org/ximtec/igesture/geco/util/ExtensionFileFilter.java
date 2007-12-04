/*
 * @(#)ExtensionFileFilter.java	1.0   Nov 15, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   General FileFilter based on file extension
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2007 	crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.util;


import java.io.File;

import javax.swing.filechooser.FileFilter;



/**
 * General FileFilter based on file extension
 * 
 * @version 1.0 Nov 15, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class ExtensionFileFilter extends FileFilter {
   String description;

   String extensions[];

   /**
    * Constructor
    * 
    * @param description a description of the file filter
    * @param extension, the file extension not to filter
    */
   public ExtensionFileFilter(String description, String extension) {
     this(description, new String[] { extension });
   }

   /**
    * Constructor
    * 
    * @param description a description of the file filter
    * @param extensions an array of file extension not to filter
    */
   public ExtensionFileFilter(String description, String extensions[]) {
     if (description == null) {
       this.description = extensions[0];
     } else {
       this.description = description;
     }
     this.extensions = (String[]) extensions.clone();
     toLower(this.extensions);
   }

   private void toLower(String array[]) {
     for (int i = 0, n = array.length; i < n; i++) {
       array[i] = array[i].toLowerCase();
     }
   }//toLower

   /**
    * Returns the description
    * 
    * @return the description
    */
   public String getDescription() {
     return description;
   }//getDescription

   
   public boolean accept(File file) {
     if (file.isDirectory()) {
       return true;
     } else {
       String path = file.getAbsolutePath().toLowerCase();
       for (int i = 0, n = extensions.length; i < n; i++) {
         String extension = extensions[i];
         if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
           return true;
         }
       }
     }
     return false;
   }//accept
 }