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
 * 27.10.2008			ukurmann	Initial Release
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

import javax.swing.filechooser.FileFilter;



/**
 * Comment
 * @version 1.0 27.10.2008
 * @author Ueli Kurmann
 */
public class FileFilterFactory {
   
   private static FileFilter compressedWorkbench;
   private static FileFilter xstreamWorkbench;
   private static FileFilter db4oWorkbench;
   private static FileFilter gestureSet;
   private static FileFilter testSet;
   private static FileFilter algorithmConfiguration;
   private static FileFilter pdf;
   private static FileFilter igb;
   
   static{
      compressedWorkbench = new ExtensionFileFilter("igz", "iGesture Workspace Compressed");
      xstreamWorkbench = new ExtensionFileFilter("igx", "iGesture Workspace XStream");
      db4oWorkbench = new ExtensionFileFilter("igd", "iGesture Workspace db4o");
      gestureSet = new ExtensionFileFilter("igs", "iGesture Gesture Set");
      testSet = new ExtensionFileFilter("igt", "iGesture Test Set");
      algorithmConfiguration = new ExtensionFileFilter("igc", "iGesture Recogniser Configuraiton");
      pdf = new ExtensionFileFilter("pdf", "Portable Document Format");
      igb = new ExtensionFileFilter("igb", "iGesture Batch Configuration");
   }
   
   public static FileFilter getWorkspaceCompressed(){
      return compressedWorkbench;
   }
   
   public static FileFilter getWorkspaceXStream(){
      return xstreamWorkbench;
   }
   
   public static FileFilter getWorkspaceDb4o(){
      return db4oWorkbench;
   }
   
   public static FileFilter getTestSet(){
      return testSet;
   }
   
   public static FileFilter getGestureSet(){
      return gestureSet;
   }

   public static FileFilter getRecogniserConfig(){
      return algorithmConfiguration;
   }
   
   public static FileFilter getPdf(){
      return pdf;
   }
   
   public static FileFilter getBatchConfig(){
      return igb;
   }
}
