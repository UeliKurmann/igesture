/*
 * @(#)IconLoader.java	1.0   Feb 01, 2007
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Icon Loader
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.02.2007		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.utils;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconLoader {
	
	private static String PATH = "16x16/";
	private static String MIME = "mimetypes/";
	private static String ACTION = "actions/";
	private static String DEVICE = "devices/";
	private static String STATUS = "status/";
	private static String TYPE = ".png";
	
	public static String PACKAGE = MIME+"package-x-generic";
	public static String DOCUMENT_NEW = ACTION+"document-new";
	public static String DOCUMENT_OPEN = ACTION+"document-open";
	
	public static String HARDDISK = DEVICE+"drive-harddisk";
	public static String TEXT_SCRIPT = ACTION+"text-x-script";
	public static String DELETE = ACTION+"edit-delete";
	public static String LIST_ADD = ACTION+"list-add";
	public static String LIST_REMOVE = ACTION+"list-remove";
	public static String IMPORT = ACTION+"format-indent-less";
	public static String EXPORT = ACTION+"format-indent-more";
	public static String SAVE = ACTION+"document-save";
	public static String INFORMATION = STATUS+"dialog-information";
	
	
	public static ImageIcon getIcon(String name){
		return loadImageIcon(loadImage(load(PATH+name+TYPE)));
	}
	
	public static Image getImage(String name){
		return loadImage(load(PATH+name+TYPE));
	}
	
	private static Image loadImage(InputStream stream){
		try {
			return ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
		
	private static ImageIcon loadImageIcon(Image image){
		return new ImageIcon(image);
	}
	
	private static InputStream load(String path){
		return IconLoader.class.getClassLoader().getResourceAsStream(path);
	}
	

}
