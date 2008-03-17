/*
 * @(#)IconLoader.java	1.0   Feb 01, 2007
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Icon loader.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Feb 01, 2007     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.sigtec.util.Constant;


/**
 * Icon loader.
 * 
 * @version 1.0 Feb 2007
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class IconLoader {

   private static final Logger LOGGER = Logger.getLogger(IconLoader.class
         .getName());

   private static String PATH = "icons/16x16/";
   private static String MIME = "mimetypes/";
   private static String ACTION = "actions/";
   private static String DEVICE = "devices/";
   private static String STATUS = "status/";
   private static String TYPE = ".png";

   public static String PACKAGE = MIME + "package-x-generic";
   public static String DOCUMENT_NEW = ACTION + "document-new";
   public static String DOCUMENT_OPEN = ACTION + "document-open";

   public static String HARDDISK = DEVICE + "drive-harddisk";
   public static String TEXT_SCRIPT = MIME + "text-x-script";
   public static String DELETE = ACTION + "edit-delete";
   public static String LIST_ADD = ACTION + "list-add";
   public static String LIST_REMOVE = ACTION + "list-remove";
   public static String IMPORT = ACTION + "format-indent-less";
   public static String EXPORT = ACTION + "format-indent-more";
   public static String SAVE = ACTION + "document-save";
   public static String INFORMATION = STATUS + "dialog-information";


   public static ImageIcon getIcon(String name) {
      return loadImageIcon(loadImage(load(PATH + name + TYPE)));
   } // getIcon


   public static Image getImage(String name) {
      return loadImage(load(PATH + name + TYPE));
   } // getImage


   private static Image loadImage(InputStream stream) {
      try {
         return ImageIO.read(stream);
      }
      catch (IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

      return null;
   } // loadImage


   private static ImageIcon loadImageIcon(Image image) {
      return new ImageIcon(image);
   } // loadImageIcon


   private static InputStream load(String path) {
      return IconLoader.class.getClassLoader().getResourceAsStream(path);
   } // load

}
