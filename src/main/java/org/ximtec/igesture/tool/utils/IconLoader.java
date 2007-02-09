package org.ximtec.igesture.tool.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconLoader {
	
	private static String PATH = "16x16/";
	private static String MIME = "mimetypes/";
	private static String ACTION = "actions/";
	private static String DEVICE = "devices/";
	private static String TYPE = ".png";
	
	public static String PACKAGE = "package-x-generic";
	public static String DOCUMENT_NEW = "document-new";
	public static String DOCUMENT_OPEN = "document-open";
	
	public static String HARDDISK = "drive-harddisk";
	public static String TEXT_SCRIPT = "text-x-script";
	public static String DELETE = "edit-delete";
	public static String LIST_ADD = "list-add";
	public static String LIST_REMOVE = "list-remove";
	public static String IMPORT = "format-indent-less";
	public static String EXPORT = "format-indent-more";
	public static String SAVE = "document-save";
	
	
	public static ImageIcon getMimeTypeIcon(String name){
		return load(load(PATH+MIME+name+TYPE));
	}
	
	public static ImageIcon getActionIcon(String name){
		return load(load(PATH+ACTION+name+TYPE));
	}
	
	public static ImageIcon getDeviceIcon(String name){
		return load(load(PATH+DEVICE+name+TYPE));
	}
	
	private static ImageIcon load(InputStream stream){
		try {
			return new ImageIcon(ImageIO.read(stream));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static InputStream load(String path){
		return IconLoader.class.getClassLoader().getResourceAsStream(path);
	}
	

}
