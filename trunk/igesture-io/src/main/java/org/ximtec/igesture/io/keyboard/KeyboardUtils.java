/*
 * @(#)KeyboardUtils.java 1.0   Nov 14, 2007
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
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


   public KeyboardUtils() {
      // HMODULE hmod = new HMODULE();
      hinst = Kernel32.INSTANCE.GetModuleHandle(null);
      kbListener = new KeyboardProc() {

         public LRESULT callback(int code, WPARAM wParam, LPARAM lParam) {
            System.out.println("keyboard callback code: " + code + " wParam "
                  + wParam + " lParam " + lParam);
    
            //lparam is always the same! How to get the pressed key?
            return User32.INSTANCE.CallNextHookEx(null, code, wParam, lParam);

         }
      };

   }


   /**
    * 
    * Register Hook function
    * 
    */

   public void registerHook() {
      handle = User32.INSTANCE.SetWindowsHookExW(User32.WH_KEYBOARD_LL, kbListener,hinst, 0);
      // handle = User32.INSTANCE.SetWindowsHookExW(User32.WH_KEYBOARD, kbListener,
      //hinst, Kernel32.INSTANCE.GetCurrentThreadId()); 
      // Kernel32.INSTANCE.GetCurrentThreadId()
      //System.out.println("error: " + Kernel32.INSTANCE.GetLastError());
      //System.out.println("java. ThreadID: " + Thread.currentThread().getId());
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
      //System.out.println("1. ThreadID: "+ System.identityHashCode(Thread.currentThread()));
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
      
      // TODO: unregister hook!

      System.out.println("Done.");
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
