/*
 * @(#)$Id:$
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
 * 27.11.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.win32;

import java.util.HashMap;
import java.util.Map;

import org.ximtec.igesture.io.wacom.Wintab32;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;


/**
 * Comment
 * @version 1.0 27.11.2008
 * @author Ueli Kurmann
 */
public class Win32 {

   /** Standard options to use the unicode version of a w32 API. */
   private static Map<String, Object> UNICODE_OPTIONS = new HashMap<String, Object>() {

      {
         put(StdCallLibrary.OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
         put(StdCallLibrary.OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
      }
   };

   /** Standard options to use the ASCII/MBCS version of a w32 API. */
   private static Map<String, Object> ASCII_OPTIONS = new HashMap<String, Object>() {

      {
         put(StdCallLibrary.OPTION_TYPE_MAPPER, W32APITypeMapper.ASCII);
         put(StdCallLibrary.OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.ASCII);
      }
   };

   private static Map<String, Object> DEFAULT_OPTIONS = Boolean
         .getBoolean("w32.ascii") ? ASCII_OPTIONS : UNICODE_OPTIONS;

   
   
   public static final GDI32 GDI32_INSTANCE = (GDI32)Native.loadLibrary("gdi32",
         GDI32.class, DEFAULT_OPTIONS);

   public static final User32 USER32_INSTANCE = (User32)Native.loadLibrary(
         "user32", User32.class, DEFAULT_OPTIONS);
   
   public static final Kernel32 KERNEL32_INSTANCE = (Kernel32)Native.loadLibrary("kernel32", Kernel32.class,
         DEFAULT_OPTIONS);
   
   public static final Wintab32 WINTAB32_INSTANCE = null;// = (Wintab32)
   //Native.loadLibrary("wintab32", Wintab32.class, DEFAULT_OPTIONS);

}
