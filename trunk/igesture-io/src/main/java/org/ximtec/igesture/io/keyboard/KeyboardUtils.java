/*
 * @(#)$Id$
 *
 * Author       :   Croci Michele, mcroci@gmail.com,
 *                  based on a first version of Abdullah Jibaly
 *
 * Purpose      :  Example of setting an hook function for keyboard.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 14, 2007    crocimi     Initial Release
 * Nov 27, 2008     kuu         Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.keyboard;

import org.ximtec.igesture.io.win32.User32;
import org.ximtec.igesture.io.win32.Win32;
import org.ximtec.igesture.io.win32.User32.MSG;
import org.ximtec.igesture.io.win32.W32API.HANDLE;
import org.ximtec.igesture.io.win32.W32API.HINSTANCE;
import org.ximtec.igesture.io.win32.W32API.LPARAM;
import org.ximtec.igesture.io.win32.W32API.LRESULT;
import org.ximtec.igesture.io.win32.W32API.WPARAM;

import com.sun.jna.win32.StdCallLibrary.StdCallCallback;


/**
 * Example of setting an hook function for keyboard. Implemented using JNA.
 * 
 * @author Michele Croci
 * @author Ueli Kurmann
 */

public class KeyboardUtils {

   
   HINSTANCE hinst;
   KeyboardProc kbListener;
   HANDLE handle;
   User32 lib = Win32.USER32_INSTANCE;
   

   /**
    * 
    * Register Hook function
    * 
    */

   public void registerHook() {
      handle = lib.SetWindowsHookExW(User32.WH_KEYBOARD_LL, kbListener,hinst, 0);
      start_loop();
   }



   public static void main(String[] args) {
      KeyboardUtils k = new KeyboardUtils();
      k.registerHook();

   }


   void start_loop() {


      MSG msg = new MSG();
      while (lib.GetMessageW(msg, null, 0, 0) != 0) {

      }
   }
   
   public void keyEvent(int key, int status){
      lib.keybd_event(key, 0 , status, 0);
   }

   public interface KeyboardProc extends StdCallCallback {
      LRESULT callback(int code, WPARAM wParam, LPARAM lParam);
   }
   
   
   /**
    * 
    * Unregister Hook function
    * 
    */
   @Override
   protected void finalize() throws Throwable {
      lib.UnhookWindowsHookExW(handle);
      super.finalize();
   }
}
