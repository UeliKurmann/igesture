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
import org.ximtec.igesture.io.win32.W32API;
import org.ximtec.igesture.io.win32.User32.MSG;
import org.ximtec.igesture.io.win32.W32API.HANDLE;
import org.ximtec.igesture.io.win32.W32API.HINSTANCE;
import org.ximtec.igesture.io.win32.W32API.LPARAM;
import org.ximtec.igesture.io.win32.W32API.LRESULT;
import org.ximtec.igesture.io.win32.W32API.WPARAM;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;


/**
 * Example of setting an hook function for keyboard. Implemented using JNA.
 * 
 * @author Michele Croci
 */

public class KeyboardUtils {

   
   HINSTANCE hinst;
   KeyboardProc kbListener;
   HANDLE handle;
   User32 lib = User32.INSTANCE;
   

   public KeyboardUtils() {
   }
   
   /*

      hinst = Kernel32.INSTANCE.GetModuleHandle(null);
      kbListener = new KeyboardProc() {

         public LRESULT callback(int code, WPARAM wParam, LPARAM lParam) {
            System.out.println("keyboard callback code: " + code + " wParam "
                  + wParam + " lParam " + lParam);

            return User32.INSTANCE.CallNextHookEx(null, code, wParam, lParam);

         }
      };

   }
*/

   /**
    * 
    * Register Hook function
    * 
    */

   public void registerHook() {
      handle = User32.INSTANCE.SetWindowsHookExW(User32.WH_KEYBOARD_LL, kbListener,hinst, 0);
      start_loop();
   }


   /**
    * 
    * Unregister Hook function
    * 
    */
   protected void finalize() throws Throwable {
      User32.INSTANCE.UnhookWindowsHookExW(handle);
      super.finalize();
   }


   public static void main(String[] args) {
      KeyboardUtils k = new KeyboardUtils();
      k.registerHook();

   }


   void start_loop() {
      System.out.println("3. ThreadID: "
            + Kernel32.INSTANCE.GetCurrentThreadId());

      MSG msg = new MSG();
      while (User32.INSTANCE.GetMessageW(msg, null, 0, 0) != 0) {
 //        User32.INSTANCE.DispatchMessage(msg);
 //        User32.INSTANCE.TranslateMessage(msg);
      }
   }
   
   public void keyEvent(int key, int status){
      lib.keybd_event(key, 0 , status, 0);
   }

   public interface KeyboardProc extends StdCallCallback {
      LRESULT callback(int code, WPARAM wParam, LPARAM lParam);
   }

   
   
   public interface Kernel32 extends W32API {

      Kernel32 INSTANCE = (Kernel32)Native.loadLibrary("kernel32",
            Kernel32.class, DEFAULT_OPTIONS);


      HINSTANCE GetModuleHandle(String lpModuleName);


      int GetModuleHandleEx(int flag, String str, HMODULE mod);


      int GetCurrentThreadId();


      int GetLastError();
      

   }

}
